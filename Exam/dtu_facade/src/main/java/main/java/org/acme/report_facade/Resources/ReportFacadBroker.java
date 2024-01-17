package main.java.org.acme.report_facade.Resources;

import com.google.gson.Gson;
import main.java.org.acme.payment_facade.Repositories.PaymentFacadeRepositories;
import main.java.org.acme.payment_facade.Resources.PaymentFacadeConfig;
import main.java.org.acme.report_facade.Reposotories.ReportFacadeRepositories;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import org.w3c.dom.events.Event;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ReportFacadBroker implements IEventSubscriber {
    CompletableFuture<String> waitFromessageReply = new CompletableFuture<>();
    ReportFacadeRepositories reportFacadeRepositories = ReportFacadeRepositories.getInstance();
    Message message;
    public void received() {
        try {
            EventSubscriber subscriber = new EventSubscriber(this);
            System.out.println(this.getClass().getSimpleName());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeEvent(Message message) throws Exception {
        Object[] payload = message.getPayload();
        String status = message.getStatus();
        UUID messageUuid = typeTransfer(payload[1], UUID.class);
        reportFacadeRepositories.getMessages();
        System.out.println(reportFacadeRepositories.getMessage(messageUuid));
        if (reportFacadeRepositories.getMessage(messageUuid)!=null) {
            waitFromessageReply.complete(status);
            reportFacadeRepositories.removeMessage(message.getMessageID());
        }
    }
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void sendReportRequestToReportService(String id, String event) {
        EventPublisher publisher = new EventPublisher();
        try {
            if (event == ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER){
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER,
                        "ReportBroker",
                        new Object[] { id });
            } else if (event == ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT) {
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT,
                        "ReportBroker",
                        new Object[] { id });
            } else if (event == ReportFacadeConfig.GENERATE_REPORT_DTU) {
                message = new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER,
                        "ReportBroker",
                        new Object[] {});
            }
            else {
                message = new Message(ReportFacadeConfig.ERROR_REPORT_EVENT,
                        "ReportBroker",
                        new Object[] {});
            }

            System.out.println(this.message.getMessageID());
            publisher.publishEvent(message);
            reportFacadeRepositories.addMessage(message);
            waitFromessageReply.join();
        } catch (Exception e) {
            waitFromessageReply.complete("404");
            e.printStackTrace();
        }
    }
}
