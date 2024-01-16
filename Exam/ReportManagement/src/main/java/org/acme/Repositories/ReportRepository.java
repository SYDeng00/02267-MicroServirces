package org.acme.Repositories;


import org.acme.Domains.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReportRepository implements IReportRepository {
    private final HashMap<UUID, Report> reports = new HashMap<>();

    @Override
    public void addReport(Report report) {
        reports.put(report.getReportId(), report);
    }

    @Override
    public Report getReport(UUID reportId) {
        return reports.get(reportId);
    }

    @Override
    public List<Report> getAllReports() {
        return new ArrayList<>(reports.values());
    }

    @Override
    public List<Report> getReportsByStatus(String status) {
        return reports.values().stream()
                .filter(report -> report.getStatus().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> getReportsForCustomer(UUID customerId) {
        return reports.values().stream()
                .filter(report -> report.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> getReportsForMerchant(UUID merchantId) {
        return reports.values().stream()
                .filter(report -> report.getMerchantId().equals(merchantId))
                .collect(Collectors.toList());
    }
}

