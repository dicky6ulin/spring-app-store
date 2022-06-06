package com.enigma.enigmaboot.service;

import com.enigma.enigmaboot.entity.Customer;
import com.enigma.enigmaboot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProduct();

    public Product getProductById(String productId);

    public Product saveProduct(Product product);

    public void deleteProduct(String productId);

    Page<Product> getProductPerPage(Pageable pageable);

    public Product saveProductPictures(Product product, MultipartFile file);
}