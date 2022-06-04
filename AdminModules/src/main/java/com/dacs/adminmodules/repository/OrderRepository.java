package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.enumType.OrderStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = ?2 WHERE o.id = ?1")
    void updateOrderStatus(Integer id, OrderStatus status);
}
