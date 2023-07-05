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
@Path("/GIOTI/companies/{company_name}/products")
public class ProductResource {
    private final ProductDAO dao;

    public ProductResource(DBI jdbi) {
        dao = new ProductDAO(jdbi.open());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerProduct(@PathParam("company_name") String companyName, Product product) {
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
    @Path("/{product_name}")
    public Response getProduct(@PathParam("company_name") String companyName,
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
        Product product = getProductObject(companyName, productID);
        return Response.ok(product).build();
    }

    @GET
    public Response getProducts(@PathParam("company_name") String companyName) {
        if (!dao.isCompanyExist(companyName)) {
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
