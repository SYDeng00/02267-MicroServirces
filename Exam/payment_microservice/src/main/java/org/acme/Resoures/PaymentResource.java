package org.acme.Resoures;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
// @QuarkusMain
// public class PaymentResource {
//     static PaymentBroker paymentBroker;
//     public PaymentResource() {
//         try {
//             paymentBroker = new PaymentBroker();
//         } catch (Exception e) {
//             // Handle the exception or log it
//             e.printStackTrace();
//         }
        
//     }
   
    
//     public static void main(String[] args){
//         //PaymentBroker paymentBroker = new PaymentBroker();

//         PaymentResource paymentResource = new PaymentResource();
//         System.out.println("Payment service lauched, start Broker");
//         try {
//             paymentBroker.received();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         Quarkus.run();
//     }
// }

import io.quarkus.runtime.QuarkusApplication;

// @QuarkusMain
// public class PaymentResource implements QuarkusApplication {

//     static PaymentBroker paymentBroker;

//     public static void main(String[] args) {
//         Quarkus.run(PaymentResource.class, args);
//     }

//     @Override
//     public int run(String... args) throws Exception {
//         try {
//             paymentBroker = new PaymentBroker();
//         } catch (Exception e) {
//             // Handle the exception or log it
//             e.printStackTrace();
//         }

//         System.out.println("Payment service launched, start Broker");

//         try {
//             paymentBroker.received();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         // You can perform additional initialization or tasks here

//         return 0; // Exit code
//     }
// }


import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class PaymentResource implements QuarkusApplication {

    static PaymentBroker paymentBroker;

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

        System.out.println("Payment service launched, start Broker");

        try {
            paymentBroker.received();
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
