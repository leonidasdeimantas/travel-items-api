package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tripUrl;

    @Column(nullable = false)
    private LocalDateTime time;

    public List(String name, String tripUrl) {
        this.name = name;
        this.tripUrl = tripUrl;
    }

    protected List() {};
}
