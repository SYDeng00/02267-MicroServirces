package org.acme.Interfaces;


import java.util.List;
import java.util.UUID;
import org.acme.Domains.Report;
/**
 * Configuration class for report Repository Interface.
 * @author Tama Sarker
 * @author Divya
 */

public interface IReportRepository {
    void addReport(Report report);
    Report getReport(UUID reportId);
    List<String> getAllReports();
    List<String> getLatestReports();
    //List<Report> getReportsByStatus(String status);
    List<String> getReportsForCustomer(UUID customerId);
    List<String> getLatestReportsForCustomer(UUID customerId);
    List<String> getReportsForMerchant(UUID merchantId);
    List<String> getLatestReportsForMerchant(UUID merchantId);
}
