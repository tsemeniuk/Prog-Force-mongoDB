package starter;

import java.util.logging.Logger;

public class LogJobThread implements Runnable {
    public static final Logger LOG = Logger.getLogger(InitializerFoodStore.class.getName());

    public void run() {
        LOG.warning("Job is finished.");
    }
}
