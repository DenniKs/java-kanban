package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Epic;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicController {
    HashMap<Integer, Epic> epics = new HashMap<>();
    Integer counterIDEpics = 0;

    public Epic findById(Integer id) {
        return epics.get(id);
    }

    public ArrayList<Epic> findAll() {
        return new ArrayList<>(epics.values());
    }

    public Epic update(Epic epic) {
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }

    public Epic add(Epic task) {
        final Epic newTask = new Epic(task.getName(), task.getDescription(), ++counterIDEpics);
        if (!epics.containsKey(newTask.getId())) {
            epics.put(newTask.getId(), newTask);
        } else {
            System.out.println("Задача с таким ID уже существует.");
            return null;
        }
        return newTask;
    }

    public void deleteById(Integer id) {
        epics.remove(id);
    }

    public void deleteAll() {
        epics.clear();
    }
}
