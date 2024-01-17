package org.acme.Repositories;


import org.acme.Domains.Report;
import org.acme.Interfaces.IReportRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReportRepository implements IReportRepository {
    private final HashMap<UUID, Report> reports = new HashMap<>();
    private List<Report> filteredReportList = new ArrayList<>();
    private List<String> returnReportList = new ArrayList<>();

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

        filteredReportList = (List<Report>) reports.values();
        for (Report r : filteredReportList) {
            // Perform actions with each report, for example, print its details
            String reportString = "ReportID =" + r.getReportId() + ";token=" + r.getToken() + ";amount=" + r.getAmount() + ";paytype=" + r.getPayType() + ";merchantId=" + r.getMerchantId()+ ";customerId=" + r.getCustomerId();
            // Add more details as needed
            returnReportList.add(reportString);
        }
        return returnReportList;
    }

 /*   @Override
    public List<Report> getReportsByStatus(String status) {
        return reports.values().stream()
                .filter(report -> report.getPayType().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }*/

    @Override
    public List<String> getReportsForCustomer(UUID customerId) {
        filteredReportList = reports.values().stream()
                .filter(report -> report.getCustomerId().equals(customerId))
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
}

