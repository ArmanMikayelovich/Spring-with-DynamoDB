package com.mikayelovich.spring_dynamo.dynamodb_spring.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.mikayelovich.spring_dynamo.dynamodb_spring.model.Product;
import com.mikayelovich.spring_dynamo.dynamodb_spring.model.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final DynamoDBMapper dynamoDBMapper;


    @Override
    public List<ProductDTO> getAllProducts() {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper
                .scan(Product.class, dynamoDBScanExpression)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public ProductDTO getProductById(String id) {
        return Optional.ofNullable(dynamoDBMapper.load(Product.class, id)).map(this::convertToDTO).orElse(null);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        dynamoDBMapper.save(product);
        return convertToDTO(product);
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO dto) {
        Product product = dynamoDBMapper.load(Product.class, id);
        BeanUtils.copyProperties(dto, product);

        dynamoDBMapper.save(product);

        return convertToDTO(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = dynamoDBMapper.load(Product.class, id);

        if (product != null) {
            dynamoDBMapper.delete(product);
        }
    }

    @Override
    public List<ProductDTO> getProductsWithStockMoreThan(int count) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("stockCount > :minStock")
                .withExpressionAttributeValues(Map.of(":minStock", new AttributeValue().withN(String.valueOf(count))));
        return dynamoDBMapper
                .scan(Product.class, dynamoDBScanExpression)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }
}

