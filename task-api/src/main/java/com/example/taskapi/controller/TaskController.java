package com.example.taskapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.dto.UpdateTaskRequest;
import com.example.taskapi.model.Task;
import com.example.taskapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {

        Task task = taskService.createTask(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(task);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {

        Task task = taskService.updateTask(id, request);

        return ResponseEntity.ok(task);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
    
    // Moves a task through the development workflow from pending to in-progress.
    @PatchMapping("/{id}/start")
    public ResponseEntity<Task> startTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.startTask(id));
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markComplete(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.markComplete(id));
    }
    
    
}