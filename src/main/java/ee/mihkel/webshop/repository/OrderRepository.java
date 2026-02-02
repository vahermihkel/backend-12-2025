package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByPerson_Id(Long id);
}
