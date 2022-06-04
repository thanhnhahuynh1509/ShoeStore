package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Cart;
import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {

    @Query("FROM Cart c WHERE c.product = ?1 AND c.customer = ?2")
    Cart findByProductAndCustomer(Product product, Customer customer);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = ?2 WHERE c.id = ?1")
    void updateCart(Integer cartId, Integer quantity);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer = ?1")
    void clear(Customer customer);

    @Query("FROM Cart c WHERE c.customer = ?1")
    List<Cart> findAllByCustomer(Customer customer);
}
