package ru.yandex.javacourse.kanban.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public Epic(String type, String name, String description, Integer id, Status status) {
        super(type, name, description, id, status);
    }

    public Epic(String name, String description) {
        super(name, description, Status.NEW, Duration.ZERO, null);
        updateDurationAndTime();
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
        updateDurationAndTime();
    }

    // Метод для удаления подзадачи
    public void removeSubTask(SubTask subTask) {
        this.subTasks.remove(subTask);
        updateDurationAndTime();
    }

    // Метод для полной очистки списка
    public void clearSubTasks() {
        this.subTasks.clear();
        updateDurationAndTime();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    private void updateDurationAndTime() {
        Duration totalDuration = Duration.ZERO;
        LocalDateTime start = null;
        LocalDateTime end = null;

        for (SubTask subTask : subTasks) {
            totalDuration = totalDuration.plus(subTask.getDuration());

            if (start == null || (subTask.getStartTime() != null && subTask.getStartTime().isBefore(start))) {
                start = subTask.getStartTime();
            }
            LocalDateTime subTaskEndTime = subTask.getEndTime();
            if (end == null || (subTaskEndTime != null && subTaskEndTime.isAfter(end))) {
                end = subTaskEndTime;
            }
        }

        this.setDuration(totalDuration);
        this.setStartTime(start);
        this.endTime = end;
    }
}
