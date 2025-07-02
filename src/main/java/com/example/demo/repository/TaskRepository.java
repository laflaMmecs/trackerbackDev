package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Кастомный метод для поиска по статусу
    List<Task> findByStatus(TaskStatus status); //абстрактный метод

    // Spring Data JPA автоматически реализует:
    // save(), findAll(), deleteById() и др.
}
