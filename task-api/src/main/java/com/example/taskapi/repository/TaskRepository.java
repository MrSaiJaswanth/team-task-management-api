package com.example.taskapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.taskapi.model.Task;

@Repository
public class TaskRepository {
	
	// In-memory storage keeps the case study simple and avoids database setup.
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();

 // AtomicLong ensures each task receives a unique ID.
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task save(Task task) {

        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }

        tasks.put(task.getId(), task);

        return task;
    }

    public void deleteById(Long id) {
        tasks.remove(id);
    }
}