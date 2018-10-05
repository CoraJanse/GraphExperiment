package cx.samplecode.graphexperiment.application;

import cx.samplecode.graphexperiment.converters.JsonToGraphConverter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

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

                Optional<TinkerGraph> optionalTinkerGraph = new JsonToGraphConverter().readJsonToTinkergraph(jsonString);

                if (optionalTinkerGraph.isPresent()) {
                    LOG.info("Succesfully converted json to TinkerGraph");
                }
                else {
                    LOG.error("Conversion of json failed for " + jsonPath);
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
