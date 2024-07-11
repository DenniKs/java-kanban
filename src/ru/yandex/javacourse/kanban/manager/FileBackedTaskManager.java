package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public File tasksStorage;
    private static final String FILE_HEADER = "id,type,name,status,description,epic";

    public FileBackedTaskManager(File tasksStorage) {
        this.tasksStorage = tasksStorage;
    }

    public FileBackedTaskManager() {

    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask subTask, Epic epic) {
        super.addSubTask(subTask, epic);
        save();
        return subTask;
    }

    @Override
    public Task findTaskById(Integer id) {
        super.findTaskById(id);
        save();
        return taskController.findById(id);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        super.findSubTaskById(id);
        save();
        return subTaskController.findById(id);
    }

    @Override
    public Epic findEpicById(Integer id) {
        super.findEpicById(id);
        save();
        return epicController.findById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        final Task deletedTask = super.deleteTaskById(id);
        save();
        return deletedTask;
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> history = super.getHistory();
        save();
        return history;
    }


    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(tasksStorage.toPath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(FILE_HEADER + "\n");
            for (Task task : taskController.getTasks().values()) {
                writer.write(task.toStringFile());
                writer.newLine();
            }
            for (SubTask subTask : subTaskController.getSubTasks().values()) {
                writer.write(subTask.toStringFile());
                writer.newLine();
            }
            for (Epic epic : epicController.getEpics().values()) {
                writer.write(epic.toStringFile());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении в файл");
        }
    }

    public static FileBackedTaskManager loadFromFile(File storageTask) {
        FileBackedTaskManager fileBackedTasksManager = new FileBackedTaskManager(storageTask);
        try (BufferedReader reader = new BufferedReader(new FileReader(storageTask, StandardCharsets.UTF_8))) {
            boolean readHistory = false;
            while (reader.ready()) {
                String line = reader.readLine();
                if (readHistory) {
                    break;
                }
                if (line.isBlank()) {
                    readHistory = true;
                    continue;
                }
                String[] splitter = line.split(", ");
                TaskType type = TaskType.valueOf(splitter[0]);
                String name = splitter[1];
                String description = splitter[2];
                Integer id = Integer.parseInt(splitter[3]);
                Status status = Status.valueOf(splitter[4]);

                switch (type) {
                    case TASK:
                        fileBackedTasksManager.taskController.getTasks().put(id,
                                new Task("TASK", name, description, id, status));
                        break;
                    case SUBTASK:
                        Integer epicId = Integer.parseInt(splitter[5]);
                        fileBackedTasksManager.subTaskController.getSubTasks().put(id,
                                new SubTask("SUBTASK", name, description, id, status, epicId));
                        break;
                    case EPIC:
                        fileBackedTasksManager.epicController.getEpics().put(id,
                                new Epic("EPIC", name, description, id, status));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown type: " + type);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке из файла");
        }
        return fileBackedTasksManager;
    }
}