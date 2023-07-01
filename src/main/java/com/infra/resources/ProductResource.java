package com.infra.resources;

import com.infra.dao.ProductDAO;
import com.infra.representations.Product;
import com.infra.representations.Specification;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/GIOTI/companies/{company_id}/products")
public class ProductResource {
    private final ProductDAO dao;

    public ProductResource(DBI jdbi) {
        dao = new ProductDAO(jdbi.open());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerProduct(@PathParam("company_id") int companyID, Product product) {
        String companyName = dao.getCompanyNameByID(companyID);
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        if (dao.isProductExist(product.getProductName())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error: product already exists in database")
                    .build();
        }

        dao.registerProduct(product);
        return Response.status(Response.Status.CREATED).entity("Success: product was registered in database").build();
    }

    @GET
    @Path("/{product_id}")
    public Response getProduct(@PathParam("company_id") int companyID, @PathParam("product_id") int productID) {
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

        Product product = getProductObject(companyName, productID);
        return Response.ok(product).build();
    }

    @GET
    public Response getProducts(@PathParam("company_id") int companyID) {
        String companyName = dao.getCompanyNameByID(companyID);
        if (companyName == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.useDatabase(companyName);
        List<Integer> productIDs = dao.getProductsIDs();
        if (productIDs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no products in database")
                    .build();
        }

        List<Product> products = new ArrayList<>();
        for (Integer productID: productIDs) {
            Product product = getProductObject(companyName, productID);
            products.add(product);
        }

        return Response.ok(products).build();
    }


    // helper methods
    private Product getProductObject(String companyName, int productID) {
        dao.useDatabase(companyName);
        String productName = dao.getProductNameByID(productID);
        Specification specs = dao.getProductSpecification(productID);
        return new Product(productID, productName, specs);
    }
}
