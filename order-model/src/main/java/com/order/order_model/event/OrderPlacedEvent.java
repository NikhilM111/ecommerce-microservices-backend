package com.order.order_model.event;

public record OrderPlacedEvent(String orderNumber, Long userId) {
}
