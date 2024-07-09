package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Task;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Epic;

import java.util.List;

public interface TaskManager {
    List<Task> findAllTasks();

    List<Epic> findAllEpics();

    List<SubTask> findAllSubTasksOfEpic(Epic epic);

    List<SubTask> findAllSubTasks();

    SubTask findSubTaskById(Integer id);

    Task findTaskById(Integer id);

    Epic findEpicById(Integer id);

    Task addTask(Task task) throws IllegalArgumentException;

    SubTask addSubTask(SubTask subTask, Epic epic);

    SubTask addSubTask(SubTask subTask);

    Epic addEpic(Epic epic);

    Task updateTaskByID(Task task);

    SubTask updateSubTaskByID(SubTask subTask);

    Task updateEpicByID(Epic epic);

    void deleteAllTask();

    void deleteAllSubTasks();

    void deleteAllEpics();

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

    Task deleteTaskById(Integer id);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

    boolean isTimeSlotAvailable(Task task);

}
