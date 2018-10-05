package cx.samplecode.graphexperiment.converters;

import cx.samplecode.graphexperiment.model.Node;
import cx.samplecode.graphexperiment.model.Snapshot;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class JsonToGraphConverterTest {

    @Test
    void testImportJsonToGraph() {
        String jsonString = getJsonIncludingNodesAndEdges();

        JsonToGraphConverter converter = new JsonToGraphConverter();

        Optional<TinkerGraph> optionalTinkerGraph = converter.readJsonToTinkergraph(jsonString);
        assertTrue(optionalTinkerGraph.isPresent());

        TinkerGraph tinkergraph = optionalTinkerGraph.get();
        assertEquals(2, IteratorUtils.count(tinkergraph.vertices()));
        assertEquals(1, IteratorUtils.count(tinkergraph.edges()));
    }

    @Test
    void testReadSnapshotFromSimpleJSONString() {
        String simpleJsonString = "" +
                "{" +
                "  \"system\":\"MySystem\",\n" +
                "  \"revisionDate\":\"1970-01-01\",\n" +
                "  \"metadata\":{}\n" +
                "}";

        JsonToGraphConverter converter = new JsonToGraphConverter();

        Optional<Snapshot> optionalSnapshot = converter.readSnapshotFromJsonString(simpleJsonString);
        assertTrue(optionalSnapshot.isPresent());

        Snapshot snapshot = optionalSnapshot.get();
        assertEquals("MySystem", snapshot.getSystem());
        assertEquals("1970-01-01", snapshot.getRevisionDate());
    }

    @Test
    void testReadSnapshotIncludingNodesAndEdges() {
        String jsonString = getJsonIncludingNodesAndEdges();
        JsonToGraphConverter converter = new JsonToGraphConverter();

        Optional<Snapshot> optionalSnapshot = converter.readSnapshotFromJsonString(jsonString);
        assertTrue(optionalSnapshot.isPresent());

        Snapshot snapshot = optionalSnapshot.get();
        assertEquals(2, snapshot.getNodes().size());
        assertEquals(1, snapshot.getEdges().size());
    }

    @Test
    void testReadSnapshotIncludingMetrics() {
        String jsonString = getJsonIncludingMetrics();
                JsonToGraphConverter converter = new JsonToGraphConverter();

        Optional<Snapshot> optionalSnapshot = converter.readSnapshotFromJsonString(jsonString);
        assertTrue(optionalSnapshot.isPresent());

        Snapshot snapshot = optionalSnapshot.get();
        assertEquals(2, snapshot.getNodes().size());

        Node node = snapshot.getNodes().get(0);
        assertEquals("myType::3CPO", node.getName());

        Map<String, Map<String, Double>> metricsPerLanguageMap = node.getMetrics();
        assertEquals("myLanguage", metricsPerLanguageMap.keySet().iterator().next());
        Map<String, Double> myLanguageMetrics = metricsPerLanguageMap.get("myLanguage");
        assertEquals(3, myLanguageMetrics.size());
    }

    private String getJsonIncludingNodesAndEdges() {
        return "" +
                "{" +
                "  \"system\":\"MySystem\",\n" +
                "  \"revisionDate\":\"1970-01-01\",\n" +
                "  \"metadata\":{},\n" +
                "  \"nodes\": [" +
                "    {" +
                "      \"name\":\"myType::3CPO\",\n" +
                "      \"type\":\"myType\",\n" +
                "      \"inEdgesCount\":0,\n" +
                "      \"outEdgesCount\":1,\n" +
                "      \"metrics\":{}\n" +
                "    }," +
                "    {\"name\":\"myType::R2D2\",\n" +
                "      \"type\":\"myType\",\n" +
                "      \"inEdgesCount\":1,\n" +
                "      \"outEdgesCount\":0,\n" +
                "      \"metrics\":{}\n" +
                "    }\n" +
                "  ],\n" +
                "  \"edges\":[\n" +
                "    {\"type\":\"myEdge\",\n" +
                "      \"fromNode\":\"myType::3CPO\",\n" +
                "      \"toNode\":\"myType::R2D2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private String getJsonIncludingMetrics() {
        return "" +
                "{" +
                "  \"system\":\"MySystem\",\n" +
                "  \"revisionDate\":\"1970-01-01\",\n" +
                "  \"metadata\":{},\n" +
                "  \"nodes\":[\n" +
                "    {\"name\":\"myType::3CPO\",\n" +
                "      \"type\":\"myType\",\n" +
                "      \"inEdgesCount\":0,\n" +
                "      \"outEdgesCount\":1,\n" +
                "      \"metrics\":{" +
                "        \"myLanguage\":{\n" +
                "          \"METRIC1\":2.0,\n" +
                "          \"METRIC2\":1.0,\n" +
                "          \"METRIC3\":10.0\n" +
                "        }\n" +
                "      }\n" +
                "    }," +
                "    {\"name\":\"myType::R2D2\",\n" +
                "      \"type\":\"myType\",\n" +
                "      \"inEdgesCount\":1,\n" +
                "      \"outEdgesCount\":0,\n" +
                "      \"metrics\":{" +
                "        \"myLanguage\":{\n" +
                "          \"METRIC1\":3.0,\n" +
                "          \"METRIC2\":2.0,\n" +
                "          \"METRIC3\":11.0\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"edges\":[\n" +
                "    {\"type\":\"myEdge\",\n" +
                "      \"fromNode\":\"myType::3CPO\",\n" +
                "      \"toNode\":\"myType::R2D2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

}