package ru.yandex.javacourse.kanban.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import static ru.yandex.javacourse.kanban.tasks.Status.NEW;

public class Task {
    private String name;
    private String description;
    private Integer id;
    private Status status;
    private String type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task() {
        this("Задача", null, -1, NEW);
    }

    public Task(String type, String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public Task(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = NEW;
    }

    public Task(String name, Integer id) {
        this(name, "", id, NEW);
    }

    public Task(String name, String description, Integer id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(Task task) {
        this.name = task.name;
        this.description = task.description;
        this.id = task.id;
        this.type = task.type;
        this.duration = task.duration;
        this.startTime = task.startTime;
        this.status = task.getStatus();
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name)
                && Objects.equals(description, task.description)
                && Objects.equals(id, task.id)
                && status == task.status
                && Objects.equals(type, task.type)
                && Objects.equals(duration, task.duration)
                && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, type, duration, startTime) + 14;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type='"  + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

    public String toStringFile() {
        return         id +
                "," + type +
                "," + name +
                "," + status +
                "," + description;
    }
}
