package com.enigma.enigmaboot.repository;

import com.enigma.enigmaboot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // menandakan bahwa ini dalaha Bean Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}