package test.ru.yandex.javacourse.kanban.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.manager.EpicController;
import ru.yandex.javacourse.kanban.manager.SubTaskController;
import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.SubTask;

import static org.junit.jupiter.api.Assertions.*;

public class TestSubTaskController {
    SubTaskController subTaskController;
    EpicController epicController;
    Epic epic1;
    SubTask subTask1;
    SubTask subTask2;

    @BeforeEach
    void setUp() {
        epicController = new EpicController();
        subTaskController = new SubTaskController(epicController);
        epic1 = new Epic("Epic 1", "Description for Epic 1", 1);
        epicController.add(epic1);
        subTask1 = new SubTask("SubTask 1", "Description for SubTask 1", 2, epic1.getId());
        subTask2 = new SubTask("SubTask 2", "Description for SubTask 2", 3, epic1.getId());
    }

    @Test
    // Проверяет добавление подзадачи.
    void testAdd() {
        SubTask addedSubTask = subTaskController.add(subTask1, epic1);
        assertNotNull(addedSubTask);
        assertEquals(subTask1.getName(), addedSubTask.getName());
        assertEquals(subTask1.getDescription(), addedSubTask.getDescription());
        assertEquals(subTask1.getEpicID(), addedSubTask.getEpicID());
    }

    @Test
    // Проверяет обновление статуса эпика при удалении всех подзадач.
    void testRefreshStatusEpicWhenDeleteAllSubTask() {
        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.DONE);
        subTaskController.add(subTask1, epic1);
        subTaskController.add(subTask2, epic1);
        subTaskController.deleteAll();
        subTaskController.refreshStatusEpicWhenDeleteAllSubTask();
        assertEquals(Status.NEW, epic1.getStatus());
    }
}