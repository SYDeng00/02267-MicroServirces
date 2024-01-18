package org.acme.Resources;



import com.google.gson.Gson;
import org.acme.Domains.Message;
import org.acme.Domains.Report;
import org.acme.Repositories.ReportRepository;
import org.acme.Resoures.EventPublisher;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Configuration class for report-facade broker.
 * @author Tama Sarker
 * @author Divya
 */
public class ReportHandler {
    EventPublisher eventPublisher = new EventPublisher();
    private ReportRepository reportRepository = new ReportRepository();
    private static final Logger LOG = Logger.getLogger(ReportHandler.class);

    public void generateCustomerReport(Object[] payload) throws Exception {
        UUID customerId = typeTransfer(payload[0], UUID.class);
        List<String> customerReports = reportRepository.getReportsForCustomer(customerId);
        LOG.info("Generated report for customer with ID: " + customerId+ " " +  ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER, "ReportFacadBroker", new Object[] { customerReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_FOR_CUSTOMER);
    }
    public void generateLatestCustomerReport(Object[] payload) throws Exception {
        UUID customerId = typeTransfer(payload[0], UUID.class);
        List<String> customerReports = reportRepository.getLatestReportsForCustomer(customerId);
        LOG.info("Generated Latest 5 Payment  report for customer with ID: " + customerId+ " " +  ReportConfig.RETRIEVE_LATEST_REPORT_FOR_CUSTOMER + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_LATEST_REPORT_FOR_CUSTOMER, "ReportFacadBroker", new Object[] { customerReports.toString() }));
        LOG.info("Report microservice send message to Report Facade:" + ReportConfig.RETRIEVE_LATEST_REPORT_FOR_CUSTOMER);
    }
    public void generateMerchantReport(Object[] payload) throws Exception {
        UUID merchantId = typeTransfer(payload[0], UUID.class);
        List<String> merchantReports = reportRepository.getReportsForMerchant(merchantId);
        LOG.info("Generated report for customer with ID: " + merchantId+ " " +  ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT, "ReportFacadBroker", new Object[] { merchantReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_FOR_MERCHANT);
    }
    public void generateLatestMerchantReport(Object[] payload) throws Exception {
        UUID merchantId = typeTransfer(payload[0], UUID.class);
        List<String> merchantReports = reportRepository.getLatestReportsForMerchant(merchantId);
        LOG.info("Generated report for customer with ID: " + merchantId+ " " +  ReportConfig.RETRIEVE_LATEST_REPORT_FOR_MERCHANT + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_LATEST_REPORT_FOR_MERCHANT, "ReportFacadBroker", new Object[] { merchantReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_LATEST_REPORT_FOR_MERCHANT);
    }
    public void generateSummaryReport() throws Exception {
        List<String> summaryReports = reportRepository.getAllReports();
        LOG.info("Generated report for DTUPay: " +  ReportConfig.RETRIEVE_REPORT_DTU + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_REPORT_DTU, "ReportFacadBroker", new Object[] { summaryReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_REPORT_DTU);
    }
    public void generateLatestSummaryReport() throws Exception {
        List<String> summaryReports = reportRepository.getLatestReports();
        LOG.info("Generated report for DTUPay: " +  ReportConfig.RETRIEVE_LATEST_REPORT_DTU + "-->");
        eventPublisher.publishEvent(
                new Message(ReportConfig.RETRIEVE_LATEST_REPORT_DTU, "ReportFacadBroker", new Object[] { summaryReports.toString() }));
        LOG.info("Report microservce send message to Report Facade:" + ReportConfig.RETRIEVE_LATEST_REPORT_DTU);
    }
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void addReport(Object[] payload) {
        Report r = new Report();
        r.setPayType(payload[0].toString());
        r.setPayOrRefundUUID(typeTransfer(payload[0], UUID.class));
        r.setCustomerId(typeTransfer(payload[2], UUID.class));
        r.setMerchantId(typeTransfer(payload[3], UUID.class));
        r.setAmount(BigDecimal.valueOf((Double) payload[4]));
        r.setToken(typeTransfer(payload[5], UUID.class));
        r.setDateTime(LocalDateTime.now());
        reportRepository.addReport(r);
    }
}



