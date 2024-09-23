package com.mikayelovich.spring_dynamo.dynamodb_spring.controller;


import com.mikayelovich.spring_dynamo.dynamodb_spring.model.ProductDTO;
import com.mikayelovich.spring_dynamo.dynamodb_spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productServiceBL;


    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productServiceBL.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable String id) {
        return productServiceBL.getProductById(id);
    }


    @GetMapping("/with-stock/{count}")
    public List<ProductDTO> getProductInStock(@PathVariable Integer count) {
        return productServiceBL.getProductsWithStockMoreThan(count);
    }



    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productServiceBL.createNewProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        return productServiceBL.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productServiceBL.deleteProduct(id);
    }


}