package ee.mihkel.webshop.model;

import ee.mihkel.webshop.entity.Product;
import lombok.Data;

@Data
public class Supplier1Product extends SupplierProduct {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Supplier1Rating rating;
}

