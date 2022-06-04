package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wards")
public class Ward {

    @Id
    private String id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;


}
