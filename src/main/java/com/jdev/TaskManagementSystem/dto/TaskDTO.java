package com.jdev.TaskManagementSystem.dto;

import com.jdev.TaskManagementSystem.enums.Status;

public record TaskDTO(String taskName, Status status) {
}
