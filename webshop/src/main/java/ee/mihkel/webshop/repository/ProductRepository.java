package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CrudRepository ---> minimaalne kogus funktsioone (.findAll()   .save()    .deleteById())
// PagingAndSortingRepository ---> lk kaupa minemine + sorteerimine
// JpaRepository ---> maksimaalne kogus funktsioone .flush()

public interface ProductRepository extends JpaRepository<Product,Long> {

//    List<Product> findByActiveTrueOrderByIdAsc();

    Page<Product> findByActiveTrue(Pageable pageable);
}
