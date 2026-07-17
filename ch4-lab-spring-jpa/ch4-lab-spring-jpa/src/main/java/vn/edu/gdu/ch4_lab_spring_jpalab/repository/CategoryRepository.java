package vn.edu.gdu.ch4_lab_spring_jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tự động có sẵn các hàm CRUD cơ bản cho Category
}