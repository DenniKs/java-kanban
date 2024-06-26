package ru.yandex.javacourse.kanban.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public Epic(String type, String name, String description, Integer id, Status status) {
        super(type, name, description, id, status);
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = new ArrayList<>(subTasks);
    }

    // Метод для добавления подзадачи
    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
    }

    // Метод для удаления подзадачи
    public boolean removeSubTask(SubTask subTask) {
        return this.subTasks.remove(subTask);
    }

    // Метод для полной очистки списка
    public void clearSubTasks() {
        this.subTasks.clear();
    }
}
