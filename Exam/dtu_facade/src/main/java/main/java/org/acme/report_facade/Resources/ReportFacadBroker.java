/*
package main.java.org.acme.report_facade.Resources;

import com.google.gson.Gson;
import main.java.org.acme.report_facade.Reposotories.ReportFacadeRepositories;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

*/
/**
 * Configuration class for report-facade broker.
 * @author Tama Sarker
 *//*

public class ReportFacadBroker implements IEventSubscriber {
    CompletableFuture<String> waitFromessageReply = new CompletableFuture<>();
    ReportFacadeRepositories reportFacadeRepositories = ReportFacadeRepositories.getInstance();
    Message message;
    String fianlStatus;
    public void sendReportRequestToReportService(String id, String event) {
        EventPublisher publisher = new EventPublisher();
        System.out.println("In if else fn: event="+ event);
        try {
            if (Objects.equals(event, ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER)) {
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER,
                        "ReportBroker",
                        new Object[]{id});
            }
            if (Objects.equals(event, ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER)) {
                message = new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER,
                        "ReportBroker",
                        new Object[]{id});
            } else if (Objects.equals(event, ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT)) {
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT,
                        "ReportBroker",
                        new Object[]{id});
            } else if (Objects.equals(event, ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT)) {
                message = new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT,
                        "ReportBroker",
                        new Object[]{id});
            } else if (Objects.equals(event, ReportFacadeConfig.GENERATE_REPORT_DTU)) {
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_DTU,
                        "ReportBroker",
                        new Object[]{});
            } else if (Objects.equals(event, ReportFacadeConfig.GENERATE_LATEST_REPORT_DTU)) {
                message = new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_DTU,
                        "ReportBroker",
                        new Object[]{});
            } else {
                message = new Message(ReportFacadeConfig.ERROR_REPORT_EVENT,
                        "ReportBroker",
                        new Object[]{});
            }

            System.out.println("Here:"+ this.message.getMessageID());
            publisher.publishEvent(message);
            reportFacadeRepositories.addMessage(message);
            waitFromessageReply.join();
        } catch (Exception e) {
            waitFromessageReply.complete("404");
            e.printStackTrace();
        }
    }
    @Override
    public void subscribeEvent(Message message) throws Exception {
        Object[] payload = message.getPayload();
        String status = message.getStatus();
        System.out.println("In Subscribe");
        UUID id = typeTransfer(payload[0], UUID.class);
        System.out.println("customerUuid:" + id);
        reportFacadeRepositories.getMessages();
        System.out.println(reportFacadeRepositories.getMessage(id));
        if (reportFacadeRepositories.getMessage(id)!=null) {
            waitFromessageReply.complete(status);
            reportFacadeRepositories.removeMessage(message.getMessageID());
        }
    }
    public void received() throws Exception{
        try {
            System.out.println("In Receive");
            EventSubscriber subscriber = new EventSubscriber(this);
            System.out.println("Bal"+this.getClass().getSimpleName());
            subscriber.subscribeEvent(String.valueOf(this.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }
}
*/
