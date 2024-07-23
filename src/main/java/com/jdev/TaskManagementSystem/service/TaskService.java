package com.jdev.TaskManagementSystem.service;

import com.jdev.TaskManagementSystem.dto.TaskDTO;
import com.jdev.TaskManagementSystem.enums.Status;
import com.jdev.TaskManagementSystem.model.Task;
import com.jdev.TaskManagementSystem.model.User;
import com.jdev.TaskManagementSystem.repositories.TaskRepository;
import com.jdev.TaskManagementSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TaskService {

    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void saveTask(Task task) {
        this.taskRepository.save(task);
    }

    public Task addTask(TaskDTO task) {
        Task newTask = new Task();
        this.saveTask(newTask);
        return newTask;
    }

    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.taskRepository.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public ResponseEntity<Task> createTaskForUser(Long userId, String taskName, Status status) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Task task = new Task();
            task.setTaskName(taskName);
            task.setStatus(status);
            task.setUser(user);
            user.getTask().add(task);
            taskRepository.save(task);
            userRepository.save(user);
            return ResponseEntity.ok(task);
        } else {
            throw new Exception("User not found");
        }
    }

    public List<Task> findTasksByUserId(@PathVariable Long userId) throws Exception {
        if(userId == null){
            throw new Exception("User ID must not be null");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return taskRepository.findByUserId(user.getId());
        } else {
            throw new Exception("User not found");
        }
    }

//    @Transactional
//    public ResponseEntity<Void> deleteTaskById(Long taskId) throws Exception {
//        Optional<Task> taskOptional = taskRepository.findById(taskId);
//        if (taskOptional.isPresent()) {
//            Task task = taskOptional.get();
//            taskRepository.deleteById(task.getTaskId());
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            throw new Exception("Task not found");
//        }
//    }

//    @Transactional
//    public void deleteTaskById(Long taskId) throws Exception {
//        LOGGER.info("Attempting to delete task with ID: " + taskId);
//        Optional<Task> taskOptional = taskRepository.findById(taskId);
//        if (taskOptional.isPresent()) {
//            taskRepository.deleteById(taskId);
//            LOGGER.info("Task deleted successfully");
//        } else {
//            LOGGER.warning("Task not found with ID: " + taskId);
//            throw new Exception("Task not found");
//        }
//    }

    @Transactional
    public ResponseEntity<Void> deleteTaskById(Long taskId) {
        logger.info("Trying to delete task with id: " + taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            logger.info("Task found, deleting...");
            taskRepository.deleteById(taskId);
            logger.info("Task deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warning("Task not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
