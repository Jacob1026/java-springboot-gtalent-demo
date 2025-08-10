package com.gtalent.demo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;
    @Column(name="email")
    private String email;
    @OneToMany
    @JoinColumn(name ="id")
    private List<Product> products;

    public Supplier(int id, String name, String address, String phone, String email, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Supplier() {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
