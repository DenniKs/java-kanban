package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

public class SubTaskController {
    private Integer counterIDSubTasks = 0;
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private EpicController epicController;

    public SubTaskController(EpicController epicController) {
        this.epicController = epicController;
    }

    public SubTask add(SubTask subTask, Epic epic) {
        final SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), ++counterIDSubTasks, epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epicController.epics.get(epic.getId()).addSubTask(/*newSubTask.getId(),*/ newSubTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newSubTask;
    }

    public SubTask findById(Integer id) {
        return subTasks.get(id);
    }

    public SubTask update(SubTask task) {
        final SubTask originalTask = subTasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        epicController.epics.get(task.getEpicID()).removeSubTask(originalTask);
        epicController.epics.get(task.getEpicID()).addSubTask(task);
        refreshStatus(task);
        return originalTask;
    }

    public SubTask deleteById(Integer id) {
        final SubTask deletedTask = subTasks.get(id);
        epicController.epics.get(deletedTask.getEpicID()).removeSubTask(deletedTask);
        subTasks.remove(id);
        refreshStatus(deletedTask);
        return deletedTask;
    }

    public void deleteAll() {
        subTasks.clear();
    }
    public void deleteSubTaskOfEpic() {
        for (Epic subTask : epicController.epics.values()) {
            subTask.clearSubTasks();
        }
    }

    public ArrayList<SubTask> findAllOfEpic(Epic epic) {
        return epicController.epics.get(epic.getId()).getSubTasks();
    }

    public void findSubTasksByEpicID(Epic epic) {
        subTasks.values().removeIf(subTask -> subTask.getEpicID().equals(epic.getId()));
    }

    public void refreshStatus(SubTask task) {
        ArrayList<SubTask> subTasksOfEpic = epicController.epics.get(task.getEpicID()).getSubTasks();
        int counterNew = 0;
        int counterDone = 0;
        for (SubTask subTask : subTasksOfEpic) {
            if (subTask.getStatus() == Status.NEW) {
                counterNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                counterDone++;
            }
        }
        if (counterNew == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.NEW);
        } else if (counterDone == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.DONE);
        } else {
            epicController.epics.get(task.getEpicID()).setStatus(Status.IN_PROGRESS);
        }
    }

    public void refreshStatusEpicWhenDeleteAllSubTask() {
        for (Epic epic : epicController.epics.values()) {
            epic.setStatus(Status.NEW);
        }
    }
}
