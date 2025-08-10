package com.gtalent.demo.repositories;

import com.gtalent.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product,Integer>{
}


