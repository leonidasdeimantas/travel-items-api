package com.travelitems.beapi.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Assignee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tripUrl;

    @Column(nullable = false)
    private LocalDateTime time;

    public Assignee(String name, String tripUrl) {
        this.name = name;
        this.tripUrl = tripUrl;
    }

    protected Assignee() {};
}
