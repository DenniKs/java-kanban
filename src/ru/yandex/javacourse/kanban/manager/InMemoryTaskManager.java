package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private TaskController taskController = new TaskController();
    private EpicController epicController = new EpicController();
    private SubTaskController subTaskController = new SubTaskController(epicController);
    private HistoryManager historyManager = Managers.getDefaultHistory();

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
    public Task addTask(Task task) {
        return taskController.add(task);
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
        if(!historyManager.getHistory().isEmpty()){
            for (var historyTask : historyManager.getHistory()) {
                for (var task: taskController.getTasks().values()) {
                    if (task.equals(historyTask)) {
                        historyManager.remove(historyTask.getId());
                    }
                }
            }
        }
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
        historyManager.remove(id);
        subTaskController.deleteById(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        historyManager.remove(id);
        subTaskController.findSubTasksByEpicID(epicController.findById(id));
        epicController.deleteById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        historyManager.remove(id);
        return taskController.deleteById(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
