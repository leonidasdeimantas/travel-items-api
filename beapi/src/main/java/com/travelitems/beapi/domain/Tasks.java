package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Tasks {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String task;

    @Column
    private String price;

    @Column
    private Long assigneeId;

    @Column(nullable=false)
    private String tripUrl;

    public Tasks(String task, String price, Long assigneeId, String tripUrl) {
        this.task = task;
        this.price = price;
        this.assigneeId = assigneeId;
        this.tripUrl = tripUrl;
    }

    protected Tasks() {};
}
