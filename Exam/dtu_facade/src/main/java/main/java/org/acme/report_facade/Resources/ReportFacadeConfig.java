package main.java.org.acme.report_facade.Resources;



/**
 * Configuration class for report-related constants.
 * @author Tama Sarker
 */
public class ReportFacadeConfig {
    private ReportFacadeConfig() {
        throw new UnsupportedOperationException("This class should not be instantiated.");
    }
    public static final String GENERATE_REPORT_EVENT = "GenerateReportEvent";
    public static final String ERROR_REPORT_EVENT = "ErrorReportEvent";
    public static final String RETRIEVE_REPORT_EVENT = "RetrieveReportEvent";
    public static final String GENERATE_REPORT_FOR_CUSTOMER = "GenerateReportForCustomer";
    public static final String GENERATE_LATEST_REPORT_FOR_CUSTOMER = "GenerateLatestReportForCustomer";
    public static final String RETRIEVE_REPORT_FOR_CUSTOMER = "RetrieveReportForCustomer";
    public static final String RETRIEVE_LATEST_REPORT_FOR_CUSTOMER = "RetrieveLatestReportForCustomer";
    public static final String GENERATE_REPORT_FOR_MERCHANT = "GenerateReportForMerchant";
    public static final String GENERATE_LATEST_REPORT_FOR_MERCHANT = "GenerateLatestReportForMerchant";
    public static final String RETRIEVE_REPORT_FOR_MERCHANT = "RetrieveReportForMerchant";
    public static final String RETRIEVE_LATEST_REPORT_FOR_MERCHANT = "RetrieveLatestReportForMerchant";
    public static final String GENERATE_REPORT_DTU = "GenerateReportForDTU";
    public static final String GENERATE_LATEST_REPORT_DTU = "GenerateLatestReportForDTU";
    public static final String SEND_UPDATE_PAYMENTS_REPORT = "ReceiveReportForReportService";
    public static final String RETRIEVE_REPORT_DTU = "RetrieveReportForDTU";
    public static final String RETRIEVE_LATEST_REPORT_DTU = "RetrieveLatestReportForDTU";

}

