package main.java.org.acme.report_facade.Resources;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.account_facade.Resources.AccountConfig;
import main.java.org.acme.payment_facade.Domains.Payment;
import main.java.org.acme.payment_facade.Resources.PaymentFacadeBroker;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Configuration class for report-facade Resource.
 * @author Tama Sarker
 */
@Path("/")
public class ReportFacadeResources implements IEventSubscriber {
    EventPublisher publisher = new EventPublisher();
    EventSubscriber subscriber = new EventSubscriber(this);
    List<String> receivedId;

    private CompletableFuture<String> idFuture ;

    public ReportFacadeResources() {
        try {
            subscriber.subscribeEvent("ReportFacadeResources");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GET
    @Path("/report/customer/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForCustomer(@PathParam("id") String id) {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_CUSTOMER, "ReportBroker", new Object[] { id }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/customer/latest/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForCustomer(@PathParam("id") String id) {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_CUSTOMER, "ReportBroker", new Object[] { id }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/merchant/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForMerchant(@PathParam("id") String id) {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_REPORT_FOR_MERCHANT, "ReportBroker", new Object[] { id }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/merchant/latest/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForMerchant(@PathParam("id") String id) {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_FOR_MERCHANT, "ReportBroker", new Object[] { id }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/dtupay/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response ReportForDTUPay() {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_REPORT_DTU, "ReportBroker", new Object[] { }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @GET
    @Path("/report/latest/dtupay/")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response LatestReportForDTUPay() {
        try {
            idFuture = new CompletableFuture<>();
            publisher.publishEvent(new Message(ReportFacadeConfig.GENERATE_LATEST_REPORT_DTU, "ReportBroker", new Object[] { }));
            idFuture.join();
            List<String> reportList = receivedId;

            return Response.status(201).entity(reportList).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    @Override
    public void subscribeEvent(Message message) {
        String status = message.getStatus();
        if (message.getEventType().equals(ReportFacadeConfig.RETRIEVE_REPORT_EVENT) && message.getService().equals("ReportFacadeResources")) {
            Gson gson = new Gson();
            receivedId = (List<String>)(message.getPayload()[0]);
            idFuture.complete(status);
        }
    }
}
