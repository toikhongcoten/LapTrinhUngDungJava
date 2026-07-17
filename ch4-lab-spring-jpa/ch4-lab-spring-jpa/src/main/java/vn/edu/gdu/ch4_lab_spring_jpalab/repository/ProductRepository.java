package vn.edu.gdu.ch4_lab_spring_jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}