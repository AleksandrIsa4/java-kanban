package main.control;

import main.target.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> history = new CustomLinkedList<>();
    private Map<Integer, CustomLinkedList.Node> historyHash = new HashMap<>();
    private List<Task> historyList = new ArrayList<>();

    public void add(Task task) {
        if (historyHash.containsKey(task.getIndex())) {
            history.removeNode(historyHash.get(task.getIndex()));
            history.linkLast(task);
            historyHash.put(task.getIndex(), history.getLast());
            return;
        }
        history.linkLast(task);
        historyHash.put(task.getIndex(), history.getLast());
    }

    public List<Task> getHistory() {
        return history.getTasks();
    }

    public void remove(int index) {
        if (historyHash.containsKey(index)) {
            history.removeNode(historyHash.get(index));
            historyHash.remove(index);
        }
    }

    private class CustomLinkedList<T> {

        class Node<E> {
            public E task;
            public Node<E> next;
            public Node<E> prev;

            public Node(Node<E> prev, E task, Node<E> next) {
                this.task = task;
                this.next = next;
                this.prev = prev;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "task=" + task +
                        ", next=" + (next != null ? next.task : null) +
                        ", prev=" + (prev != null ? prev.task : null) +
                        '}';
            }
        }

        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        private void linkLast(T task) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null || oldTail.task == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            size++;
        }

        private Node getLast() {
            return tail;
        }

        private void removeNode(Node node) {
            Node ptr = head;
            if (ptr == node) {
                if (size == 1) {
                    node.task = null;
                    size--;
                    return;
                }
                head = node.next;
                head.prev = null;
                node.task = null;
                size--;
                return;
            }
            if (tail == node) {
                tail = node.prev;
                tail.next = null;
                node.task = null;
                size--;
                return;
            }
            while (ptr != null) {
                if (ptr.task == node.task) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.task = null;
                    size--;
                    break;
                }
                ptr = ptr.next;
            }
        }

        private List<Task> getTasks() {
            historyList.clear();
            Node ptr = head;
            try {
                if (ptr.task == null) {
                    return historyList;
                }
            } catch (NullPointerException e) {
                return historyList;
            }
            while (ptr != null) {
                historyList.add((Task) ptr.task);
                ptr = ptr.next;
            }
            return historyList;
        }
    }
}
