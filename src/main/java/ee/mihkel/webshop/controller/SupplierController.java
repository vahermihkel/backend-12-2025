package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.Supplier1Product;
import ee.mihkel.webshop.model.Supplier2Product;
import ee.mihkel.webshop.model.Supplier3Product;
import ee.mihkel.webshop.model.SupplierProduct;
import ee.mihkel.webshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// supplier -> tarnija.
// võtame hankija API endpoindilt enda rakendusse kõik tooted.

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("supplier1")
    public List<Supplier1Product> getProductsFromSupplier1() {
        return supplierService.getProductsFromSupplier1();
    }

    @GetMapping("supplier2")
    public List<Supplier2Product> getProductsFromSupplier2() {
        return supplierService.getProductsFromSupplier2();
    }

    @GetMapping("supplier3")
    public List<Supplier3Product> getProductsFromSupplier3() {
        return supplierService.getProductsFromSupplier3();
    }

    @GetMapping("supplier-products")
    public List<SupplierProduct> getProductsFromSupplier() {
        return supplierService.getProductsFromSupplier();
    }
}
