package com.service.product_model.service;

import com.service.product_model.dto.ProductRequest;
import com.service.product_model.dto.ProductResponse;
import com.service.product_model.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    ProductResponse addProduct(ProductRequest payLoad);
}
