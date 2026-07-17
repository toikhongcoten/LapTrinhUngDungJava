package vn.edu.gdu.ch4_lab_spring_jpalab.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private BigDecimal price;

    @Column(unique = true)
    private String sku;

    // Cấu hình Quan hệ Nhiều - Một (Nhiều Sản phẩm thuộc về Một Danh mục)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id") // Tự động tạo cột khóa ngoại tên 'category_id' dưới DB
    private Category category;

    // --- Constructors ---
    public Product() {}

    public Product(String name, BigDecimal price, String sku, Category category) {
        this.name = name;
        this.price = price;
        this.sku = sku;
        this.category = category;
    }

    // --- Getter và Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}