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
    private SubTaskController subTaskController;
    private EpicController epicController;
    private Epic epic1;
    private SubTask subTask1;
    private SubTask subTask2;

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

//    @Test
//    // Проверяет поиск подзадачи по идентификатору.
//    void testFindById() {
//        subTaskController.add(subTask1, epic1);
//        SubTask foundSubTask = subTaskController.findById(subTask1.getId());
//        assertEquals(subTask1, foundSubTask);
//    }
//
//    @Test
//    // Проверяет обновление подзадачи.
//    void testUpdate() {
//        subTaskController.add(subTask1, epic1);
//        SubTask updatedSubTask = new SubTask("Updated SubTask 1", "Updated Description for SubTask 1", subTask1.getId(), epic1.getId());
//        updatedSubTask.setStatus(Status.IN_PROGRESS);
//        subTaskController.update(updatedSubTask);
//        SubTask foundSubTask = subTaskController.findById(updatedSubTask.getId());
//        assertEquals(updatedSubTask.getName(), foundSubTask.getName());
//        assertEquals(updatedSubTask.getDescription(), foundSubTask.getDescription());
//        assertEquals(updatedSubTask.getStatus(), foundSubTask.getStatus());
//    }
//
//    @Test
//    // Проверяет удаление подзадачи по идентификатору.
//    void testDeleteById() {
//        subTaskController.add(subTask1, epic1);
//        subTaskController.deleteById(subTask1.getId());
//        SubTask foundSubTask = subTaskController.findById(subTask1.getId());
//        assertNull(foundSubTask);
//    }
//
//    @Test
//    // Проверяет удаление всех подзадач.
//    void testDeleteAll() {
//        subTaskController.add(subTask1, epic1);
//        subTaskController.add(subTask2, epic1);
//        subTaskController.deleteAll();
//        List<SubTask> subTasks = subTaskController.findAllOfEpic(epic1);
//        assertTrue(subTasks.isEmpty());
//    }
//
//    @Test
//    // Проверяет поиск всех подзадач эпика.
//    void testFindAllOfEpic() {
//        subTaskController.add(subTask1, epic1);
//        subTaskController.add(subTask2, epic1);
//        List<SubTask> subTasks = subTaskController.findAllOfEpic(epic1);
//        assertEquals(2, subTasks.size());
//        assertTrue(subTasks.contains(subTask1));
//        assertTrue(subTasks.contains(subTask2));
//    }
//
//    @Test
//    // Проверяет обновление статуса эпика на основе статусов подзадач.
//    void testRefreshStatus() {
//        subTask1.setStatus(Status.NEW);
//        subTask2.setStatus(Status.DONE);
//        subTaskController.add(subTask1, epic1);
//        subTaskController.add(subTask2, epic1);
//        subTaskController.refreshStatus(subTask1);
//        assertEquals(Status.IN_PROGRESS, epic1.getStatus());
//    }

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