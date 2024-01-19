package org.acme.Resource;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * @author Yingli, Divya
 */
@QuarkusMain
public class TokenResource implements QuarkusApplication {

    static TokenBroker tokenBroker;

    public static void main(String[] args) {
        Quarkus.run(TokenResource.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        try {
            tokenBroker = new TokenBroker();
        } catch (Exception e) {
            // Handle the exception or log it
            e.printStackTrace();
        }

        System.out.println("Token service launched, start Broker");

        try {
            System.out.println("in main");
            tokenBroker.received();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run a long-lived task or thread
        startLongRunningTask();

        // Keep the application running
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