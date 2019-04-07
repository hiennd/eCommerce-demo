package demo.sa.order.service;

import demo.sa.order.exception.OrderNotFoundException;
import demo.sa.order.mapper.OrderMapper;
import demo.sa.order.model.OrderEntity;
import demo.sa.order.persistence.OrderRepository;
import demo.sa.order.model.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = orderMapper.map(order, OrderEntity.class);
        OrderEntity newOrder = orderRepository.save(orderEntity);
        return orderMapper.map(newOrder, OrderDto.class);
    }

    public OrderDto getOrder(String orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return orderMapper.map(orderEntity, OrderDto.class);
    }

    public List<OrderDto> getOrders() {
        List<OrderEntity> orderEntities =  orderRepository.findAll();
        return orderEntities
                .stream()
                .map(e -> orderMapper.map(e, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto updateOrder(String orderId, OrderDto order) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        OrderEntity newOrderEntity = orderMapper.map(order, OrderEntity.class);
        newOrderEntity.setOrderId(orderId);
        orderRepository.save(newOrderEntity);
        return order;
    }
    
}
