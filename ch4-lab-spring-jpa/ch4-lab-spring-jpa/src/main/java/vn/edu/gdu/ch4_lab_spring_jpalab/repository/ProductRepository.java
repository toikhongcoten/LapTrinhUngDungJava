package vn.edu.gdu.ch4_lab_spring_jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    List<Product> findExpensiveProducts(@Param("minPrice") BigDecimal minPrice);

    @Query(value = "SELECT * FROM products ORDER BY price DESC LIMIT 3", nativeQuery = true)
    List<Product> findTop3ByOrderByPriceDescNative();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.price = p.price * :rate WHERE p.id = :id")
    int updateProductPrice(@Param("id") Long id, @Param("rate") BigDecimal rate);
}