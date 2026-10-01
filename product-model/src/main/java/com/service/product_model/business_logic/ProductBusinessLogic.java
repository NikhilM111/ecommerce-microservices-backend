package com.service.product_model.business_logic;

import com.service.product_model.dto.ProductRequest;
import com.service.product_model.dto.ProductResponse;
import com.service.product_model.entities.Product;
import com.service.product_model.repository.ProductRepository;
import com.service.product_model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductBusinessLogic implements ProductService {

    private final ProductRepository  productRepository;

    @Override
    public List<Product> getAllProducts() {

        List<Product> result = productRepository.findAll();
        return result;
    }

    @Override
    public ProductResponse addProduct(ProductRequest payLoad) {

        Product newProduct = new Product();

        newProduct.setProductName(payLoad.getProductName());
        newProduct.setProductDescription(payLoad.getProductDescription());
        newProduct.setPrice(payLoad.getPrice());
        newProduct.setSkuCode(payLoad.getSkuCode());
        productRepository.save(newProduct);

        ProductResponse result = new ProductResponse();
        result.setProductId(newProduct.getProductId());
        result.setProductName(newProduct.getProductName());
        result.setProductDescription(newProduct.getProductDescription());
        result.setPrice(newProduct.getPrice());
        result.setSkuCode(newProduct.getSkuCode());

        return result;
    }
}
