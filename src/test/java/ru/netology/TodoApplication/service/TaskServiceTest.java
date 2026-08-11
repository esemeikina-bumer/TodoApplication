package ru.netology.TodoApplication.service;

import ru.netology.TodoApplication.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void createTask_ShouldReturnTaskWithId() {
        Task task = new Task();
        task.setTitle("Test Title");
        task.setDescription("Test Description");

        Task created = taskService.createTask(task);

        assertNotNull(created.getId());
        assertEquals(1L, created.getId());
        assertEquals("Test Title", created.getTitle());
        assertEquals("Test Description", created.getDescription());
        assertFalse(created.isCompleted());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Desc 1");

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Desc 2");

        taskService.createTask(task1);
        taskService.createTask(task2);

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("Desc");
        Task created = taskService.createTask(task);

        Task found = taskService.getTaskById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Test", found.getTitle());
    }

    @Test
    void getTaskById_NotFound_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.getTaskById(999L));
        assertEquals("Task not found with id: 999", exception.getMessage());
    }

    @Test
    void updateTask_ShouldUpdateTask() {
        Task task = new Task();
        task.setTitle("Original");
        task.setDescription("Desc");
        Task created = taskService.createTask(task);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated");
        updatedTask.setDescription("New Desc");
        updatedTask.setCompleted(true);

        Task result = taskService.updateTask(created.getId(), updatedTask);

        assertEquals("Updated", result.getTitle());
        assertEquals("New Desc", result.getDescription());
        assertTrue(result.isCompleted());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void updateTask_NotFound_ShouldThrowException() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated");
        updatedTask.setDescription("New Desc");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.updateTask(999L, updatedTask));
        assertEquals("Task not found with id: 999", exception.getMessage());
    }

    @Test
    void deleteTask_ShouldRemoveTask() {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("Desc");
        Task created = taskService.createTask(task);

        assertEquals(1, taskService.getAllTasks().size());

        taskService.deleteTask(created.getId());
        assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void deleteTask_NotFound_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.deleteTask(999L));
        assertEquals("Task not found with id: 999", exception.getMessage());
    }

    @Test
    void toggleComplete_ShouldChangeStatus() {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("Desc");
        Task created = taskService.createTask(task);

        assertFalse(created.isCompleted());

        Task toggled = taskService.toggleComplete(created.getId());
        assertTrue(toggled.isCompleted());
        assertNotNull(toggled.getUpdatedAt());

        toggled = taskService.toggleComplete(created.getId());
        assertFalse(toggled.isCompleted());
    }

    @Test
    void toggleComplete_NotFound_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.toggleComplete(999L));
        assertEquals("Task not found with id: 999", exception.getMessage());
    }
}