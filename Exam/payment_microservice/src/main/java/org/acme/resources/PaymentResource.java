package org.acme.resources;

import org.jboss.logging.Logger;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;

/**
 * This is the main clss of microservice,
 * used for automatically run the subscriber functions
 * 
 * @author Yingli
 * @version 1.0
 * 
 */

@QuarkusMain
public class PaymentResource implements QuarkusApplication {

    PaymentBroker paymentBroker;
    private static final Logger LOG = Logger.getLogger(PaymentResource.class);

    public static void main(String[] args) {
        Quarkus.run(PaymentResource.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        try {
            paymentBroker = new PaymentBroker();
        } catch (Exception e) {
            // Handle the exception or log it
            e.printStackTrace();
        }

        LOG.info("Payment service launched, start Broker");

        try {
            paymentBroker.received();
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
