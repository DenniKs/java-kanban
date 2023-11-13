package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private static class HistoryRecord {
        private Task task;
        private HistoryRecord prev;
        private HistoryRecord next;

        private HistoryRecord(Task task) {
            this.task = task;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HistoryRecord that = (HistoryRecord) o;
            return task.equals(that.task);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task);
        }

    }

    private HistoryRecord head;
    private HistoryRecord last;

    private HashMap<Integer, HistoryRecord> historyRecordMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        } else {
            addLast(task);
            if (historyRecordMap.size() > 10) {
                remove(head.task.getId());
            }
        }
    }

    @Override
    public void remove(int id) {
        final HistoryRecord oldHistoryRecord = historyRecordMap.remove(id);
        if (oldHistoryRecord != null) {
            if (oldHistoryRecord == head && oldHistoryRecord == last) {
                head = null;
                last = null;
            } else if (oldHistoryRecord == head) {
                head = oldHistoryRecord.next;
                if (head != null) {
                    head.prev = null;
                }
            } else if (oldHistoryRecord == last) {
                last = oldHistoryRecord.prev;
                if (last != null) {
                    last.next = null;
                }
            } else {
                oldHistoryRecord.prev.next = oldHistoryRecord.next;
                if (oldHistoryRecord.next != null) {
                    oldHistoryRecord.next.prev = oldHistoryRecord.prev;
                }
            }
        }
    }

    @Override
    public void removeAll() {
        head = null;
        last = null;
        historyRecordMap.clear();
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> tasks = new ArrayList<>();
        HistoryRecord current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }

    private void addLast(Task task) {
        remove(task.getId());

        final HistoryRecord newHistoryRecord = new HistoryRecord(task);
        if (head == null) {
            head = newHistoryRecord;
        } else {
            last.next = newHistoryRecord;
            newHistoryRecord.prev = last;
        }
        last = newHistoryRecord;
        historyRecordMap.put(task.getId(), newHistoryRecord);
    }
}
