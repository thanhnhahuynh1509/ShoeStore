package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public double getDiscountPrice() {
        return product.getPrice() * (product.getDiscountPercent() / 100);
    }

    public double getPrice() {
        return product.getPrice() - getDiscountPrice();
    }

    public double getTotalPrice() {
        return getPrice() * quantity;
    }
}
