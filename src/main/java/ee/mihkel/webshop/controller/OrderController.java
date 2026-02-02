package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.OrderPaid;
import ee.mihkel.webshop.model.ParcelMachine;
import ee.mihkel.webshop.model.PaymentLink;
import ee.mihkel.webshop.model.Supplier3Response;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import ee.mihkel.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("person-orders")
    public List<Order> getPersonOrders(){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return orderRepository.findByPerson_Id(id);
    }

    @PostMapping("orders")
    public PaymentLink addOrder(@RequestBody List<OrderRow> orderRows) throws ExecutionException {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return orderService.pay(personId, orderRows);
    }

    // localhost:8080/parcelmachines?country=ee
    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        return orderService.getParcelMachines(country);
    }

    @GetMapping("check-payment")
    public OrderPaid checkPayment(@RequestParam String orderReference, String paymentReference) {
        return orderService.checkPayment(orderReference, paymentReference);
    }
}
