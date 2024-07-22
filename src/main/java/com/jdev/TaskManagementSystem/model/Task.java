package com.jdev.TaskManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jdev.TaskManagementSystem.dto.TaskDTO;
import com.jdev.TaskManagementSystem.enums.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Task(Long id, String taskName, Status status, User user) {
        this.id = id;
        this.taskName = taskName;
        this.status = status;
        this.user = user;
    }
    public Task(TaskDTO data) {
        this.taskName = data.taskName();
        this.status = data.status();
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
