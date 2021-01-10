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
    private Long peopleId;

    public Tasks(String task, Long peopleId) {
        this.task = task;
        this.peopleId = peopleId;
    }
}
