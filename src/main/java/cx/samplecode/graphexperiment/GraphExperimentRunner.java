package cx.samplecode.graphexperiment;

import org.apache.log4j.Logger;

public class GraphExperimentRunner {

    private final static Logger LOG = Logger.getLogger(GraphExperimentRunner.class);

    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println(args[0]);
        }
        else {
            LOG.info("json import file name not specified");
        }
    }
}
