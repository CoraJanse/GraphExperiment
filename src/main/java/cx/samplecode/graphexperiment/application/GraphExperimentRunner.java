package cx.samplecode.graphexperiment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.samplecode.graphexperiment.model.Snapshot;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraphExperimentRunner {

    private final static Logger LOG = Logger.getLogger(GraphExperimentRunner.class);

    public static void main(String[] args) {

        if (args.length > 0) {
            try {
                byte[] jsonData = Files.readAllBytes(Paths.get(args[0]));
                ObjectMapper objectMapper = new ObjectMapper();

                Snapshot snapshot = objectMapper.readValue(jsonData, Snapshot.class);
                LOG.info("Succesfully imported snapshot: " + snapshot);
            } catch (IOException e) {
                LOG.error("error reading json import file");
            }
        }
        else {
            LOG.info("json import file name not specified");
        }
    }
}
