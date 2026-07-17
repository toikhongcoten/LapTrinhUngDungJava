package vn.edu.gdu.ch4_lab_spring_jpalab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- Đã thêm dòng import này
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Cấu hình Quan hệ Một - Nhiều (Một Danh mục có Nhiều Sản phẩm)
    // Thêm @JsonIgnore để tránh lỗi tuần hoàn vô hạn (JSON Infinite Loop) dẫn đến lỗi 500
    @JsonIgnore // <-- Đã thêm annotation này ở đây
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    // --- Constructors ---
    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    // --- Getter và Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}