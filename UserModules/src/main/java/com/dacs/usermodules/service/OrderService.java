package com.dacs.usermodules.service;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.enumType.OrderStatus;
import com.dacs.usermodules.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> listByCustomer(Customer customer) {
        return orderRepository.findAllByCustomer(customer);
    }

    public Order get(Integer id) {
        return orderRepository.findById(id).orElseGet(() -> null);
    }

    public void cancelOrder(Integer id, String reason) {
        Order order = get(id);
        if(order != null) {
            order.setCancellationReason(reason);
            order.setOrderStatus(OrderStatus.CANCEL);
            orderRepository.save(order);
        }
    }

    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

}
