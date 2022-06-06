package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.exception.DataNotFoundException;
import com.enigma.enigmaboot.repository.ProductRepository;
import com.enigma.enigmaboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        if (!productRepository.findById(productId).isPresent()) {
            String message = String.format(ResponseMessage.DATA_NOT_FOUND, "product", productId);
            throw new DataNotFoundException(message);
        }
        return productRepository.findById(productId).get();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> getProductPerPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product saveProductPictures(Product product, MultipartFile file) {
        String pathFolderString = "C:\\Dev\\Java\\EnigmaCamp\\day25-NewProject-SpringBoot\\enigma-boot\\src\\main\\java\\com\\enigma\\enigmaboot\\assets\\";
        Path pathFolder = Paths.get(pathFolderString);
        Path pathFile = Paths.get(pathFolder.toString() + "/" + product.getProductName() + " - " + file.getOriginalFilename() + ".png");
        try {
            file.transferTo(pathFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setProductImages(file.getOriginalFilename());
        return productRepository.save(product);
    }
}