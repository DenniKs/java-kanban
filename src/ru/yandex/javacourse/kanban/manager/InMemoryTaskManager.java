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
    private InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

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
        addInHistory(subTask);
        return subTask;
    }

    @Override
    public Task findTaskById(Integer id) {
        final Task task = taskController.findById(id);
        addInHistory(task);
        return task;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epicController.findById(id);
        addInHistory(epic);
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
        if(!inMemoryHistoryManager.getListHistory().isEmpty()){
            for (var historyTask : inMemoryHistoryManager.getListHistory().values()) {
                for (var task: taskController.getTasks().values()) {
                    if (task.equals(historyTask.task)) {
                        inMemoryHistoryManager.remove(historyTask.task.getId());
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
        removeFromHistoryById(id);
        subTaskController.deleteById(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        removeFromHistoryById(id);
        subTaskController.findSubTasksByEpicID(epicController.findById(id));
        epicController.deleteById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        removeFromHistoryById(id);
        return taskController.deleteById(id);
    }

    // Получение истории
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    // Удаление всей истории
    public void removeAllHistory() {
        inMemoryHistoryManager.removeAll();
    }

    // Добавление задачи в историю
    public void addInHistory(Task task) {
        inMemoryHistoryManager.add(task);
    }

    // Удаление задачи из истории по ИД
    public void removeFromHistoryById(int id) {
        inMemoryHistoryManager.remove(id);
    }
}
