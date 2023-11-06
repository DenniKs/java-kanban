package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    static class historyRecord {
        protected Task task;
        private historyRecord prev;
        private historyRecord next;

        public historyRecord(Task task) {
            this.task = task;
        }
    }

    private historyRecord head;
    private historyRecord last;

    private HashMap<Integer, historyRecord> listHistory = new HashMap<>();

    public HashMap<Integer, historyRecord> getListHistory() {
        return listHistory;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        addLast(task);
    }

    private void addLast(Task task) {
        remove(task.getId());

        final historyRecord newHistoryRecord = new historyRecord(task);
        if (head == null) {
            head = newHistoryRecord;
        } else {
            last.next = newHistoryRecord;
            newHistoryRecord.prev = last;
        }
        last = newHistoryRecord;
        listHistory.put(task.getId(), newHistoryRecord);
    }

    @Override
    public void remove(int id) {
        final historyRecord oldHistoryRecord = listHistory.remove(id);
        if (oldHistoryRecord != null) {
            if (oldHistoryRecord == head) {
                head = oldHistoryRecord.next;
                last = oldHistoryRecord.next;
            } else if (oldHistoryRecord == last) {
                last = oldHistoryRecord.prev;
                last.next = null;
            } else {
                oldHistoryRecord.prev.next = oldHistoryRecord.next;
            }
        }
    }

    @Override
    public void removeAll() {
        historyRecord head = null;
        historyRecord tail = null;
        HashMap<Integer, historyRecord> map = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        final ArrayList<Task> tasks = new ArrayList<>();
        historyRecord current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }
}
