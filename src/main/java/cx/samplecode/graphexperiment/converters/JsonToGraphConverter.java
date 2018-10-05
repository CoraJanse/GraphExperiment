package cx.samplecode.graphexperiment.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.samplecode.graphexperiment.model.Snapshot;
import org.apache.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public class JsonToGraphConverter {

    private final static Logger LOG = Logger.getLogger(JsonToGraphConverter.class);

    public Optional<TinkerGraph> readJsonToTinkergraph(String jsonString) {
        Optional<Snapshot> optionalSnapshot = readSnapshotFromJsonString(jsonString);
        if (optionalSnapshot.isPresent()) {
            LOG.info("Succesfully imported snapshot: " + optionalSnapshot.get());
            return convertSnapshotToTinkergraph(optionalSnapshot.get());
        }
        return Optional.empty();
    }

    Optional<Snapshot> readSnapshotFromJsonString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Snapshot snapshot = mapper.readValue(jsonString, Snapshot.class);
            return Optional.of(snapshot);
        } catch (IOException e) {
            LOG.error("Error mapping json string to Snapshot object");
            return Optional.empty();
        }
    }

    private Optional<TinkerGraph> convertSnapshotToTinkergraph(Snapshot snapshot) {
        TinkerGraph tinkerGraph = TinkerGraph.open();

        snapshot.getNodes().forEach(node ->
                tinkerGraph.addVertex(T.label, node.getType(), T.id, node.getName()));

        snapshot.getEdges().forEach(edge -> {
            Optional<Vertex> fromNode = getNodeFromTinkerpopGraph(tinkerGraph, edge.getFromNode());
            Optional<Vertex> toNode = getNodeFromTinkerpopGraph(tinkerGraph, edge.getToNode());

            if (fromNode.isPresent() && toNode.isPresent()) {
                fromNode.get().addEdge(edge.getType(), toNode.get());
            }
        });
        return Optional.of(tinkerGraph);
    }

    private Optional<Vertex> getNodeFromTinkerpopGraph(TinkerGraph tinkerGraph, String nodeName) {
        Iterator<Vertex> vertices = tinkerGraph.vertices(nodeName);
        if (vertices.hasNext()) {
            return Optional.of(vertices.next());
        }
        else {
            LOG.error("Error creating edge: node " + nodeName + " not found");
            return Optional.empty();
        }
    }
}
