package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class TripEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String tripUrl;

    @Column(nullable = false)
    private String name;

    @Column
    private String location;

    @Column(nullable = false)
    private LocalDateTime time;

    public TripEntity(String tripUrl, String name, String location) {
        this.tripUrl = tripUrl;
        this.name = name;
        this.location = location;
        this.time = LocalDateTime.now();
    }

    protected TripEntity() {};

    public TripDto tripToDto() {
        return new TripDto(this.tripUrl, this.name, this.location);
    }
}
