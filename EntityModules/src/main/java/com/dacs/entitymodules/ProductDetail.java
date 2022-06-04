package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public ProductDetail() {
    }

    public ProductDetail(Integer id, String name, String value, Product product) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.product = product;
    }
}
