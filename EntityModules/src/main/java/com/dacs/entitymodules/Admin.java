package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="admins")
@Getter
@Setter
public class Admin {

    @Transient
    public static final String IMAGE_ROOT_DIR = "admin-images";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admins_roles",
            joinColumns = @JoinColumn(name="admin_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    public String getImagePath() {
        if(this.image == null || this.image.isEmpty())
            return "/common/images/default-user.png";
        return "/" + IMAGE_ROOT_DIR + "/" + this.id + "/" + this.image;
    }
}
