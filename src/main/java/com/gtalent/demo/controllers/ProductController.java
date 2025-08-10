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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;


    @Autowired
    public ProductController(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
// 1. 使用 stream 將 List<Product> 轉換成 List<ProductResponse>
        List<ProductResponse> productResponses = productRepository.findAll()
                .stream().map(product -> {
            // 在 map 中，目標是建立並回傳一個設定好的 ProductResponse 物件
            ProductResponse response = new ProductResponse(product); // 假設建構子只複製基本欄位

            // 在 Controller 中進行 null 檢查
            if (product.getSupplier() != null) {
                response.setSupplier(new SupplierResponse(product.getSupplier()));
            }
            // map 的結尾是回傳轉換好的 response 物件，而不是 ResponseEntity
            return response;
        }).collect(Collectors.toList());

        // 2. 將整個轉換好的列表放進一個 ResponseEntity.ok() 中回傳
        return ResponseEntity.ok(productResponses);
    }

    /**
     * 根據 ID 獲取單一產品
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductsById(@PathVariable int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductResponse response = new ProductResponse(product.get());
            response.setSupplier(new SupplierResponse(product.get().getSupplier()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 創建新產品
     * POST請求成功後，標準的 RESTful 回應是 201 Created
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        Optional<Supplier>supplier = supplierRepository.findById(request.getSupplierId());
        if(supplier.isPresent()){
            Product newProduct = new Product();
            newProduct.setName(request.getName());
            newProduct.setPrice(request.getPrice());
            newProduct.setQuantity(request.getQuantity());
            newProduct.setStatus(request.getStatus());
            newProduct.setSupplier(supplier.get());
            Product savedProduct = productRepository.save(newProduct);
            ProductResponse response = new ProductResponse(savedProduct);
            response.setSupplier(supplier.map(SupplierResponse::new).get());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * 根據 ID 更新現有產品
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable Integer id, @RequestBody ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(request.getName());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setQuantity(request.getQuantity());
            Product updatedProduct = productRepository.save(existingProduct);
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
