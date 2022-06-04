package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Transient
    public static final String IMAGE_ROOT_DIR = "post-images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String mainImage;
    private String description;
    private String fullContent;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "category_post_id")
    private CategoryPost categoryPost;

    @Transient
    public String getImagePath() {
        if(this.id == null || this.mainImage == null || this.mainImage.isEmpty())
            return "/common/images/default-product.png";
        return "/" + IMAGE_ROOT_DIR + "/" + this.id + "/" + this.mainImage;
    }
}
