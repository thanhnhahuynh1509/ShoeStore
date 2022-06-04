package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private double cost;
    private double price;

    @Column(name = "discount_percent")
    private double discountPercent;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

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
