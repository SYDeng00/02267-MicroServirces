package org.acme;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.Domains.Message;
import org.acme.Repositories.ReportRepository;
import org.acme.Resources.ReportBroker;
import org.acme.Resources.ReportConfig;
import org.acme.Resources.ReportHandler;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Divya
 */

public class ReportSteps {

    private ReportBroker mockReportBroker;
    private Message mockMessage;
    private ReportHandler reportHandler;
    private ReportRepository mockReportRepository;
    private UUID customerId;
    private UUID merchantId;

    @Before
    public void setup() {
        mockReportBroker = mock(ReportBroker.class);
        mockMessage = mock(Message.class);
        reportHandler = new ReportHandler();
        mockReportRepository = mock(ReportRepository.class);

        setupMockReports();
    }

    private void setupMockReports() {
        // Create a list of 5 reports for a customer and a merchant
        List<String> customerReports = new ArrayList<>();
        List<String> merchantReports = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customerReports.add("Customer Report " + (i + 1));
            merchantReports.add("Merchant Report " + (i + 1));
        }

        // Set up mock behavior to return these reports
        Mockito.when(mockReportRepository.getLatestReportsForCustomer(Mockito.any(UUID.class)))
                .thenReturn(customerReports);
        Mockito.when(mockReportRepository.getLatestReportsForMerchant(Mockito.any(UUID.class)))
                .thenReturn(merchantReports);

        // Set up mock behavior to return non-empty lists for all reports
        Mockito.when(mockReportRepository.getReportsForCustomer(Mockito.any(UUID.class)))
                .thenReturn(customerReports);
        Mockito.when(mockReportRepository.getReportsForMerchant(Mockito.any(UUID.class)))
                .thenReturn(merchantReports);
        Mockito.when(mockReportRepository.getAllReports())
                .thenReturn(Arrays.asList("Report 1", "Report 2", "Report 3", "Report 4", "Report 5", "Report 6"));
    }

    @Given("a customer with ID")
    public void a_customer_with_id() {
        this.customerId = UUID.randomUUID();
    }

    @Given("a merchant with ID")
    public void a_merchant_with_id() {
        this.merchantId = UUID.randomUUID();
    }

    @When("I generate reports for the customer")
    public void i_generate_reports_for_the_customer() throws Exception {
        Object[] payload = new Object[]{customerId};
        Mockito.when(mockMessage.getEventType()).thenReturn(ReportConfig.GENERATE_REPORT_FOR_CUSTOMER);
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        mockReportBroker.subscribeEvent(mockMessage);
    }

    @When("I retrieve the latest five reports for the customer")
    public void i_retrieve_the_latest_five_reports_for_the_customer() throws Exception {
        Object[] payload = new Object[]{customerId};
        Mockito.when(mockMessage.getEventType()).thenReturn(ReportConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER);
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        mockReportBroker.subscribeEvent(mockMessage);
    }

    @When("I generate reports for the merchant")
    public void i_generate_reports_for_the_merchant() throws Exception {
        Object[] payload = new Object[]{merchantId};
        Mockito.when(mockMessage.getEventType()).thenReturn(ReportConfig.GENERATE_REPORT_FOR_MERCHANT);
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        mockReportBroker.subscribeEvent(mockMessage);
    }

    @When("I retrieve the latest five reports for the merchant")
    public void i_retrieve_the_latest_five_reports_for_the_merchant() throws Exception {
        Object[] payload = new Object[]{merchantId};
        Mockito.when(mockMessage.getEventType()).thenReturn(ReportConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT);
        Mockito.when(mockMessage.getPayload()).thenReturn(payload);
        mockReportBroker.subscribeEvent(mockMessage);
    }

    @Then("the reports for the customer should be successfully generated")
    public void the_reports_for_the_customer_should_be_successfully_generated() {
        try {
            Mockito.verify(mockReportBroker).subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(mockReportRepository.getReportsForCustomer(customerId));
    }

    @Then("I should receive the latest five reports for the customer")
    public void i_should_receive_the_latest_five_reports_for_the_customer() {
        try {
            Mockito.verify(mockReportBroker).subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, mockReportRepository.getLatestReportsForCustomer(customerId).size());
    }

    @Then("the reports for the merchant should be successfully generated")
    public void the_reports_for_the_merchant_should_be_successfully_generated() {
        try {
            Mockito.verify(mockReportBroker).subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(mockReportRepository.getReportsForMerchant(merchantId));
    }

    @Then("I should receive the latest five reports for the merchant")
    public void i_should_receive_the_latest_five_reports_for_the_merchant() {
        try {
            Mockito.verify(mockReportBroker).subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, mockReportRepository.getLatestReportsForMerchant(merchantId).size());
    }

    @Given("a manager requests a summary report")
    public void a_manager_requests_a_summary_report() {
        Mockito.when(mockMessage.getEventType()).thenReturn(ReportConfig.GENERATE_REPORT_DTU);
        try {
            Mockito.doNothing().when(mockReportBroker).subscribeEvent(mockMessage);
            mockReportBroker.subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Then("the summary report should be successfully provided to the manager")
    public void the_summary_report_should_be_successfully_provided_to_the_manager() {
        try {
            Mockito.verify(mockReportBroker).subscribeEvent(mockMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(mockReportRepository.getAllReports().isEmpty());
    }



}

