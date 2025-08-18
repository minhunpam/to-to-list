package com.example.to_do_list.domain;

import jakarta.persistence.*;

import java.time.*;


@Entity
@Table(name = "todos")
public class Todo {
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    boolean completed = false;

    @Column(nullable = false)
    private LocalDate due_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    @Column(nullable = false, updatable = false)
    private Instant created_at = Instant.now();

    @Column(nullable = false)
    private Instant updated_at = Instant.now();

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.created_at = now;
        this.updated_at = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updated_at = Instant.now();
    }

    public Long getId() { return id; }

    public String getTitle() {return title;}
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDue_date() { return due_date; }
    public void setDue_date(LocalDate due_date) { this.due_date = due_date; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Instant getCreated_at() { return created_at; }
    public Instant getUpdated_at() { return updated_at; }

}
