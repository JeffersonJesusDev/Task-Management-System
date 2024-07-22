package com.jdev.TaskManagementSystem.controllers;
import com.jdev.TaskManagementSystem.dto.TaskDTO;
import com.jdev.TaskManagementSystem.enums.Status;
import com.jdev.TaskManagementSystem.model.Task;
import com.jdev.TaskManagementSystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody @Validated TaskDTO task){
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<Task> createTaskById(@PathVariable Long userId, @RequestBody TaskDTO taskDTO) throws Exception {
        Task task = taskService.createTaskForUser(userId, taskDTO.taskName(), taskDTO.status());
        return ResponseEntity.ok(task);
    }


    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

}
