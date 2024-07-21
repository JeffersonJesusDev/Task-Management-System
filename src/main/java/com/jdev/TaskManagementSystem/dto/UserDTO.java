package com.jdev.TaskManagementSystem.dto;

import com.jdev.TaskManagementSystem.model.Task;

import java.util.List;

public record UserDTO(
        String name,
        String email,
        String password,
        List<Task> task) {
}
