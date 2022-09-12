package main.control;

import main.target.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InMemoryHistoryManager implements HistoryManager {

    private CustomLinkedList<Task> history = new CustomLinkedList<>();
    private Map<Integer, Node> historyHash = new HashMap<>();
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
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        private void linkLast(T task) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
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
                head = node.next;
                head.prev = null;
                node.task = null;
                return;
            }
            if (tail == node) {
                tail = node.prev;
                tail.next = null;
                node.task = null;
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
            while (ptr != null) {
                historyList.add((Task) ptr.task);
                ptr = ptr.next;
            }
            return historyList;
        }
    }
}
