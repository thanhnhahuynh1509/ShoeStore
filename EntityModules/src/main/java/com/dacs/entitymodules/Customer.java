package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="customers")
@Getter
@Setter
public class Customer {

    public static final String IMAGE_ROOT_DIR = "customer-images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private Date createdDate = new Date();
    private String verifyToken;
    private String forgetToken;
    private String image;
    private boolean enabled = false;
    private String type;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    public String getImagePath() {
        if(id == null || image == null || image.isEmpty())
            return "/common/images/default-user.png";
        return "/" + IMAGE_ROOT_DIR + "/" + this.id + "/" + this.image;
    }

}
