package com.example.demo.model;

import com.example.demo.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;  // Правильно для Spring Boot 3.x
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    // Стратегия генерации ID (автоинкремент в БД)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Определение имени колонки в таблице
    @Column(name = "id")
    private Long id;

    // Аннотация валидации: поле не может быть пустым
    @NotBlank(message = "Title is required")
    // Определение колонки с запретом NULL-значений
    @Column(name = "title", nullable = false)
    private String title;

    // Определение колонки с типом TEXT в БД
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Указание, что enum должен сохраняться как строка
    @Enumerated(EnumType.STRING)
    // Колонка не может быть NULL
    @Column(name = "status", nullable = false)
    // Установка значения по умолчанию
    private TaskStatus status = TaskStatus.NEW;

    // Автоматическая установка времени создания при сохранении
    @CreationTimestamp
    // Колонка только для чтения (не обновляется)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Автоматическое обновление времени при изменениях
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Пример связи с пользователем (опционально)
    // Аннотация отношения "многие-к-одному"
    @ManyToOne(fetch = FetchType.LAZY)
    // Указание колонки для хранения связи
    @JoinColumn(name = "user_id")
    private User user;





}


