package com.travelitems.beapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String task;

    @Column
    private String price;

    @Column
    private Long assigneeId;

    @Column
    private String tags;

    @Column(nullable = false)
    private String tripUrl;

    @Column
    private boolean completed;

    @Column(nullable = false)
    private LocalDateTime time;

    public Task(String task, String price, Long assigneeId, String tripUrl, boolean completed) {
        this.task = task;
        this.price = price;
        this.assigneeId = assigneeId;
        this.tripUrl = tripUrl;
        this.completed = completed;
    }

    protected Task() {};
}
