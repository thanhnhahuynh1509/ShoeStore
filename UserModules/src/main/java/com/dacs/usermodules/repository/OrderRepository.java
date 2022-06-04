package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAllByCustomer(Customer customer);

}
