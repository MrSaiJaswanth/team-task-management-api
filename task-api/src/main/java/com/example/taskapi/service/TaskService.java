package com.example.taskapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.dto.UpdateTaskRequest;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.model.Task;
import com.example.taskapi.model.TaskStatus;
import com.example.taskapi.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    
    public Task startTask(Long id) {
        Task task = getTaskById(id);
        
        // Starting a task moves it from pending to in-progress.
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());
        
        return taskRepository.save(task);
    }
    
    public Task markComplete(Long id) {

        Task task = getTaskById(id);

        task.setStatus(TaskStatus.COMPLETED);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
    
    
    public Task createTask(CreateTaskRequest request) {

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        
        // New tasks always enter the workflow in the pending state.
        task.setStatus(TaskStatus.PENDING);
        
        // Store creation and update times when the task is first created.
        LocalDateTime now = LocalDateTime.now();

        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, UpdateTaskRequest request) {

        Task task = getTaskById(id);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        
        // Keep the original creation time and update only the modification time.
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {

        getTaskById(id);

        taskRepository.deleteById(id);
    }
}