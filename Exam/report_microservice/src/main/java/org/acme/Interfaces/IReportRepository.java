package org.acme.Interfaces;


import java.util.List;
import java.util.UUID;
import org.acme.Domains.Report;


public interface IReportRepository {
    void addReport(Report report);
    Report getReport(UUID reportId);
    List<String> getAllReports();
    //List<Report> getReportsByStatus(String status);
    List<String> getReportsForCustomer(UUID customerId);
    List<String> getReportsForMerchant(UUID merchantId);
}
