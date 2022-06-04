package com.dacs.usermodules.service;

import com.dacs.entitymodules.Address;
import com.dacs.entitymodules.Customer;
import com.dacs.usermodules.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> listByCustomerEmail(String email) {
        return addressRepository.findAllByCustomerEmail(email);
    }

    public Address get(Integer id) {
        return addressRepository.findById(id).orElseGet(() -> null);
    }

    public Address getAddressDefault(Customer customer) {
        return addressRepository.findAddressDefault(customer);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public void setDefaultAddress(Customer customer, Integer addressId) {
        addressRepository.setDefaultAddressFault4All(customer);
        addressRepository.setDefaultAddress(addressId);
    }

    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }

}
