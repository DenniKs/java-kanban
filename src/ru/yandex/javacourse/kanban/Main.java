package ru.yandex.javacourse.kanban;

import static ru.yandex.javacourse.kanban.tasks.Status.IN_PROGRESS;

import ru.yandex.javacourse.kanban.manager.Managers;
import ru.yandex.javacourse.kanban.manager.TaskManager;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("_____________________________");

        System.out.println("Проводим проверку методов.");
        TaskManager taskManager = Managers.getDefault();

        System.out.println("_____________________________");
        System.out.println();

        System.out.print("1.1 ");
        System.out.println("addTask(task) запуск.");
        System.out.println();
        Task task = new Task();
        System.out.println("Создаем 2 задачи.");
        System.out.println();
        Task addTask = taskManager.addTask(task);
        Task addTask1 = taskManager.addTask(task);
        Task addTask2 = taskManager.addTask(task);
        Task addTask3 = taskManager.addTask(task);
        System.out.println("Печатаем две задачи:");
        System.out.println(addTask);
        System.out.println(addTask1);
        System.out.println(addTask2);
        System.out.println(addTask3);
        System.out.println();
        if (!task.equals(addTask) && !addTask.equals(addTask1))
            System.out.println("addTask работает.");
        else
            System.out.println("addTask(task) не работает.");

        System.out.println("_____________________________");
        System.out.println();

        System.out.print("1.2 ");
        System.out.println("findAllTask() запуск.");
        System.out.println();
        List<Task> taskArrayList = taskManager.findAllTasks();
        System.out.println("Печатаем список задач:");
        for (Task value : taskArrayList) {
            System.out.println(value);
        }
        System.out.println();
        if (taskArrayList.isEmpty()) {
            System.out.println("findAllTask() не работает.");
        } else {
            System.out.println("findAllTask() работает.");
        }

        System.out.println("_____________________________");
        System.out.println();

        System.out.print("1.3 ");
        System.out.println("updateTaskById() запуск.");
        System.out.println();
        Task taskUpdate = new Task("Проверка", "Проверка обновления", 2, IN_PROGRESS);
        taskManager.updateTaskByID(taskUpdate);
        System.out.println("Печатам новую и обновленную задачу:");
        System.out.println();
        System.out.println(taskUpdate);
        System.out.println(taskManager.findTaskById(taskUpdate.getId()));
        System.out.println();
        System.out.println("Печатам список после обновления:");
        System.out.println();
        for (Task value : taskManager.findAllTasks()) {
            System.out.println(value);
        }
        System.out.println();
        if (taskManager.findTaskById(taskUpdate.getId()).equals(taskUpdate))
            System.out.println("Метод updateTaskById() работает");
        else
            System.out.println("Метод updateTaskById() не работает");

        System.out.println();
        System.out.println("Печатаем историю задач:");

        System.out.println("_____________________________");
        System.out.println();

        System.out.print("1.4 ");
        System.out.println("deleteTaskById() запуск.");
        System.out.println();
        System.out.println("Печатам удаляемую задачу:");
        System.out.println();
        System.out.println(taskManager.findTaskById(2));
        taskManager.deleteTaskById(2);
        System.out.println();
        System.out.println("Печатам список после удаления:");
        System.out.println();
        for (Task value : taskManager.findAllTasks()) {
            System.out.println(value);
        }
        System.out.println();
        if (taskManager.findTaskById(2) == null) {
            System.out.println("Задача удалена. deleteTaskById() работает.");
        }
        else {
            System.out.println("deleteTaskById() не работает.");
        }

        System.out.println("_____________________________");
        System.out.println();

        System.out.print("1.5 ");
        System.out.println("getHistory() запуск.");
        taskManager.findTaskById(3);
        taskManager.findTaskById(1);
        taskManager.findTaskById(3);
        System.out.println();
        System.out.println("Печатаем историю задач:");

        List<Task> tasks = taskManager.getHistory();
        for (var t : tasks) {
            System.out.println(t);
        }
    }
}
