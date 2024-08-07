package ru.yandex.javacourse.kanban.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Task;
import ru.yandex.javacourse.kanban.manager.FileBackedTaskManager;
import ru.yandex.javacourse.kanban.manager.Managers;
import ru.yandex.javacourse.kanban.manager.TaskManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    public static final int PORT = 8080;
    private static final String PATH = "/tasks";
    private static final String URL = "http://localhost:";
    private final HttpServer httpServer;
    private final Gson gson;
    private final TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        taskManager = FileBackedTaskManager.loadFromFile(new File("src/resources/data.csv"));
        gson = Managers.getGson();
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext(PATH, this::generalHandler);
        httpServer.createContext(PATH + "/task", this::taskHandler);
        httpServer.createContext(PATH + "/subtask", this::subtaskHandler);
        httpServer.createContext(PATH + "/epic", this::epicHandler);
        httpServer.createContext(PATH + "/history", this::historyHandler);
        httpServer.createContext(PATH + "/subtask/epic", this::subtasksEpicHandler);
    }

    private void subtasksEpicHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response = "";
        String requestMethod = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + path + " с методом " + requestMethod);

        if ("GET".equals(requestMethod)) {
            String query = httpExchange.getRequestURI().getQuery();
            try {
                int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                Epic epicById = taskManager.findEpicById(id);
                response = gson.toJson(taskManager.findAllSubTasksOfEpic(epicById));
            } catch (StringIndexOutOfBoundsException e) {
                response = "В запросе отсутствует id";
                statusCode = 400;
            } catch (NumberFormatException e) {
                response = "Неверный формат id";
                statusCode = 400;
            }
        }
        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(response.getBytes());
        }
    }

    private void historyHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response;
        String requestMethod = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + path + " с методом " + requestMethod);

        if ("GET".equals(requestMethod)) {
            response = gson.toJson(taskManager.getHistory());
        } else {
            response = "Некорректный запрос";
            statusCode = 400;
        }
        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(response.getBytes());
        }
    }

    private void epicHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response = "";

        String requestMethod = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + path + " с методом " + requestMethod);

        switch (requestMethod) {
            case "GET" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    response = gson.toJson(taskManager.findAllEpics());
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        Epic epicById = taskManager.findEpicById(id);
                        if (epicById != null) {
                            response = gson.toJson(epicById);
                        } else {
                            response = "Эпик с данным id не найден";
                            statusCode = 404;
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            case "POST" -> {
                try {
                    String body = readText(httpExchange);
                    Epic epic = gson.fromJson(body, Epic.class);
                    Integer id = epic.getId();
                    taskManager.addEpic(epic);
                    statusCode = 201;
                    response = "Эпик с id=" + id + " обновлен/создан";
                } catch (JsonSyntaxException e) {
                    response = "Неверный формат запроса";
                    statusCode = 400;
                }
            }
            case "DELETE" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.deleteAllEpics();
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        taskManager.deleteEpicById(id);
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            default -> {
                response = "Некорректный запрос";
                statusCode = 400;
            }
        }
        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(response.getBytes());
        }
    }

    private void subtaskHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response = "";
        String requestMethod = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + path + " с методом " + requestMethod);

        switch (requestMethod) {
            case "GET" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    response = gson.toJson(taskManager.findAllSubTasks());
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        SubTask subTaskById = taskManager.findSubTaskById(id);
                        if (subTaskById != null) {
                            response = gson.toJson(subTaskById);
                        } else {
                            statusCode = 400;
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            case "POST" -> {
                try {
                    String body = readText(httpExchange);
                    SubTask subTask = gson.fromJson(body, SubTask.class);
                    Integer id = subTask.getId();
                    taskManager.addSubTask(subTask);
                    statusCode = 201;
                    response = "Подзадача с id=" + id + " обновлена";
                } catch (JsonSyntaxException e) {
                    response = "Неверный формат запроса";
                    statusCode = 400;
                }
            }
            case "DELETE" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.deleteAllSubTasks();
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        taskManager.deleteSubTaskById(id);
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            default -> {
                response = "Некорректный запрос";
                statusCode = 404;
            }
        }
        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(response.getBytes());
        }
    }

    private void taskHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response = "";
        String requestMethod = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + path + " с методом " + requestMethod);

        switch (requestMethod) {
            case "GET" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    response = gson.toJson(taskManager.findAllTasks());
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        Task taskById = taskManager.findTaskById(id);
                        if (taskById != null) {
                            response = gson.toJson(taskById);
                        } else {
                            statusCode = 400;
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            case "POST" -> {
                try (InputStream inputStream = httpExchange.getRequestBody()) {
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Task task = gson.fromJson(body, Task.class);
                    Integer id = task.getId();
                    taskManager.addTask(task);
                    statusCode = 201;
                    response = "Задача с id=" + id + " обновлена/создана";
                } catch (JsonSyntaxException e) {
                    response = "Неверный формат запроса";
                    statusCode = 400;
                }
            }
            case "DELETE" -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null) {
                    taskManager.deleteAllTask();
                } else {
                    try {
                        int id = Integer.parseInt(query.substring(query.indexOf("?id=") + 4));
                        taskManager.deleteTaskById(id);
                        response = "Задача с id=" + id + " удалена";
                    } catch (StringIndexOutOfBoundsException e) {
                        response = "В запросе отсутствует необходимый параметр id";
                        statusCode = 400;
                    } catch (NumberFormatException e) {
                        response = "Неверный формат id";
                        statusCode = 400;
                    }
                }
            }
            default -> {
                response = "Некорректный запрос";
                statusCode = 400;
            }
        }

        httpExchange.sendResponseHeaders(statusCode, 0);

        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(response.getBytes());
        }

    }

    private void generalHandler(HttpExchange httpExchange) throws IOException {
        int statusCode = 200;
        String response;
        String requestMethod = httpExchange.getRequestMethod();
        String uri = httpExchange.getRequestURI().toString();

        System.out.println("Обрабатывается запрос " + uri + " с методом " + requestMethod);

        if ("GET".equals(requestMethod)) {
            response = gson.toJson(taskManager.getPrioritizedTasks());
        } else {
            response = "Некорректный запрос";
            statusCode = 400;
        }
        httpExchange.sendResponseHeaders(statusCode, 0);
        try {
            try (OutputStream responseBody = httpExchange.getResponseBody()) {
                responseBody.write(response.getBytes());
            }
        } finally {
            httpExchange.close();
        }
    }

    public void start() {
        System.out.println("Стартуем сервер " + PORT);
        System.out.println(URL + PORT + PATH);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    private String readText(HttpExchange httpExchange) throws IOException {
        return new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }
}
