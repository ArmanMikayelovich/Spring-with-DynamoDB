package com.mikayelovich.spring_dynamo.dynamodb_spring.service;

import com.mikayelovich.spring_dynamo.dynamodb_spring.model.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(String id);

    ProductDTO createNewProduct(ProductDTO dto);

    ProductDTO updateProduct(String id, ProductDTO dto);

    void deleteProduct(String id);


    public List<ProductDTO> getProductsWithStockMoreThan(int count);

}

