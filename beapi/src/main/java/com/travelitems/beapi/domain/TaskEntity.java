package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String task;

    @Column
    private String price;

    @Column
    private Long assigneeId;

    @Column
    private String tags;

    @Column(nullable=false)
    private String tripUrl;

    @Column
    private boolean completed;

    public TaskEntity(String task, String price, Long assigneeId, String tripUrl, boolean completed) {
        this.task = task;
        this.price = price;
        this.assigneeId = assigneeId;
        this.tripUrl = tripUrl;
        this.completed = completed;
    }

    protected TaskEntity() {};
}