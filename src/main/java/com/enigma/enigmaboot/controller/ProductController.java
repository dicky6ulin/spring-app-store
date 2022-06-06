package com.enigma.enigmaboot.controller;

import com.enigma.enigmaboot.constant.ApiURLConstant;
import com.enigma.enigmaboot.constant.ResponseMessage;
import com.enigma.enigmaboot.entity.Product;
import com.enigma.enigmaboot.service.ProductService;
import com.enigma.enigmaboot.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiURLConstant.PRODUCT)
public class ProductController {

    @Autowired
    ProductService productService;

//    @ResponseStatus(HttpStatus.CREATED) //200 OK | 200 Created
//    @PostMapping
//    public Product addProduct (@RequestBody Product product) {
//        return  productService.saveProduct(product);
//    }
    @PostMapping
    public ResponseEntity<Response<Product>> addProduct (@RequestBody Product product) {
        Response<Product> response = new Response<>();
        String message = String.format(ResponseMessage.DATA_INSERT, "product");
        response.setMessage(message);
        response.setData(productService.saveProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

//    @GetMapping
//    public List<Product> getAllProduct() {
//        return productService.getAllProduct();
//    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

//    @GetMapping
//    public PageResponseWrapper<Product> getProductPerPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
//                                                          @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
//                                                          @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
//                                                          @RequestParam(name = "direction", defaultValue = "asc") String direction) {
//        Sort sorting = Sort.by(Sort.Direction.fromString(direction), sortBy);
//        Pageable pageable = PageRequest.of(page,sizePerPage,sorting);
//        Page<Product> productPage = productService.getProductPerPage(pageable);
//        return new PageResponseWrapper<>(productPage);
//    }

    @GetMapping
    public Page<Product> getProductPerPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                           @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
                                           @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        Sort sorting = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page,sizePerPage,sorting);
        return productService.getProductPerPage(pageable);
    }

    @PutMapping
    public Product updateProduct (@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping
    public void deleteProduct (@RequestParam String id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/pictures")
    public void registerProductPictures(@RequestPart(name = "pictures", required = false) MultipartFile file,
                                        @RequestPart(name = "product") String product) {
        Product product1 = new Product();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            product1 = objectMapper.readValue(product, Product.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productService.saveProductPictures(product1, file);
    }
}