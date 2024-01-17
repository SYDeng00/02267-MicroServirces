package main.java.org.acme.report_facade.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.payment_facade.Domains.Payment;
import main.java.org.acme.payment_facade.Resources.PaymentFacadeBroker;

/**
 * Configuration class for report-facade Resource.
 * @author Tama Sarker
 */
@Path("/")
public class ReportFacadeResources {
    ReportFacadBroker reportFacadBroker;
    @GET
    @Path("/report/customer/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForCustomer(@PathParam("id") String id) {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(id,ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Generated for Customer successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/customer/latest/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForCustomer(@PathParam("id") String id) {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(id,ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Latest Generated for Customer successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/merchant/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForMerchant(@PathParam("id") String id) {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(id,ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Generated for Merchant successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/merchant/latest/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForMerchant(@PathParam("id") String id) {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(id,ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Latest Generated for Merchant successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/dtupay/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForDTUPay() {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(null,ReportFacadeConfig.GENERATE_REPORT_DTU);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Generated for DTUPay successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/latest/dtupay/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForDTUPay() {
        try {
            reportFacadBroker = new ReportFacadBroker();
            reportFacadBroker.sendReportRequestToReportService(null,ReportFacadeConfig.GENERATE_LATEST_REPORT_DTU);
            reportFacadBroker.received();
            return Response.status(201).entity("Report Latest Generated for DTUPay successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
