package com.jdev.TaskManagementSystem.controllers;
import com.jdev.TaskManagementSystem.dto.TaskDTO;
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
        Task task = taskService.createTaskForUser(userId, taskDTO.taskName(), taskDTO.status()).getBody();
        return ResponseEntity.ok(task);
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Task>> findTaskByUserId(@PathVariable Long userId) throws Exception {
        List<Task> newtask = taskService.findTasksByUserId(userId);
        return new ResponseEntity<>(newtask, HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        return this.taskService.getAllTasks();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask (@PathVariable Long taskId, @RequestBody TaskDTO data) throws Exception {
        taskService.updateTask(taskId, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
