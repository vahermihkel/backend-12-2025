package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // BASE URL         endpoint
    // localhost:8080/products?page=0&size=20&sort=id,asc
    @GetMapping("products")
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    @GetMapping("admin-products")
    public List<Product> getAdminProducts(){
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if(product.getId() != null) {
            throw new RuntimeException("Cannot add product with id");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

//    @DeleteMapping("products")
//    public List<Product> deleteProduct(@RequestParam Long id){
//        productRepository.deleteById(id);
//        return productRepository.findAll();
//    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    // GET localhost:8080/1
    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    // @RequestParam --> kui on 2 või rohkem parameetrit
    //               --> kui mõni on nullable ehk pole vajalik saata

    // @PathVariable on selgem --> täpselt 1 muutuja mis on alati vajalik

    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        if(product.getId() == null) {
            throw new RuntimeException("Cannot edit product without id");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
}

// 2xx -> edukas
// 4xx -> front-end viga
// 5xx -> back-end viga
