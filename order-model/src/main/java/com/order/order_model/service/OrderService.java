package com.order.order_model.service;

import com.order.order_model.dto.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest payLoad);
}
