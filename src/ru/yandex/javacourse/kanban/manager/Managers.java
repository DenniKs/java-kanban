package ru.yandex.javacourse.kanban.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.javacourse.kanban.adapter.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {

    public  static TaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public  static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static HTTPTaskManager getDefault() throws IOException, InterruptedException {
        return new HTTPTaskManager("http://localhost:8078/");
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
}
