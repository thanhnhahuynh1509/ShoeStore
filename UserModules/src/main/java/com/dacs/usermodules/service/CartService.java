package com.dacs.usermodules.service;

import com.dacs.entitymodules.Cart;
import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Product;
import com.dacs.usermodules.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> listByCustomer(Customer customer) {
        return cartRepository.findAllByCustomer(customer);
    }

    public String addCart(Product product, Customer customer, Integer quantity) {
        if(quantity > 5)
            return "NOT OK";
        Cart cart = cartRepository.findByProductAndCustomer(product, customer);

        if(cart != null) {
            quantity += cart.getQuantity();
            if(quantity > 5)
                return "NOT OK";
            cartRepository.updateCart(cart.getId(), quantity);
            return "OK UPDATE";
        } else {
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setProduct(product);
            cart.setQuantity(quantity);

            cartRepository.save(cart);
        }
        return "OK CREATE";
    }

    public void updateCart(Integer cartId, Integer quantity) {
        cartRepository.updateCart(cartId, quantity);
    }

    public void delete(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clear(Customer customer) {
        cartRepository.clear(customer);
    }
}
