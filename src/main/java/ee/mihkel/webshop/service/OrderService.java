package ee.mihkel.webshop.service;

import ee.mihkel.webshop.entity.*;
import ee.mihkel.webshop.model.*;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CacheService cacheService;

    @Value("${everypay.base-url}")
    private String everyPayBaseUrl;

    @Value("${everypay.username}")
    private String everyPayUsername; // 4x

    @Value("${everypay.password}")
    private String everyPayPassword;

    @Value("${everypay.customer-url}")
    private String everyPayCustomerUrl;

    @Value("${everypay.order-prefix}")
    private String everyPayOrderPrefix;


    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        String url = "https://www.omniva.ee/locations.json";
        ParcelMachine[] response = restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class).getBody();
        return Arrays.stream(response).filter(e -> e.a0_NAME.equals(country.toUpperCase())).toList();
    }

    public PaymentLink pay(Long personId, List<OrderRow> orderRows) throws ExecutionException {
        Order order = saveOrder(personId, orderRows);
        // 1. ID saadame maksesse
        // 2. Enne makset võiks salvestada andmebaasi maksmata kujul terve tellimuse
        return getPaymentLink(order.getId(), order.getTotal());
    }

    // ?order_reference=tyuijfgh7&payment_reference=5a7aae9fc11421edc6d9ba285a81fbe924951b83becbb3cb32aaad6c713e79b4
    // ?order_reference=tyuijfgh8&payment_reference=5742055c8441f2c8831ca28d35d8dbcfdf0ce78737a72b4b283b4d4745b1ac94
    private PaymentLink getPaymentLink(Long id, double total) {
        String url = everyPayBaseUrl + "/payments/oneoff";

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name("EUR3D1"); // Apollo Group. Apollo kino. Apollo raamatupood. Blender. Postimees. Duo.
        body.setNonce("dasdsa" + Math.random() + System.currentTimeMillis()); // turvaelement. ei läheks 2x identne päring
        body.setTimestamp(ZonedDateTime.now().toString()); // turvaelement. praegune kellaega +/- 5 minutit
        body.setAmount(total); // summa
        body.setOrder_reference(everyPayOrderPrefix + id); // tellimuse ID
        body.setCustomer_url(everyPayCustomerUrl); // kuhu meid tagasi suunatakse
        body.setApi_username(everyPayUsername); // kasutajanimi, mis peab ühtima headeris oleva kasutajanimega

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everyPayUsername,everyPayPassword);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EveryPayBody> entity = new HttpEntity<>(body, headers);

        EveryPayResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class).getBody();
        if (response == null) {
            throw new RuntimeException("Couldnt get everypay response");
        }
        PaymentLink paymentLink = new PaymentLink();
        paymentLink.setLink(response.getPayment_link());
        return paymentLink;
    }

    private Order saveOrder(Long personId, List<OrderRow> orderRows) throws ExecutionException {
        Order order = new Order();
        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setOrderRows(orderRows); // ID-dega mapib külge
//        order.setTotal(products.stream().mapToDouble(Product::getPrice).sum());
        double sum = 0;
        for (OrderRow orderRow : orderRows){
            Product dbProduct = cacheService.getProduct(orderRow.getProduct().getId());
            sum += dbProduct.getPrice() * orderRow.getQuantity();
        }
        order.setTotal(sum);
        order.setPaymentStatus(PaymentStatus.initial);
        return orderRepository.save(order);
    }

    // "https://igw-demo.every-pay.com/api/v4/payments/5a7aae9fc11421edc6d9ba285a81fbe924951b83becbb3cb32aaad6c713e79b4?api_username=e36eb40f5ec87fa2&detailed=false"
    // "https://igw-demo.every-pay.com/api/v4/payments/5742055c8441f2c8831ca28d35d8dbcfdf0ce78737a72b4b283b4d4745b1ac94?api_username=e36eb40f5ec87fa2&detailed=false"
    public OrderPaid checkPayment(String orderReference, String paymentReference) {
        String url = everyPayBaseUrl + "/payments/" + paymentReference
                + "?api_username="+everyPayUsername+"&detailed=false";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everyPayUsername,everyPayPassword);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EveryPayBody> entity = new HttpEntity<>(null, headers);

        EveryPayCheck response = restTemplate.exchange(url, HttpMethod.GET, entity, EveryPayCheck.class).getBody();
        if (response == null) {
            throw new RuntimeException("Couldnt get everypay response");
        }
        if (!response.getOrder_reference().equals(orderReference)) {
            throw new RuntimeException("Order reference does not match");
        }
        Order order = orderRepository.findById(Long.valueOf(orderReference.replace("tyuijfgh", ""))).orElseThrow();
        order.setPaymentStatus(PaymentStatus.valueOf(response.getPayment_state()));
        orderRepository.save(order);
        OrderPaid orderPaid = new OrderPaid();
        orderPaid.setPaid(response.getPayment_state().equals("settled"));
        return orderPaid;
    }
}
