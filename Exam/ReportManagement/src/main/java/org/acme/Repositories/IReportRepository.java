package org.acme.Repositories;


import java.util.List;
import java.util.UUID;
import org.acme.Domains.Report;


public interface IReportRepository {
    void addReport(Report report);
    Report getReport(UUID reportId);
    List<Report> getAllReports();
    List<Report> getReportsByStatus(String status);
    List<Report> getReportsForCustomer(UUID customerId);
    List<Report> getReportsForMerchant(UUID merchantId);
}
