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

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String tripUrl;

    public Peoples(String name, String tripUrl) {
        this.name = name;
        this.tripUrl = tripUrl;
    }

    protected  Peoples() {};
}
