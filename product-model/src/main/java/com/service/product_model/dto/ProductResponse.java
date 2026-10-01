package com.service.product_model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private String skuCode;
}
