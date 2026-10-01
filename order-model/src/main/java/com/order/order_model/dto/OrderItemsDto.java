package com.order.order_model.dto;

import java.math.BigDecimal;

public record OrderItemsDto(String skuCode, BigDecimal price, Integer quantity) {
}
