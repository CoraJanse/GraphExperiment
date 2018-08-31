package cx.samplecode.graphexperiment.application;

import cx.samplecode.graphexperiment.converters.JsonToGraphConverter;
import cx.samplecode.graphexperiment.model.Snapshot;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Optional;

public class GraphExperimentRunner {

    private final static Logger LOG = Logger.getLogger(GraphExperimentRunner.class);

    public static void main(String[] args) {

        if (args.length > 0) {
            String jsonPath = Paths.get(args[0]).toString();
            String jsonString;

            try {
                FileInputStream inputStream = new FileInputStream(jsonPath);
                jsonString = IOUtils.toString(inputStream, Charset.defaultCharset());

                Optional<Snapshot> optionalSnapshot = new JsonToGraphConverter().readSnapshotFromJsonString(jsonString);

                if (optionalSnapshot.isPresent()) {
                    LOG.info("Succesfully imported snapshot: " + optionalSnapshot.get());
                }
                else {
                    LOG.info("No snapshot found in json import file " + jsonPath);
                }
            } catch (IOException e) {
                LOG.error("Error reading json import file " + jsonPath);
            }
        }
        else {
            LOG.info("No json import file name specified");
        }
    }
}
