package test.ru.yandex.javacourse.kanban.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Task 1", "Description for Task 1", 1);
    }

    @Test
    void testGetName() {
        assertEquals("Task 1", task.getName());
    }

    @Test
    void testSetName() {
        task.setName("New Task Name");
        assertEquals("New Task Name", task.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Description for Task 1", task.getDescription());
    }

    @Test
    void testSetDescription() {
        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription());
    }

    @Test
    void testGetId() {
        assertEquals(1, task.getId());
    }

    @Test
    void testGetStatus() {
        assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    void testSetStatus() {
        task.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void testEquals() {
        Task taskCopy = new Task("Task 1", "Description for Task 1", 1);
        assertEquals(task, taskCopy);

        Task differentTask = new Task("Task 2", "Description for Task 2", 2);
        assertNotEquals(task, differentTask);
    }

    @Test
    void testHashCode() {
        Task taskCopy = new Task("Task 1", "Description for Task 1", 1);
        assertEquals(task.hashCode(), taskCopy.hashCode());

        Task differentTask = new Task("Task 2", "Description for Task 2", 2);
        assertNotEquals(task.hashCode(), differentTask.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Task{name='Task 1', description='Description for Task 1', id=1, status='NEW'}";
        assertEquals(expected, task.toString());
    }
}
