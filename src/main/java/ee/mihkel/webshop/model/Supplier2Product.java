package ee.mihkel.webshop.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Supplier2Product extends SupplierProduct {
    private int id;
    private String title;
    private String slug;
    private int price;
    private String description;
    private Category category;
    private ArrayList<String> images;
    private Date creationAt;
    private Date updatedAt;
}

@Data
class Category{
    private int id;
    private String name;
    private String slug;
    private String image;
    private Date creationAt;
    private Date updatedAt;
}
