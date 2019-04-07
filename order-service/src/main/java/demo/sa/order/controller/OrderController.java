package demo.sa.order.controller;

import demo.sa.order.OrderApi;
import demo.sa.order.model.dto.OrderDto;
import demo.sa.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @Override
    public ResponseEntity<OrderDto> getOrder(String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(String orderId, OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderDto));
    }
}
