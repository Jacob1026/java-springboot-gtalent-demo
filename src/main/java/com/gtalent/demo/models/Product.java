package com.gtalent.demo.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="products")

public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id ;

   @Column(name="name")
   private String name;

   @Column(name="price")
   private BigDecimal price;

   @Column(name="quantity")
   private int quantity;

    @Column(name="status")
   private int status;

    public Product(int id, String name, BigDecimal price, int quantity, int status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
