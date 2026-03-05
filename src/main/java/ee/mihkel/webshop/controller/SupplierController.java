package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.Supplier1Product;
import ee.mihkel.webshop.model.Supplier2Product;
import ee.mihkel.webshop.model.Supplier3Product;
import ee.mihkel.webshop.model.SupplierProduct;
import ee.mihkel.webshop.service.MailService;
import ee.mihkel.webshop.service.SupplierService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// supplier -> tarnija.
// võtame hankija API endpoindilt enda rakendusse kõik tooted.

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MailService mailService; // TODO: Eraldi controller

    @GetMapping("supplier1")
    public List<Supplier1Product> getProductsFromSupplier1() {
        mailService.sendPlainText("vahermihkel@gmail.com", "Tere", "Sisu");
        return supplierService.getProductsFromSupplier1();
    }

    @GetMapping("supplier2")
    public List<Supplier2Product> getProductsFromSupplier2() throws MessagingException {
        mailService.sendHtml("vahermihkel@gmail.com", "Tere", "<h1>Tere</h1><button>Nupp</button>");
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
