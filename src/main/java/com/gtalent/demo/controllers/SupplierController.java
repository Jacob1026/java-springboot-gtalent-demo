package com.gtalent.demo.controllers;

import com.gtalent.demo.models.Supplier;
import com.gtalent.demo.models.Supplier;
import com.gtalent.demo.repositories.SupplierRepository;
import com.gtalent.demo.repositories.SupplierRepository;
import com.gtalent.demo.requests.SupplierRequest;
import com.gtalent.demo.responses.SupplierResponse;
import com.gtalent.demo.responses.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
@CrossOrigin("*")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return  ResponseEntity.ok(suppliers.stream().map(SupplierResponse::new ).toList());
    }

    /**
     * 根據 ID 獲取單一產品
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Integer id) {
        // 使用 Optional 的 map 和 orElseGet 讓程式碼更優雅
        return supplierRepository.findById(id)
                .map(supplier  -> ResponseEntity.ok(new SupplierResponse(supplier)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 創建新產品
     * POST請求成功後，標準的 RESTful 回應是 201 Created
     */
    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest request) {
        // 1. 根據請求(request)建立一個新的 Supplier 實體物件
        Supplier newSupplier = new Supplier();
        newSupplier.setId(request.getId());
        newSupplier.setName(request.getName());
        newSupplier.setAddress(request.getAddress());
        newSupplier.setPhone(request.getPhone());
        newSupplier.setEmail(request.getEmail());
        Supplier savedSupplier = supplierRepository.save(newSupplier);

        SupplierResponse response = new SupplierResponse(savedSupplier);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 根據 ID 更新現有產品
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> updateSupplierById(@PathVariable Integer id, @RequestBody SupplierRequest request) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);

        if (optionalSupplier.isPresent()) {
            // 1. 從資料庫中獲取該產品
            Supplier existingSupplier = optionalSupplier.get();
            // 2. 更新產品屬性
            existingSupplier.setId(request.getId());
            existingSupplier.setName(request.getName());
            existingSupplier.setAddress(request.getAddress());
            existingSupplier.setPhone(request.getPhone());
            existingSupplier.setEmail(request.getEmail());

            // 3. 儲存更新後的產品
            Supplier updatedSupplier = supplierRepository.save(existingSupplier);

            // 4. 建立回應並回傳 200 OK
            SupplierResponse response = new SupplierResponse(updatedSupplier);
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
    public ResponseEntity<Void> deleteSupplierById(@PathVariable Integer id) {
        // 先檢查產品是否存在，可以提供更精確的回應 (404 Not Found)
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            // 成功刪除，回傳 204 No Content
            return ResponseEntity.noContent().build();
        } else {
            // 如果產品不存在，回傳 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
