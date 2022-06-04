package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "districts")
public class District {

    @Id
    private String id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public District() {

    }

    public District(String id) {
        this.id = id;
    }
}
