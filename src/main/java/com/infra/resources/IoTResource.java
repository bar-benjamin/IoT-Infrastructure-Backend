package com.infra.resources;

import com.infra.dao.IoTDAO;
import com.infra.representations.Address;
import com.infra.representations.IoT;
import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/GIOTI/companies/{company_name}/products/{product_name}/iot_devices")
public class IoTResource {
    private final IoTDAO dao;

    public IoTResource(DBI jdbi) {
        dao = new IoTDAO(jdbi.open());
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerIoT(@PathParam("company_name") String companyName,
                                @PathParam("product_name") String productName,
                                IoT iot) {
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(productName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such product in database")
                    .build();
        }

        int productID = dao.getProductIDByName(productName);
        dao.registerIOT(iot, productID);
        return Response.status(Response.Status.CREATED).entity("Success: IoT was registered in database").build();
    }

    @GET
    @Path("/{iot_device_id}")
    public Response getIoT(@PathParam("company_name") String companyName,
                           @PathParam("product_name") String productName,
                           @PathParam("iot_device_id") int deviceSerialNumber) {
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(productName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such product in database")
                    .build();
        }

        if (!dao.isIoTExist(deviceSerialNumber)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such IoT device in database")
                    .build();
        }

        IoT iot = getIoTObject(deviceSerialNumber);
        return Response.ok(iot).build();
    }

    @GET
    public Response getIoTs(@PathParam("company_name") String companyName,
                            @PathParam("product_name") String productName) {
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(productName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such product in database")
                    .build();
        }

        int productID = dao.getProductIDByName(productName);
        List<String> iots = dao.getIoTs(productID);
        return Response.ok(iots).build();
    }

    // helper methods
    private IoT getIoTObject(int deviceSerialNumber) {
        String ownerName = dao.getOwnerNameByID(deviceSerialNumber);
        String ownerEmail = dao.getOwnerEmailByID(deviceSerialNumber);
        String ownerPhone = dao.getOwnerPhoneByID(deviceSerialNumber);
        Address ownerAddress = dao.getOwnerAddressByID(deviceSerialNumber);
        List<PaymentInfo> payments = dao.getPaymentsByID(deviceSerialNumber);

        return new IoT(deviceSerialNumber, ownerName, ownerEmail, ownerPhone, ownerAddress, payments);
    }
}
