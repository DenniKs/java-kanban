package ru.yandex.javacourse.kanban.tasks;

import java.util.Objects;
import static ru.yandex.javacourse.kanban.tasks.Status.NEW;

public class Task {
    private String name;
    private String description;
    private Integer id;
    private Status status;
    private String type;

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
        this.status = task.status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(getName(), task.getName())
                && Objects.equals(getDescription(), task.getDescription())
                && Objects.equals(getId(), task.getId())
                && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getId(), getStatus());
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
