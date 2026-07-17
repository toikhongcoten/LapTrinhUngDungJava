package vn.edu.gdu.ch4_lab_spring_jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}