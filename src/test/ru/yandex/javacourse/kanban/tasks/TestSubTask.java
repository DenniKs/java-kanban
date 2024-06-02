package test.ru.yandex.javacourse.kanban.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.SubTask;

import static org.junit.jupiter.api.Assertions.*;

public class TestSubTask {
    private SubTask subTask;

    @BeforeEach
    void setUp() {
        subTask = new SubTask("SubTask 1", "Description for SubTask 1", 2, 1);
    }

    @Test
    void testGetEpicID() {
        assertEquals(1, subTask.getEpicID());
    }

    @Test
    void testSetEpicID() {
        subTask.setEpicID(2);
        assertEquals(2, subTask.getEpicID());
    }

    @Test
    void testGetName() {
        assertEquals("SubTask 1", subTask.getName());
    }

    @Test
    void testSetName() {
        subTask.setName("New SubTask Name");
        assertEquals("New SubTask Name", subTask.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Description for SubTask 1", subTask.getDescription());
    }

    @Test
    void testSetDescription() {
        subTask.setDescription("New Description");
        assertEquals("New Description", subTask.getDescription());
    }

    @Test
    void testGetId() {
        assertEquals(2, subTask.getId());
    }

    @Test
    void testGetStatus() {
        assertEquals(Status.NEW, subTask.getStatus());
    }

    @Test
    void testSetStatus() {
        subTask.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    void testEquals() {
        SubTask subTaskCopy = new SubTask("SubTask 1", "Description for SubTask 1", 2, 1);
        assertEquals(subTask, subTaskCopy);

        SubTask differentSubTask = new SubTask("SubTask 2", "Description for SubTask 2", 3, 1);
        assertNotEquals(subTask, differentSubTask);
    }

    @Test
    void testHashCode() {
        SubTask subTaskCopy = new SubTask("SubTask 1", "Description for SubTask 1", 2, 1);
        assertEquals(subTask.hashCode(), subTaskCopy.hashCode());

        SubTask differentSubTask = new SubTask("SubTask 2", "Description for SubTask 2", 3, 1);
        assertNotEquals(subTask.hashCode(), differentSubTask.hashCode());
    }

    @Test
    void testToString() {
        String expected = "SubTask{epicID=1, name='SubTask 1', description='Description for SubTask 1', id=2, status='NEW'}";
        assertEquals(expected, subTask.toString());
    }
}
