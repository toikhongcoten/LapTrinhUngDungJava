package vn.edu.gdu.ch4_lab_spring_jpalab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Category;
import vn.edu.gdu.ch4_lab_spring_jpalab.entity.Product;
import vn.edu.gdu.ch4_lab_spring_jpalab.repository.CategoryRepository;
import vn.edu.gdu.ch4_lab_spring_jpalab.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // --- API BÀI 3: TẠO DANH MỤC MỚI ---
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    // --- API BÀI 3: THÊM SẢN PHẨM VÀO DANH MỤC THEO ID ---
    @PostMapping("/with-category/{categoryId}")
    public ResponseEntity<?> createProductWithCategory(
            @PathVariable Long categoryId,
            @RequestBody Product product) {

        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy Danh mục với ID: " + categoryId);
        }

        product.setCategory(categoryOpt.get());
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // --- CÁC API TRUY VẤN NÂNG CAO ---
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getProductsPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productRepository.findByNameContainingIgnoreCase(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(productRepository.findByPriceBetween(min, max));
    }

    @GetMapping("/expensive")
    public ResponseEntity<List<Product>> getExpensiveProducts(@RequestParam BigDecimal minPrice) {
        return ResponseEntity.ok(productRepository.findExpensiveProducts(minPrice));
    }

    @GetMapping("/top3")
    public ResponseEntity<List<Product>> getTop3ExpensiveProducts() {
        return ResponseEntity.ok(productRepository.findTop3ByOrderByPriceDescNative());
    }

    @PutMapping("/{id}/increase-price")
    public ResponseEntity<String> increaseProductPrice(@PathVariable Long id, @RequestParam BigDecimal rate) {
        int rowsAffected = productRepository.updateProductPrice(id, rate);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Cập nhật giá thành công cho sản phẩm ID: " + id);
        }
        return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm hoặc cập nhật thất bại.");
    }
}