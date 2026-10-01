package com.order.order_model.business_logic;

import com.order.order_model.dto.OrderItemsDto;
import com.order.order_model.dto.OrderRequest;
import com.order.order_model.entities.Order;
import com.order.order_model.entities.OrderItems;
import com.order.order_model.event.OrderPlacedEvent;
import com.order.order_model.repository.OrderRepository;
import com.order.order_model.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBusinessLogic implements OrderService {

    private final OrderRepository orderRepository;
    private final RestClient restClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public String placeOrder(OrderRequest payLoad) {

        Order newOrder = new Order();
        String orderNumber = UUID.randomUUID().toString();

        newOrder.setOrderNumber(orderNumber);
        newOrder.setCustomerId(payLoad.userId());

        List<OrderItems> items = payLoad.orderItems()
                .stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        newOrder.setOrderItems(items);

//        Check product avilability in inventory
        boolean isAllProductsInStock = items.stream().allMatch(item -> {
           Boolean inStock = restClient.get()
                   .uri("/api/inventory/in-stock?skuCode={skuCode}&quantity={quantity}",
                           item.getSkuCode(), item.getQuantity()).retrieve().body(Boolean.class);

           return inStock != null && inStock;
        });

        if(isAllProductsInStock) {
            orderRepository.save(newOrder);

            // Sent data to Kafka setup file
            OrderPlacedEvent event = new OrderPlacedEvent(orderNumber, payLoad.userId());
            kafkaTemplate.send("order-placed", event);

            return "Order Placed Successfully";
        } else {
            throw new IllegalArgumentException("Few products are out of stock");
        }
    }

    private OrderItems mapToEntity(OrderItemsDto dto) {
        OrderItems lineItem = new OrderItems();
        lineItem.setSkuCode(dto.skuCode());
        lineItem.setPrice(dto.price());
        lineItem.setQuantity(dto.quantity());
        return lineItem;
    }

    public String fallbackMethod(OrderRequest payLoad, Throwable throwable) {
        return "Oops! Something went wrong while placing the order. Please try again later. Error: " + throwable.getMessage();
    }
}
