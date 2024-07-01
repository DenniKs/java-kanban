package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.*;
import java.time.LocalDateTime;

public class InMemoryTaskManager implements TaskManager {

    public TaskController taskController = new TaskController();
    public EpicController epicController = new EpicController();
    public SubTaskController subTaskController = new SubTaskController(epicController);
    public HistoryManager historyManager = Managers.getDefaultHistory();

    private Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));

    @Override
    public ArrayList<Task> findAllTasks() {
        return taskController.findAll();
    }

    @Override
    public ArrayList<Epic> findAllEpics() {
        return epicController.findAll();
    }

    @Override
    public ArrayList<SubTask> findAllSubTasksOfEpic(Epic epic) {
        return subTaskController.findAllOfEpic(epic);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTaskController.findById(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Task findTaskById(Integer id) {
        final Task task = taskController.findById(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epicController.findById(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask subTask, Epic epic) {
        return subTaskController.add(subTask, epic);
    }

    @Override
    public Epic addEpic(Epic epic) {
        return epicController.add(epic);
    }

    @Override
    public Task updateTaskByID(Task task) {
        return taskController.update(task);
    }

    @Override
    public SubTask updateSubTaskByID(SubTask subTask) {
        return subTaskController.update(subTask);
    }

    @Override
    public Task updateEpicByID(Epic epic) {
        return epicController.update(epic);
    }

    @Override
    public void deleteAllTask() {
        taskController.deleteAll();
    }

    @Override
    public void deleteAllSubTasks() {
        subTaskController.deleteAll();
        subTaskController.deleteSubTaskOfEpic();
        subTaskController.refreshStatusEpicWhenDeleteAllSubTask();
    }

    @Override
    public void deleteAllEpics() {
        epicController.deleteAll();
        subTaskController.deleteAll();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        subTaskController.deleteById(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        subTaskController.findSubTasksByEpicID(epicController.findById(id));
        epicController.deleteById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        return taskController.deleteById(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public boolean isTimeSlotAvailable(Task newTask) {
        LocalDateTime newStart = newTask.getStartTime();
        LocalDateTime newEnd = newTask.getEndTime();

        if (newStart == null || newEnd == null) return true;

        return prioritizedTasks.stream()
                .map(task -> new LocalDateTime[] { task.getStartTime(), task.getEndTime() })
                .noneMatch(taskTimes -> taskTimes[0] != null && taskTimes[1] != null &&
                        newStart.isBefore(taskTimes[1]) && newEnd.isAfter(taskTimes[0]));
    }

    @Override
    public Task addTask(Task task) throws IllegalArgumentException {
        if (isTimeSlotAvailable(task)) {
            prioritizedTasks.add(task);
            return taskController.add(task);
        }
        throw new IllegalArgumentException("Временной интервал запроса совпадает с существующей задачей");
    }
}
