package org.acme.Resources;


import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventSubscriber;
import org.jboss.logging.Logger;


/**
 * Configuration class for report broker.
 * @author Tama Sarker
 */

public class ReportBroker implements IEventSubscriber {
    private ReportHandler reportHandler = new ReportHandler();
    private static final Logger LOG = Logger.getLogger(ReportBroker.class);

    @Override
    public void subscribeEvent(Message message) throws Exception {
        String eventType = message.getEventType();
        Object[] payload = message.getPayload();
        LOG.info("Event type for report: " + eventType);

        switch (eventType) {
            case ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER:
                LOG.info("-------------------------------Customer Report generation request received");
                reportHandler.generateCustomerReport(payload);
                break;
            case ReportConfig.RETRIEVE_LATEST_REPORT_FOR_CUSTOMER:
                LOG.info("-------------------------------Customer Latest 5 Payment Report generation request received");
                reportHandler.generateLatestCustomerReport(payload);
                break;
            case ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT:
                LOG.info("-------------------------------Merchant Report retrieval request received");
               reportHandler.generateMerchantReport(payload);
                break;
            case ReportConfig.RETRIEVE_LATEST_REPORT_FOR_MERCHANT:
                LOG.info("-------------------------------Merchant Latest 5 Payment Report retrieval request received");
                reportHandler.generateLatestMerchantReport(payload);
                break;
            case ReportConfig.RETRIEVE_REPORT_DTU:
                LOG.info("-------------------------------DTU Report retrieval request received");
                reportHandler.generateSummaryReport();
                break;
            case ReportConfig.RETRIEVE_LATEST_REPORT_DTU:
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
            EventSubscriber subscriber = new EventSubscriber(this);
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            LOG.error("Error in subscribing to events", e);
        }
    }
}

