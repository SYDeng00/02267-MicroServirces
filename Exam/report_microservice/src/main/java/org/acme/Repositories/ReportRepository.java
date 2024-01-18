package org.acme.Repositories;

import org.acme.Domains.Report;
import org.acme.Interfaces.IReportRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReportRepository implements IReportRepository {
    private static final HashMap<UUID, Report> reports = new HashMap<>();
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
        List<String> allReports = new ArrayList<>();
        for (Report report : reports.values()) {
            String reportInfo = createReportString(report);
            allReports.add(reportInfo);
        }
        return allReports;
    }

    @Override
    public List<String> getLatestReports() {
        return reports.values().stream()
                .sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .map(this::createReportString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReportsForCustomer(UUID customerId) {
        List<String> reportsForCustomer = new ArrayList<>();
        for (Report report : reports.values()) {
            if (customerId.equals(report.getCustomerId())) {
                reportsForCustomer.add(createReportString(report));
            }
        }
        return reportsForCustomer;
    }

    @Override
    public List<String> getLatestReportsForCustomer(UUID customerId) {
        return reports.values().stream()
                .filter(report -> customerId.equals(report.getCustomerId()))
                .sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .map(this::createReportString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReportsForMerchant(UUID merchantId) {
        List<String> reportsForMerchant = new ArrayList<>();
        for (Report report : reports.values()) {
            if (merchantId.equals(report.getMerchantId())) {
                reportsForMerchant.add(createReportString(report));
            }
        }
        return reportsForMerchant;
    }

    @Override
    public List<String> getLatestReportsForMerchant(UUID merchantId) {
        return reports.values().stream()
                .filter(report -> merchantId.equals(report.getMerchantId()))
                .sorted(Comparator.comparing(Report::getDateTime).reversed())
                .limit(5)
                .map(this::createReportString)
                .collect(Collectors.toList());
    }

    private String createReportString(Report report) {
        return "Report ID: " + report.getReportId() +
                ", Amount: " + report.getAmount() +
                ", CustomerID: " + report.getCustomerId() +
                ", MerchantId: " + report.getMerchantId() +
                ", Token: " + report.getToken();
    }
}
