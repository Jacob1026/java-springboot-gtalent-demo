package com.gtalent.demo.controllers;

import com.gtalent.demo.models.Product;
import com.gtalent.demo.models.Supplier;
import com.gtalent.demo.models.User;
import com.gtalent.demo.repositories.ProductRepository;
import com.gtalent.demo.repositories.SupplierRepository;
import com.gtalent.demo.repositories.UserRepository;
import com.gtalent.demo.requests.CreateUserRequest;
import com.gtalent.demo.requests.ProductRequest;
import com.gtalent.demo.requests.UpdateUserRequest;
import com.gtalent.demo.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    private final ProductRepository productRepository;


    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return  ResponseEntity.ok(products.stream().map(ProductResponse::new ).toList());
    }

    /**
     * 根據 ID 獲取單一產品
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        // 使用 Optional 的 map 和 orElseGet 讓程式碼更優雅
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(new ProductResponse(product)))
                .orElseGet(() -> ResponseEntity.notFound().build()); // 若找不到產品，回傳 404 Not Found
    }

    /**
     * 創建新產品
     * POST請求成功後，標準的 RESTful 回應是 201 Created
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        // 1. 根據請求(request)建立一個新的 Product 實體物件
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setPrice(request.getPrice());
        newProduct.setQuantity(request.getQuantity());
        // 假設 Product 實體有 status 欄位
        // newProduct.setStatus(1); // 可在此設定預設狀態

        // 2. 將新產品存入資料庫
        Product savedProduct = productRepository.save(newProduct);

        // 3. 將儲存後的產品轉換為回應(response)格式
        ProductResponse response = new ProductResponse(savedProduct);

        // 4. 回傳 201 Created 狀態碼，並在 body 中附上新建立的資源
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 根據 ID 更新現有產品
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable Integer id, @RequestBody ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            // 1. 從資料庫中獲取該產品
            Product existingProduct = optionalProduct.get();
            // 2. 更新產品屬性
            existingProduct.setName(request.getName());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setQuantity(request.getQuantity());

            // 3. 儲存更新後的產品
            Product updatedProduct = productRepository.save(existingProduct);

            // 4. 建立回應並回傳 200 OK
            ProductResponse response = new ProductResponse(updatedProduct);
            return ResponseEntity.ok(response);
        } else {
            // 如果產品不存在，回傳 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根據 ID 刪除產品
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer id) {
        // 先檢查產品是否存在，可以提供更精確的回應 (404 Not Found)
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            // 成功刪除，回傳 204 No Content
            return ResponseEntity.noContent().build();
        } else {
            // 如果產品不存在，回傳 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
