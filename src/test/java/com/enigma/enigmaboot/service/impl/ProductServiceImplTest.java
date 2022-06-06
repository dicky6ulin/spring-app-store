package com.enigma.enigmaboot.service.impl;

import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.exception.DataNotFoundException;
import com.enigma.enigmaboot.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    private Product product;

    @BeforeEach
    /*
    Setiap ada yang buat test case befpre Each di panggil
     */
    void setup() {
        product = new Product("a01","Book",6000,10);
    }

    @AfterEach
    void cleanUp() {
        product = new Product();
    }

    @Test
    void saveProduct() {
        Product product = new Product("a111", "Shampo", 3000, 2);
        productServiceImpl.saveProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void saveProductSuccess() {
//        when(productRepository.save(new Product("a12", "Book", 5000, 5))).thenReturn(new Product("a12", "Book", 5000, 5));
        when(productRepository.save(product)).thenReturn(product);
        Product product = new Product("a01", "Book", 6000, 10);
        Product product1 = productServiceImpl.saveProduct(product);
        assertEquals("Book", product1.getProductName());
    }

    @Test
    void getAllProduct() {
        List<Product> products = new ArrayList<>();
        Product product = new Product("a01","Baju",6000,10);
        Product product1 = new Product("a02","Celana",9000,10);
        products.add(product);
        products.add(product1);

        when(productRepository.findAll()).thenReturn(products);
        List<Product> productList = productServiceImpl.getAllProduct(); //nampung
        assertEquals(2,productList.size()); // cek panjang array
        verify(productRepository, times(1)).findAll(); //bener gak dpanggil sekali
    }

    @Test
    void getProductById() {
        when(productRepository.findById("a01")).thenReturn(Optional.of(Optional.of(product).get()));
        Product product = productServiceImpl.getProductById("a01");
        assertEquals("Book", product.getProductName());
        assertEquals(6000, product.getProductPrice());
    }

    @Test
    void getProductByIdNotAvailable(){
        Throwable ex = assertThrows(DataNotFoundException.class,() -> productServiceImpl.getProductById("xx"));
        System.out.println(ex.getMessage());
        assertEquals("Resource product with ID not found", ex.getMessage());
    }

    @Test
    void deleteProduct() {
        productServiceImpl.deleteProduct("a12");
        verify(productRepository, times(1)).deleteById("a12");
    }

    @Test
    void getProductPerPage() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("asc","productName"));
        Page<Product> productPage = productServiceImpl.getProductPerPage(pageable);
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        assertEquals(productRepository.findAll(pageable),productPage);
    }
}