package com.order.order_model.dto;

import java.util.List;

public record OrderRequest(Long userId, List<OrderItemsDto> orderItems) {
}
