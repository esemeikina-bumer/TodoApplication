package ru.netology.TodoApplication.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.netology.TodoApplication.model.Task;
//import ru.netology.TodoApplication.service.TaskService;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(TaskController.class)
//class TaskControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TaskService taskService;
//
//    private ObjectMapper objectMapper;
//    private Task testTask;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        testTask = new Task();
//        testTask.setId(1L);
//        testTask.setTitle("Test Task");
//        testTask.setDescription("Test Description");
//        testTask.setCompleted(false);
//        testTask.setCreatedAt(LocalDateTime.now());
//        testTask.setUpdatedAt(LocalDateTime.now());
//    }
//
//    @Test
//    void getAllTasks_ShouldReturnList() throws Exception {
//        Task task1 = new Task();
//        task1.setId(1L);
//        task1.setTitle("Task 1");
//        task1.setDescription("Desc 1");
//        task1.setCreatedAt(LocalDateTime.now());
//        task1.setUpdatedAt(LocalDateTime.now());
//
//        Task task2 = new Task();
//        task2.setId(2L);
//        task2.setTitle("Task 2");
//        task2.setDescription("Desc 2");
//        task2.setCreatedAt(LocalDateTime.now());
//        task2.setUpdatedAt(LocalDateTime.now());
//
//        List<Task> tasks = Arrays.asList(task1, task2);
//
//        when(taskService.getAllTasks()).thenReturn(tasks);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(get("/api/tasks")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].title").value("Task 1"))
//                .andExpect(jsonPath("$[1].title").value("Task 2"));
//    }
//
//    @Test
//    void getTaskById_ShouldReturnTask() throws Exception {
//        when(taskService.getTaskById(1L)).thenReturn(testTask);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(get("/api/tasks/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("Test Task"));
//    }
//
//    @Test
//    void getTaskById_NotFound_ShouldReturn404() throws Exception {
//        when(taskService.getTaskById(999L))
//                .thenThrow(new RuntimeException("Task not found with id: 999"));
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(get("/api/tasks/999")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createTask_ShouldReturnCreated() throws Exception {
//        Task newTask = new Task();
//        newTask.setTitle("New Task");
//        newTask.setDescription("New Description");
//
//        Task savedTask = new Task();
//        savedTask.setId(1L);
//        savedTask.setTitle("New Task");
//        savedTask.setDescription("New Description");
//        savedTask.setCompleted(false);
//        savedTask.setCreatedAt(LocalDateTime.now());
//        savedTask.setUpdatedAt(LocalDateTime.now());
//
//        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);
//
//        String json = objectMapper.writeValueAsString(newTask);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(post("/api/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("New Task"));
//    }
//
//    @Test
//    void createTask_InvalidTitle_ShouldReturnBadRequest() throws Exception {
//        Task invalidTask = new Task();
//        invalidTask.setTitle("");
//        invalidTask.setDescription("Description");
//
//        String json = objectMapper.writeValueAsString(invalidTask);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(post("/api/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateTask_ShouldReturnUpdated() throws Exception {
//        Task updatedTask = new Task();
//        updatedTask.setTitle("Updated Task");
//        updatedTask.setDescription("Updated Description");
//        updatedTask.setCompleted(true);
//
//        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(testTask);
//
//        String json = objectMapper.writeValueAsString(updatedTask);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(put("/api/tasks/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Test Task"));
//    }
//
//    @Test
//    void deleteTask_ShouldReturnNoContent() throws Exception {
//        doNothing().when(taskService).deleteTask(1L);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(delete("/api/tasks/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void toggleComplete_ShouldReturnUpdated() throws Exception {
//        Task toggledTask = new Task();
//        toggledTask.setId(1L);
//        toggledTask.setTitle("Test Task");
//        toggledTask.setDescription("Test Description");
//        toggledTask.setCompleted(true);
//        toggledTask.setCreatedAt(LocalDateTime.now());
//        toggledTask.setUpdatedAt(LocalDateTime.now());
//
//        when(taskService.toggleComplete(1L)).thenReturn(toggledTask);
//
//        // ИЗМЕНЕНО: добавила /api/
//        mockMvc.perform(patch("/api/tasks/1/complete")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.completed").value(true));
//    }
//}


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.TodoApplication.model.Task;
import ru.netology.TodoApplication.service.TaskService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private ObjectMapper objectMapper;
    private Task testTask;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setCompleted(false);
        testTask.setCreatedAt(LocalDateTime.now());
        testTask.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getAllTasks_ShouldReturnList() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Desc 1");
        task1.setCreatedAt(LocalDateTime.now());
        task1.setUpdatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Desc 2");
        task2.setCreatedAt(LocalDateTime.now());
        task2.setUpdatedAt(LocalDateTime.now());

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(testTask);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void getTaskById_NotFound_ShouldReturn404() throws Exception {
        when(taskService.getTaskById(999L))
                .thenThrow(new RuntimeException("Task not found with id: 999"));

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(get("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_ShouldReturnCreated() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setDescription("New Description");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("New Task");
        savedTask.setDescription("New Description");
        savedTask.setCompleted(false);
        savedTask.setCreatedAt(LocalDateTime.now());
        savedTask.setUpdatedAt(LocalDateTime.now());

        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        String json = objectMapper.writeValueAsString(newTask);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void createTask_InvalidTitle_ShouldReturnBadRequest() throws Exception {
        Task invalidTask = new Task();
        invalidTask.setTitle("");
        invalidTask.setDescription("Description");

        String json = objectMapper.writeValueAsString(invalidTask);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTask_ShouldReturnUpdated() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setCompleted(true);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(testTask);

        String json = objectMapper.writeValueAsString(updatedTask);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(delete("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void toggleComplete_ShouldReturnUpdated() throws Exception {
        Task toggledTask = new Task();
        toggledTask.setId(1L);
        toggledTask.setTitle("Test Task");
        toggledTask.setDescription("Test Description");
        toggledTask.setCompleted(true);
        toggledTask.setCreatedAt(LocalDateTime.now());
        toggledTask.setUpdatedAt(LocalDateTime.now());

        when(taskService.toggleComplete(1L)).thenReturn(toggledTask);

        // ИЗМЕНЕНО: убрала /api
        mockMvc.perform(patch("/tasks/1/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }
}