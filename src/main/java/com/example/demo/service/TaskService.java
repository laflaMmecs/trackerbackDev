package com.example.demo.service;

import com.example.demo.model.enums.TaskStatus;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//Указывает Spring, что это компонент сервисного слоя
@RequiredArgsConstructor // Lombok: автоматически генерирует конструктор с final полями

public class TaskService {
    //1. Метод создания задачи
    public Task createTask(Task task) {
        task.setStatus(TaskStatus.NEW); // БИЗНЕС-ЛОГИКА: Принудительно устанавливаем статус NEW при создании
        return taskRepository.save(task); // Делегирование сохранения репозиторию
    }
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);// Делегирование поиска репозиторию
    }
    //Метод удаление задачи
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }





}
