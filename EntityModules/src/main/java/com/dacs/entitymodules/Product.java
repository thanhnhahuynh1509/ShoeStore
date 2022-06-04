package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product {

    @Transient
    public static final String IMAGE_ROOT_DIR = "product-images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String alias;

    @Column(name="short_description")
    private String shortDescription;

    @Column(name="full_description")
    private String fullDescription;

    @Column(name="main_image")
    private String mainImage;

    @Column(name="created_time")
    private Date createdTime;

    @Column(name="updated_time")
    private Date updatedTime;

    private boolean enabled = true;

    @Column(name="in_stock")
    private boolean inStock = true;

    private double price;
    private double cost;

    @Column(name="discount_percent")
    private double discountPercent;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<ProductDetail> productDetails = new ArrayList<>();

    public Product() {

    }

    public Product(Integer id) {
        this.id = id;
    }

    @Transient
    public void addExtraImage(Integer id, String name) {
        productImages.add(new ProductImage(id, name, this));
    }

    @Transient
    public void addProductDetail(Integer id, String name, String value) {
        productDetails.add(new ProductDetail(id, name, value, this));
    }

    @Transient
    public String getMainImagePath() {
        if(this.mainImage == null || this.mainImage.isEmpty()) return "/common/images/default-product.png";
        return "/" + IMAGE_ROOT_DIR + "/" + this.id + "/" + this.mainImage;
    }

    @Transient
    public boolean containsImageName(String name) {
        return productImages.stream().anyMatch(m -> m.getName().equals(name));
    }

}
