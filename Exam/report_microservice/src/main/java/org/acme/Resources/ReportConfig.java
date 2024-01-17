package org.acme.Resources;



/**
 * Configuration class for report-related constants.
 */
public class ReportConfig {
    private ReportConfig() {
        throw new UnsupportedOperationException("This class should not be instantiated.");
    }

    // Event triggered to generate a new report
    public static final String GENERATE_REPORT_EVENT = "GenerateReportEvent";
    // Payload structure: {UUID->customerId/merchantId, String->reportType, LocalDateTime->startDate, LocalDateTime->endDate}
    // reportType can be "customer", "merchant", or "summary"

    // Event triggered to retrieve an existing report
    public static final String RETRIEVE_REPORT_EVENT = "RetrieveReportEvent";
    // Payload structure: {UUID->reportId}
    public static final String ERROR_REPORT_EVENT = "ErrorReportEvent";

    // Constant for customer-specific report request
    public static final String GENERATE_REPORT_FOR_CUSTOMER = "GenerateReportForCustomer";
    public static final String RETRIEVE_REPORT_FOR_CUSTOMER = "RetrieveReportForCustomer";
    // Used to specify that the report being requested/generated is for a specific customer

    // Constant for merchant-specific report request
    public static final String GENERATE_REPORT_FOR_MERCHANT = "GenerateReportForMerchant";
    public static final String RETRIEVE_REPORT_FOR_MERCHANT = "RetrieveReportForMerchant";
    // Used to specify that the report being requested/generated is for a specific merchant

    // Constant for summary report request
    public static final String GENERATE_REPORT_DTU = "GenerateReportForDTU";
    public static final String RETRIEVE_REPORT_DTU = "RetrieveReportForDTU";
    // Used for generating a summary report that includes overall transaction data

    // Add more constants as needed based on your system's requirements
}

