package test.ru.yandex.javacourse.kanban.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.manager.EpicController;
import ru.yandex.javacourse.kanban.tasks.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestEpicController {
    private EpicController epicController;
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        epicController = new EpicController();
        epic1 = new Epic("Epic 1", "Description for Epic 1", 1);
        epic2 = new Epic("Epic 2", "Description for Epic 2", 2);
    }

    @Test
    // Проверяет добавление эпика.
    void testAdd() {
        Epic addedEpic = epicController.add(epic1);
        assertNotNull(addedEpic);
        assertEquals(epic1.getName(), addedEpic.getName());
        assertEquals(epic1.getDescription(), addedEpic.getDescription());
        assertNotEquals(0, addedEpic.getId());
    }

    @Test
    // Проверяет поиск эпика по идентификатору.
    void testFindById() {
        epicController.add(epic1);
        Epic foundEpic = epicController.findById(epic1.getId());
        assertEquals(epic1, foundEpic);
    }

    @Test
    // Проверяет получение всех эпиков.
    void testFindAll() {
        epicController.add(epic1);
        epicController.add(epic2);
        List<Epic> epics = epicController.findAll();
        assertEquals(2, epics.size());
        assertTrue(epics.contains(epic1));
        assertTrue(epics.contains(epic2));
    }

    @Test
    // Проверяет обновление эпика.
    void testUpdate() {
        epicController.add(epic1);
        Epic updatedEpic = new Epic("Updated Epic 1", "Updated Description for Epic 1", epic1.getId());
        epicController.update(updatedEpic);
        Epic foundEpic = epicController.findById(updatedEpic.getId());
        assertEquals(updatedEpic.getName(), foundEpic.getName());
        assertEquals(updatedEpic.getDescription(), foundEpic.getDescription());
    }

    @Test
    // Проверяет удаление эпика по идентификатору.
    void testDeleteById() {
        epicController.add(epic1);
        epicController.deleteById(epic1.getId());
        Epic foundEpic = epicController.findById(epic1.getId());
        assertNull(foundEpic);
    }

    @Test
    // Проверяет удаление всех эпиков.
    void testDeleteAll() {
        epicController.add(epic1);
        epicController.add(epic2);
        epicController.deleteAll();
        List<Epic> epics = epicController.findAll();
        assertTrue(epics.isEmpty());
    }
}
