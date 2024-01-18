package org.acme.Resources;


import com.google.gson.Gson;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import org.jboss.logging.Logger;

import static io.quarkus.arc.ComponentsProvider.LOG;


/**
 * Configuration class for report broker.
 * @author Tama Sarker
 * @author Divya
 */

public class ReportBroker implements IEventSubscriber {
    EventPublisher eventPublisher = new EventPublisher();
    private ReportHandler reportHandler = new ReportHandler();
    @Override
    public void subscribeEvent(Message message) throws Exception {
        String eventType = message.getEventType();
        Object[] payload = message.getPayload();
        switch (eventType) {
            case ReportConfig.GENERATE_REPORT_FOR_CUSTOMER:
                reportHandler.generateCustomerReport(payload);
                LOG.info("-------------------------------Customer Report generation request received");
                break;
            case ReportConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER:
                LOG.info("-------------------------------Customer Latest 5 Payment Report generation request received");
                reportHandler.generateLatestCustomerReport(payload);
                break;
            case ReportConfig.GENERATE_REPORT_FOR_MERCHANT:
                LOG.info("-------------------------------Merchant Report retrieval request received");
                reportHandler.generateMerchantReport(payload);
                break;
            case ReportConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT:
                LOG.info("-------------------------------Merchant Latest 5 Payment Report retrieval request received");
                reportHandler.generateLatestMerchantReport(payload);
                break;
            case ReportConfig.GENERATE_REPORT_DTU:
                LOG.info("-------------------------------DTU Report retrieval request received");
                reportHandler.generateSummaryReport();
                break;
            case ReportConfig.GENERATE_LATEST_REPORT_DTU:
                LOG.info("-------------------------------Latest 5 Payment Report retrieval request received");
                reportHandler.generateLatestSummaryReport();
                break;
            case ReportConfig.SEND_UPDATE_PAYMENTS_REPORT:
                LOG.info("-------------------------------DTU Report Repository update request received");
                reportHandler.addReport(payload);
                break;
            // Add additional cases for other report-related events
            default:
                LOG.warn("Unhandled event type: " + eventType);
                break;
        }
    }
    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new ReportBroker());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
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

