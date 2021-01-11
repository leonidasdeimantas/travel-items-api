package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String tripUrl;

    @Column
    private String name;

    @Column
    private String location;

    public Trip(String tripUrl, String name, String location) {
        this.tripUrl = tripUrl;
        this.name = name;
        this.location = location;
    }

    protected Trip() {};
}
