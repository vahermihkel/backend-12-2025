package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("orders")
    public Order addOrder(@RequestParam Long personId, @RequestBody List<OrderRow> orderRows){ // TODO: personId authist
        Order order = new Order();
        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setOrderRows(orderRows); // ID-dega mapib külge
//        order.setTotal(products.stream().mapToDouble(Product::getPrice).sum());
        double sum = 0;
        for (OrderRow orderRow : orderRows){
            Product dbProduct = productRepository.findById(orderRow.getProduct().getId()).orElseThrow();
            sum += dbProduct.getPrice() * orderRow.getQuantity();
        }
        order.setTotal(sum);
        return orderRepository.save(order);
    }
}
