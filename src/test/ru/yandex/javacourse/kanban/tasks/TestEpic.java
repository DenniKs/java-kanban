package test.ru.yandex.javacourse.kanban.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.SubTask;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestEpic {
    private Epic epic;
    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    void setUp() {
        epic = new Epic("Epic 1", "Description for Epic 1", 1);
        subTask1 = new SubTask("SubTask 1", "Description for SubTask 1", 2, 1);
        subTask2 = new SubTask("SubTask 2", "Description for SubTask 2", 3, 1);
    }

    @Test
    // Проверяет добавление подзадач в эпик.
    void testAddSubTask() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        assertEquals(2, epic.getSubTasks().size());
        assertTrue(epic.getSubTasks().contains(subTask1));
        assertTrue(epic.getSubTasks().contains(subTask2));
    }

    @Test
    // Проверяет удаление подзадачи из эпика.
    void testRemoveSubTask() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.removeSubTask(subTask1);

        assertEquals(1, epic.getSubTasks().size());
        assertFalse(epic.getSubTasks().contains(subTask1));
        assertTrue(epic.getSubTasks().contains(subTask2));
    }

    @Test
    // Проверяет удаление всех подзадач из эпика.
    void testClearSubTasks() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.clearSubTasks();

        assertEquals(0, epic.getSubTasks().size());
    }

    @Test
    // Проверяет получение всех подзадач эпика.
    void testGetSubTasks() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        ArrayList<SubTask> subTasks = epic.getSubTasks();
        assertEquals(2, subTasks.size());
        assertTrue(subTasks.contains(subTask1));
        assertTrue(subTasks.contains(subTask2));
    }

    @Test
    // Проверяет установку нового списка подзадач в эпик.
    void testSetSubTasks() {
        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        newSubTasks.add(subTask1);
        newSubTasks.add(subTask2);

        epic.setSubTasks(newSubTasks);

        assertEquals(2, epic.getSubTasks().size());
        assertTrue(epic.getSubTasks().contains(subTask1));
        assertTrue(epic.getSubTasks().contains(subTask2));
    }
}
