package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.OrderRepository;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.enumType.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> list(OrderStatus orderStatus) {
        return orderRepository.findAllByOrderStatus(orderStatus);
    }

    public Order get(Integer id) {
        return orderRepository.findById(id).orElseGet(() -> null);
    }

    public void updateOrderStatus(Integer id, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(id, orderStatus);
    }
}
