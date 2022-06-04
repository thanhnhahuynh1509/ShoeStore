package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {

    @Transient
    public static final String IMAGE_ROOT_DIR = "brand-images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
    private String image;

    @ManyToMany
    @JoinTable(name="brands_categories",
            joinColumns = @JoinColumn(name="brand_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();

    @Transient
    public String getImagePath() {
        if(this.image == null || this.image.isEmpty()) return "/common/images/default-product.png";
        return "/" + IMAGE_ROOT_DIR + "/" + this.id + "/" + this.image;
    }
}
