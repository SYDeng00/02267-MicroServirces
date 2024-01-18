package org.acme.Repositories;


import org.acme.Domains.Report;
import org.acme.Interfaces.IReportRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Configuration class for report.
 * @author Tama Sarker
 * @author Divya
 */
public class ReportRepository implements IReportRepository {
    private static HashMap<UUID, Report> reports = new HashMap<>();
    private Collection<Report> filteredReportList = new ArrayList<>();
    private List<String> returnReportList = new ArrayList<>();

    {
        UUID reportId1 = UUID.randomUUID();
        UUID reportId2 = UUID.randomUUID();
        UUID customerId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        UUID merchantId1 = UUID.fromString("123r4567-e89b-12d3-a456-426614174001");
        UUID customerId2 =  UUID.fromString("111e2222-e89b-12d3-a456-426614174003");
        UUID merchantId2 = UUID.fromString("987e6543-e89b-12d3-a456-426614174002");

        // Create LocalDateTime instances for demo purposes
        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDateTime dateTime2 = LocalDateTime.now().minusDays(1);

        // Create demo reports
        Report demoReport1 = new Report(reportId1, UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("100.00"), customerId1, merchantId1, dateTime1, "Credit");
        Report demoReport2 = new Report(reportId2, UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("150.50"), customerId2, merchantId2, dateTime2, "Debit");

        // Add demo reports to the reports map
        reports.put(demoReport1.getReportId(), demoReport1);
        reports.put(demoReport2.getReportId(), demoReport2);
    }

    @Override
    public void addReport(Report report) {
        reports.put(UUID.randomUUID(), report);
    }

    @Override
    public Report getReport(UUID reportId) {
        return reports.get(reportId);
    }

    @Override
    public List<String> getAllReports() {
        filteredReportList = reports.values();
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString = "ReportID =" + r.getReportId() + ";token=" + r.getToken() + ";amount=" + r.getAmount() + ";paytype=" + r.getPayType() + ";merchantId=" + r.getMerchantId()+ ";customerId=" + r.getCustomerId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }
    @Override
    public List<String> getLatestReports() {

        filteredReportList = reports.values().stream()
                .sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .collect(Collectors.toList());
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString = "ReportID =" + r.getReportId() + ";token=" + r.getToken() + ";amount=" + r.getAmount() + ";paytype=" + r.getPayType() + ";merchantId=" + r.getMerchantId()+ ";customerId=" + r.getCustomerId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }
    @Override
    public List<String> getReportsForCustomer(UUID customerId) {
       /* filteredReportList = reports.values().stream()
                .filter(report -> report.getCustomerId().equals(customerId))
                .collect(Collectors.toList());*/
        returnReportList = null;
        filteredReportList.addAll(reports.values());
        if (filteredReportList!=null){
            for (Report r : filteredReportList) {
                // Perform actions with each report, for example, print its details
                if (r.getCustomerId()==customerId){
                    String reportString = "ReportID =" + r.getReportId() + ";token=" + r.getToken() + ";amount=" + r.getAmount() + ";paytype=" + r.getPayType() + ";merchantId=" + r.getMerchantId();
                    // Add more details as needed
                    System.out.println(reportString);
                    returnReportList.add(reportString);
                }
            }
        }

        return returnReportList;
    }
    @Override
    public List<String> getLatestReportsForCustomer(UUID customerId) {
        filteredReportList = reports.values().stream()
                .filter(report -> report.getCustomerId().equals(customerId)).sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .collect(Collectors.toList());
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString = "ReportID =" + r.getReportId() + ";token=" + r.getToken() + ";amount=" + r.getAmount() + ";paytype=" + r.getPayType() + ";merchantId=" + r.getMerchantId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }

    @Override
    public List<String> getReportsForMerchant(UUID merchantId) {

        filteredReportList = reports.values().stream()
                .filter(report -> report.getMerchantId().equals(merchantId))
                .collect(Collectors.toList());
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString ="ReportID =" +r.getReportId()+ ";token="+r.getToken()+";amount="+r.getAmount()+";paytype="+r.getPayType()+";merchantId="+r.getMerchantId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }
    @Override
    public List<String> getLatestReportsForMerchant(UUID merchantId) {

        filteredReportList = reports.values().stream()
                .filter(report -> report.getCustomerId().equals(merchantId)).sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .collect(Collectors.toList());
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString ="ReportID =" +r.getReportId()+ ";token="+r.getToken()+";amount="+r.getAmount()+";paytype="+r.getPayType()+";merchantId="+r.getMerchantId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }
}

