package ru.yandex.javacourse.kanban.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Task;
import ru.yandex.javacourse.kanban.http.KVTaskClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HTTPTaskManager extends FileBackedTaskManager {

    private final KVTaskClient kvTaskClient;
    private final static String KEY_TASKS = "tasks";
    private final static String KEY_SUBTASKS = "subtasks";
    private final static String KEY_EPICS = "epics";
    private final static String KEY_HISTORY = "history";
    private final static Gson gson = Managers.getGson();

    public HTTPTaskManager(String path) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(path);

        JsonElement jsonTasks = JsonParser.parseString(kvTaskClient.load(KEY_TASKS));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksAsJsonArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksAsJsonArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                taskController.add(task);
            }
        }

        JsonElement jsonSubTasks = JsonParser.parseString(kvTaskClient.load(KEY_SUBTASKS));
        if (!jsonSubTasks.isJsonNull()) {
            JsonArray jsonSubTasksAsJsonArray = jsonSubTasks.getAsJsonArray();
            for (JsonElement jsonSubTask : jsonSubTasksAsJsonArray) {
                SubTask subTask = gson.fromJson(jsonSubTask, SubTask.class);
                subTaskController.add(subTask);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(kvTaskClient.load(KEY_EPICS));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsAsJsonArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsAsJsonArray) {
                Epic epic = gson.fromJson(jsonEpic, Epic.class);
                epicController.add(epic);
            }
        }

        JsonElement jsonHistory = JsonParser.parseString(kvTaskClient.load(KEY_HISTORY));
        if (!jsonHistory.isJsonNull()) {
            JsonArray jsonHistoryAsJsonArray = jsonHistory.getAsJsonArray();
            for (JsonElement jsonId : jsonHistoryAsJsonArray) {
                int id = jsonId.getAsInt();
                List<Task> taskList = taskController.findAll();
                List<SubTask> subTaskList = subTaskController.findAll();
                List<Epic> epicList = epicController.findAll();
                if (!taskList.isEmpty()) {
                    this.findTaskById(id);
                } else if (!subTaskList.isEmpty()) {
                    this.findSubTaskById(id);
                } else if (!epicList.isEmpty()) {
                    this.findEpicById(id);
                }
            }
        }
    }

    @Override
    public void save() {
        kvTaskClient.put(KEY_TASKS, gson.toJson(taskController.findAll()));
        kvTaskClient.put(KEY_SUBTASKS, gson.toJson(subTaskController.findAll()));
        kvTaskClient.put(KEY_EPICS, gson.toJson(epicController.findAll()));
        kvTaskClient.put(KEY_HISTORY, gson.toJson(this.getHistory()
                .stream()
                .map(Task::getId)
                .collect(Collectors.toList())));
    }
}