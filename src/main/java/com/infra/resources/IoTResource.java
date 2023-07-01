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
@Path("/GIOTI/companies/{company_id}/products/{product_id}/iot_devices")
public class IoTResource {
    private final IoTDAO dao;

    public IoTResource(DBI jdbi) {
        dao = new IoTDAO(jdbi.open());
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerIoT(@PathParam("company_id") int companyID,
                                @PathParam("product_id") int productID,
                                IoT iot) {
        String companyName = dao.getCompanyNameByID(companyID);

        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(dao.getProductNameByID(productID))) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such product in database")
                    .build();
        }

        dao.registerIOT(iot, productID);
        return Response.status(Response.Status.CREATED).entity("Success: IoT was registered in database").build();
    }

    @GET
    @Path("/{iot_device_id}")
    public Response getIoT(@PathParam("company_id") int companyID,
                           @PathParam("product_id") int productID,
                           @PathParam("iot_device_id") int deviceSerialNumber) {
        String companyName = dao.getCompanyNameByID(companyID);

        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(dao.getProductNameByID(productID))) {
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
    public Response getIoTs(@PathParam("company_id") int companyID,
                            @PathParam("product_id") int productID) {
        String companyName = dao.getCompanyNameByID(companyID);
        if (companyName == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (!dao.isProductExist(dao.getProductNameByID(productID))) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such product in database")
                    .build();
        }

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
