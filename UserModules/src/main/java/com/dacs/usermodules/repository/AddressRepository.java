package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Address;
import com.dacs.entitymodules.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Query("FROM Address WHERE customer.email LIKE ?1")
    List<Address> findAllByCustomerEmail(String email);

    @Query("FROM Address a WHERE a.defaultAddress = true AND a.customer = ?1")
    Address findAddressDefault(Customer customer);

    @Modifying
    @Query("UPDATE Address a SET a.defaultAddress = false WHERE a.customer = ?1")
    void setDefaultAddressFault4All(Customer customer);

    @Modifying
    @Query("UPDATE Address SET defaultAddress = true WHERE id = ?1")
    void setDefaultAddress(Integer addressId);
}
