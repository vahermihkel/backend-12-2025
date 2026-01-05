package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Category;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.CategoryRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
//@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping( "categories")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.status(200).body(categoryRepository.findAll());
    }

    @PostMapping("categories")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category){
        System.out.println("1. Adding Category...");
        log.info("2. Adding Category...");
        if(category.getId() != null) {
            throw new RuntimeException("Cannot add category with id");
        }
        categoryRepository.save(category);
        return ResponseEntity.status(201).body(categoryRepository.findAll());
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }
}
