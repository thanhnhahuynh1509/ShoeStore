package com.dacs.entitymodules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Date createdDate = new Date();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Transient
    public String getFormatDate() {
        return new SimpleDateFormat("HH:ss dd/MM/yyyy").format(createdDate);
    }
}
