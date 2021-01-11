package com.travelitems.beapi.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Peoples {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Long tripId;

    public Peoples(String name, Long tripId) {
        this.name = name;
        this.tripId = tripId;
    }

    protected  Peoples() {};
}
