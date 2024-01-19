package org.acme.Resources;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;

/*
Author: Siyuan Deng s232275
*/

@QuarkusMain
public class AccountResource implements QuarkusApplication {

    static AccountBroker accountBroker;

    public static void main(String[] args) {
        Quarkus.run(AccountResource.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        try {
            accountBroker = new AccountBroker();
        } catch (Exception e) {
            //Handle or log exceptions
            e.printStackTrace();
        }

        System.out.println("Account service launched, starting Broker");

        try {
            accountBroker.received();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Run a long-running task or thread
        startLongRunningTask();

        //Keep the application running
        Quarkus.waitForExit();

        return 0; //This line will not execute until the application stops
    }

    private void startLongRunningTask() {
        //Start background thread
        Thread backgroundThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                //Perform some long-running tasks
                try {
                    Thread.sleep(5000); //Sleep for 5 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        backgroundThread.setDaemon(true); //Set to true to create a daemon thread
        backgroundThread.start();
    }
}
