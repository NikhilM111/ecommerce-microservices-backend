package com.service.product_model.controller;

import com.service.product_model.dto.ProductRequest;
import com.service.product_model.dto.ProductResponse;
import com.service.product_model.entities.Product;
import com.service.product_model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        List<Product> result = productService.getAllProducts();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest payLoad) {

        ProductResponse result = productService.addProduct(payLoad);
        return ResponseEntity.ok(result);
    }
}
