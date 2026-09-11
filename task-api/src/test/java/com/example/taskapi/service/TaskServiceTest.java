package com.example.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taskapi.dto.CreateTaskRequest;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.model.Task;
import com.example.taskapi.model.TaskStatus;
import com.example.taskapi.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }
    
    @Test
    void startTask_shouldSetStatusToInProgress() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task))
                .thenReturn(task);

        Task result = taskService.startTask(1L);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskRepository).save(task);
    }
    
    @Test
    void markComplete_shouldSetStatusToCompleted() {

        Task task = new Task();

        task.setId(1L);
        task.setTitle("Learn Spring Boot");
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task))
                .thenReturn(task);

        Task result = taskService.markComplete(1L);

        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(taskRepository).save(task);
    }
    
    @Test
    void createTask_shouldCreatePendingTask() {

        CreateTaskRequest request = new CreateTaskRequest(
                "Learn REST API",
                "Build a Spring Boot API",
                null
        );

        when(taskRepository.save(org.mockito.ArgumentMatchers.any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.createTask(request);

        assertEquals("Learn REST API", result.getTitle());
        assertEquals("Build a Spring Boot API", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
    }

    @Test
    void getTaskById_shouldThrowExceptionWhenTaskDoesNotExist() {

        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,() -> taskService.getTaskById(999L));
    }
}