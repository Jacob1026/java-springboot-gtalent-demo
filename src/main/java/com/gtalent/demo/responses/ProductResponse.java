package com.gtalent.demo.responses;

import com.gtalent.demo.models.Product;

import java.math.BigDecimal;

public class ProductResponse {
    private String name;
    private BigDecimal price;
    private double quantity;
    private int status;
    private SupplierResponse supplier;


    public ProductResponse(String name, BigDecimal price, double quantity, int status) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
    public ProductResponse(Product product){
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity =product.getQuantity();
        this.status = product.getStatus();
    }
    public SupplierResponse getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierResponse supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
