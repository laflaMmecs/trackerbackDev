package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.RabbitMQSender;
import com.example.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Управление задачами", description = "Endpoints для создания и просмотра задач.")
public class TaskController {

    private final TaskService taskService;
    private final RabbitMQSender rabbitMQSender; // <--- ВЕРНУЛИ 'final', ТЕПЕРЬ ОН БУДЕТ ИНИЦИАЛИЗИРОВАН В КОНСТРУКТОРЕ

    // Внедрение зависимости через конструктор
    public TaskController(TaskService taskService, RabbitMQSender rabbitMQSender) { // <--- ДОБАВИЛИ RabbitMQSender В ПАРАМЕТРЫ КОНСТРУКТОРА
        this.taskService = taskService;
        this.rabbitMQSender = rabbitMQSender; // <--- ИНИЦИАЛИЗИРУЕМ ЕГО ЗДЕСЬ
    }

    // Получение всех задач
    @Operation(summary = "Получить все задачи", description = "Возвращает список всех существующих задач.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Создание новой задачи
    @Operation(summary = "Создать новую задачу", description = "Создает новую задачу на основе переданных данных. Статус задачи автоматически устанавливается в 'NEW'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана и возвращена",
                    content = @Content(schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос, ошибка валидации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(task));
    }

    @PostMapping("/send-message-test") // Новый эндпоинт для теста
    public ResponseEntity<String> sendTestMessage(@RequestBody String message) {
        rabbitMQSender.sendNewTaskMessage(message); // <- Теперь rabbitMQSender не будет null
        return ResponseEntity.ok("Message sent to RabbitMQ: " + message);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}