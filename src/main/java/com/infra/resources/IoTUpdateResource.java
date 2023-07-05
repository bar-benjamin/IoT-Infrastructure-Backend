package com.infra.resources;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.infra.config.MongoDBConfiguration;
import com.infra.representations.IoTData;
import com.infra.representations.IoTUpdate;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/GIOTI/companies/{company_name}/products/{product_name}/iot_devices/{iot_device_id}/iot_updates")
public class IoTUpdateResource {
    private final MongoCollection<Document> collection;

    public IoTUpdateResource(MongoDBConfiguration mongoDB) {
        MongoClient mongoClient = MongoClients.create(mongoDB.getMongoURI());
        MongoDatabase database = mongoClient.getDatabase(mongoDB.getDatabaseName());
        collection = database.getCollection(mongoDB.getCollectionName());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIoT(@PathParam("company_name") String companyName,
                              @PathParam("product_name") String productName,
                              @PathParam("iot_device_id") int deviceSerialNumber,
                              IoTData iotData) {
        Document document = new Document();
        document.append("iot_data", iotData.getIoTData())
                .append("timestamp", Instant.now())
                .append("company_name", companyName)
                .append("product_name", productName)
                .append("deviceSerialNumber", deviceSerialNumber);

        collection.insertOne(document);

        return Response.status(Response.Status.CREATED).entity("Success: IoT was updated in database").build();
    }

    @GET
    @Path("/{num_of_updates}")
    public Response getIoTUpdates(@PathParam("company_name") String companyName,
                                  @PathParam("product_name") String productName,
                                  @PathParam("iot_device_id") int deviceSerialNumber,
                                  @PathParam("num_of_updates") int numOfUpdates) {
        List<Document> updates = collection.find()
                .sort(Sorts.descending("timestamp"))
                .limit(numOfUpdates)
                .into(new ArrayList<>());

        List<IoTUpdate<Object>> iotUpdates = new ArrayList<>();

        for (Document update : updates) {
            Object iotData = update.get("iot_data");
            Instant timestamp = update.getDate("timestamp").toInstant();

            IoTUpdate<Object> iotUpdate = new IoTUpdate<>(iotData, timestamp);
            iotUpdates.add(iotUpdate);
        }

        return Response.ok(iotUpdates).build();
    }

}
