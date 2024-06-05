package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
        }

        // Геттеры и сеттеры для полей
        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, next, prev);
        }
    }

    private Node<Task> head;
    private Node<Task> tail;
    private final HashMap<Integer, Node<Task>> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        remove(task.getId()); // Удаляем существующую запись, если она есть
        linkLast(task);
    }

    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node<Task> node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    private void removeNode(Node<Task> node) {
        if (node.getPrev() == null) {
            head = node.getNext();
        } else {
            node.getPrev().setNext(node.getNext());
        }

        if (node.getNext() == null) {
            tail = node.getPrev();
        } else {
            node.getNext().setPrev(node.getPrev());
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            history.add(current.getData());
            current = current.getNext();
        }
        return history;
    }
}
