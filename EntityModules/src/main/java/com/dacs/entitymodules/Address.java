package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String details;
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name="district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name="ward_id")
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String getFullAddress() {
        return details + ", " + ward.getName() + ", " + district.getName() + ", " + city.getName();
    }

}
