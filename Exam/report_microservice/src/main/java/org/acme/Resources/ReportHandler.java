package org.acme.Resources;



import com.google.gson.Gson;
import org.acme.Domains.Message;
import org.acme.Domains.Report;
import org.acme.Repositories.ReportRepository;
import org.acme.Resoures.EventPublisher;
import org.jboss.logging.Logger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is used for handling all report-related operations in DTU Pay.
 */
public class ReportHandler {
    EventPublisher eventPublisher = new EventPublisher();
    private ReportRepository reportRepository = new ReportRepository();
    private static final Logger LOG = Logger.getLogger(ReportHandler.class);

    /**
     * Generates a report for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of reports for the given customer.
     */
    public void generateCustomerReport(Object[] payload) throws Exception {
        UUID customerId = typeTransfer(payload[0], UUID.class);
        List<Report> customerReports = reportRepository.getReportsForCustomer(customerId);
        LOG.info("Generated report for customer with ID: " + customerId+ " " +  ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER, "ReportFacadBroker", new Object[] { customerReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER);
    }

    /**
     * Generates a report for a specific merchant.
     *
     * @param merchantId The ID of the merchant.
     * @return A list of reports for the given merchant.
     */
    public void generateMerchantReport(Object[] payload) throws Exception {
        UUID merchantId = typeTransfer(payload[0], UUID.class);
        List<Report> merchantReports = reportRepository.getReportsForMerchant(merchantId).stream()
                .map(report -> new Report(report.getReportId(), report.getTransactionId(), report.getAmount(),
                        report.getDateTime(), null, report.getMerchantId(), report.getStatus()))
                .collect(Collectors.toList());
        LOG.info("Generated report for customer with ID: " + merchantId+ " " +  ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT, "ReportFacadBroker", new Object[] { merchantReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT);
    }

    /**
     * Generates a summary report for the manager.
     *
     * @return A list of all reports.
     */
    public void generateSummaryReport() throws Exception {
        List<Report> summaryReports = reportRepository.getAllReports();
        LOG.info("Generated report for DTUPay: " +  ReportConfig.RETRIEVE_REPORT_DTU + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_DTU, "ReportFacadBroker", new Object[] { summaryReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_DTU);
    }
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

}



