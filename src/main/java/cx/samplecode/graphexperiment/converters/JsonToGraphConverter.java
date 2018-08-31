package cx.samplecode.graphexperiment.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.samplecode.graphexperiment.model.Snapshot;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class JsonToGraphConverter {

    private final static Logger LOG = Logger.getLogger(JsonToGraphConverter.class);

    public Optional<Snapshot> readSnapshotFromJsonString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Snapshot snapshot = mapper.readValue(jsonString, Snapshot.class);
            return Optional.of(snapshot);
        } catch (IOException e) {
            LOG.error("Error mapping json string to Snapshot object");
            return Optional.empty();
        }
    }
}
