package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "category_posts")
public class CategoryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String alias;

    public CategoryPost() {

    }

    public CategoryPost(Integer id) {
        this.id = id;
    }

    public CategoryPost(String name) {
        this.name = name;
    }
}
