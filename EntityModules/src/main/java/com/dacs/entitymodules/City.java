package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City {

    @Id
    private String id;
    private String name;
    private String type;

    public City() {

    }

    public City(String id) {
        this.id = id;
    }
}
