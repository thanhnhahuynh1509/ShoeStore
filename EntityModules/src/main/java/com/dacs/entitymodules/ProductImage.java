package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public ProductImage() {

    }

    public ProductImage(Integer id, String name, Product product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }

    @Transient
    public String getImagePath() {
        if(this.name == null || this.name.isEmpty()) return "/common/images/default-product.png";
        return "/" + Product.IMAGE_ROOT_DIR + "/" + product.getId() + "/extra/" + name;
    }

}
