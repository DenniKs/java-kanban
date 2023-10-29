package ru.yandex.javacourse.kanban.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
}
