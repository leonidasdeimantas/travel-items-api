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

    @Column
    private String task;

    @Column
    private String price;

    @Column
    private Long assigneeId;

    @Column
    private Long tripId;

    public Tasks(String task, String price, Long assigneeId, Long tripId) {
        this.task = task;
        this.price = price;
        this.assigneeId = assigneeId;
        this.tripId = tripId;
    }

    protected Tasks() {};
}
