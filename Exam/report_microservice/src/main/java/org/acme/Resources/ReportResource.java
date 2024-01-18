package org.acme.Resources;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.jboss.logging.Logger;
/**
 * Configuration class for report Resource.
 * @author Divya
 */
@QuarkusMain
public class ReportResource implements QuarkusApplication {

    static ReportBroker reportBroker = new ReportBroker();
    private static final Logger LOG = Logger.getLogger(ReportResource.class);

    public static void main(String[] args) {
        Quarkus.run(ReportResource.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        try {
            reportBroker = new ReportBroker();
        } catch (Exception e) {
            // Handle the exception or log it
            e.printStackTrace();
        }

        LOG.info("Report microservice launched, start Broker");

        try {
            reportBroker.received();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startLongRunningTask();
        Quarkus.waitForExit();

        return 0; // This line won't be reached until the application is stopped
    }

    private void startLongRunningTask() {
        // Example: Start a background thread
        Thread backgroundThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                // Perform some long-running task
                try {
                    Thread.sleep(5000); // Sleep for 5 seconds as an example
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        backgroundThread.setDaemon(true); // Set to true if you want it to be a daemon thread
        backgroundThread.start();
    }
}

