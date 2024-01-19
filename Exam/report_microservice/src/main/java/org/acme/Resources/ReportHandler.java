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

/**
 * Configuration class for report-facade broker.
 * 
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
		LOG.info("Generated report for customer with ID: " + customerId + " " + ReportConfig.RETRIEVE_REPORT_EVENT
				+ "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { customerReports }));
	}

	public void generateLatestCustomerReport(Object[] payload) throws Exception {
		UUID customerId = typeTransfer(payload[0], UUID.class);
		List<String> customerReports = reportRepository.getLatestReportsForCustomer(customerId);
		LOG.info("Generated Latest 5 Payment  report for customer with ID: " + customerId + " "
				+ ReportConfig.RETRIEVE_REPORT_EVENT + "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { customerReports }));
	}

	public void generateMerchantReport(Object[] payload) throws Exception {
		UUID merchantId = typeTransfer(payload[0], UUID.class);
		List<String> merchantReports = reportRepository.getReportsForMerchant(merchantId);
		LOG.info("Generated report for customer with ID: " + merchantId + " " + ReportConfig.RETRIEVE_REPORT_EVENT
				+ "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { merchantReports }));
	}

	public void generateLatestMerchantReport(Object[] payload) throws Exception {
		UUID merchantId = typeTransfer(payload[0], UUID.class);
		List<String> merchantReports = reportRepository.getLatestReportsForMerchant(merchantId);
		LOG.info("Generated report for customer with ID: " + merchantId + " " + ReportConfig.RETRIEVE_REPORT_EVENT
				+ "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { merchantReports }));
	}

	public void generateSummaryReport() throws Exception {
		List<String> summaryReports = reportRepository.getAllReports();
		LOG.info("Generated report for DTUPay: " + ReportConfig.RETRIEVE_REPORT_EVENT + "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { summaryReports }));
	}

	public void generateLatestSummaryReport() throws Exception {
		List<String> summaryReports = reportRepository.getLatestReports();
		LOG.info("Generated report for DTUPay: " + ReportConfig.RETRIEVE_REPORT_EVENT + "-->");
		eventPublisher.publishEvent(new Message(ReportConfig.RETRIEVE_REPORT_EVENT, "ReportFacadeResources",
				new Object[] { summaryReports }));
	}

	public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
		Gson gson = new Gson();
		String json = gson.toJson(payload);
		return gson.fromJson(json, objectClass);
	}

	public void addReport(Object[] payload) {
		Report r = new Report();
		r.setPayType(payload[0].toString());
		r.setPayOrRefundUUID(typeTransfer(payload[1], UUID.class));
		r.setCustomerId(typeTransfer(payload[2], UUID.class));
		r.setMerchantId(typeTransfer(payload[3], UUID.class));
		r.setAmount(BigDecimal.valueOf((Double) payload[4]));
		r.setToken(typeTransfer(payload[5], UUID.class));
		r.setDateTime(LocalDateTime.now());
		System.out.println(r.getPayType());
		System.out.println(r.getCustomerId());
		reportRepository.addReport(r);
	}
}
