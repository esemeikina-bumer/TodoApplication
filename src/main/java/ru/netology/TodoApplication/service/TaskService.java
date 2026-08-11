package ru.netology.TodoApplication.service;

import ru.netology.TodoApplication.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        task.setId(idCounter.getAndIncrement());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setUpdatedAt(LocalDateTime.now());
        return existingTask;
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        tasks.remove(task);
    }

    public Task toggleComplete(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(!task.isCompleted());
        task.setUpdatedAt(LocalDateTime.now());
        return task;
    }
}