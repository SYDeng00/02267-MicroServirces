package org.acme.Resources;



import org.acme.Domains.Report;
import org.acme.Repositories.ReportRepository;
import org.jboss.logging.Logger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is used for handling all report-related operations in DTU Pay.
 */
public class ReportHandler {
    private ReportRepository reportRepository = new ReportRepository();
    private static final Logger LOG = Logger.getLogger(ReportHandler.class);

    /**
     * Generates a report for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of reports for the given customer.
     */
    public List<Report> generateCustomerReport(UUID customerId) {
        List<Report> customerReports = reportRepository.getReportsForCustomer(customerId);
        LOG.info("Generated report for customer with ID: " + customerId);
        return customerReports;
    }

    /**
     * Generates a report for a specific merchant.
     *
     * @param merchantId The ID of the merchant.
     * @return A list of reports for the given merchant.
     */
    public List<Report> generateMerchantReport(UUID merchantId) {
        List<Report> merchantReports = reportRepository.getReportsForMerchant(merchantId).stream()
                .map(report -> new Report(report.getReportId(), report.getTransactionId(), report.getAmount(),
                        report.getDateTime(), null, report.getMerchantId(), report.getStatus()))
                .collect(Collectors.toList());
        LOG.info("Generated report for merchant with ID: " + merchantId);
        return merchantReports;
    }

    /**
     * Generates a summary report for the manager.
     *
     * @return A list of all reports.
     */
    public List<Report> generateSummaryReport() {
        List<Report> summaryReports = reportRepository.getAllReports();
        LOG.info("Generated summary report for the manager");
        return summaryReports;
    }
}



