package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("FROM Customer c WHERE c.email like ?1")
    Customer findByEmail(String email);

    @Query("FROM Customer c WHERE c.verifyToken like ?1")
    Customer findByVerifyToken(String token);

    @Query("FROM Customer c WHERE c.forgetToken like ?1")
    Customer findByForgetToken(String token);

    @Modifying
    @Query("UPDATE Customer c SET c.enabled = true, c.verifyToken = null WHERE c.id = ?1")
    void enabled(Integer customerId);

}
