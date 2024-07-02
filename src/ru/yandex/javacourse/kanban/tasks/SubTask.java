package ru.yandex.javacourse.kanban.tasks;

import java.util.Objects;

public class SubTask extends Task {
    private Integer epicID;

    public SubTask(String type, String name, String description, Integer id, Status status, Integer epicId) {
        super(type, name, description, id, status);
        this.epicID = epicId;
    }

    public SubTask(String name, String description, Integer id, Integer epicID) {
        super(name, description, id);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epicID, subTask.epicID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID) + 21;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "type='" + super.getType() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", id=" + super.getId() +
                ", status='" + super.getStatus() + '\'' +
                ", epicID=" + epicID +
                '}';
    }

    public String toStringFile() {
        return          super.getId() +
                "," + super.getType() +
                "," + super.getName() +
                "," + super.getStatus() +
                "," + super.getDescription() +
                "," + epicID;
    }
}
