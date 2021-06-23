package com.travelitems.beapi.domain;

import com.travelitems.beapi.domain.dto.TripDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tripUrl;

    @Column(nullable = false)
    private String name;

    @Column
    private String location;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isPublic;

    public Trip(String tripUrl, String name, String location, Long userId) {
        this.tripUrl = tripUrl;
        this.name = name;
        this.location = location;
        this.time = LocalDateTime.now();
        this.userId = userId;
    }

    protected Trip() {};

    public TripDto tripToDto() {
        return new TripDto(this.tripUrl, this.name, this.location, this.isPublic);
    }
}
