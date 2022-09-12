package main.control;

import main.target.Task;

public class Node<T> {

    public T task;
    public Node<T> next;
    public Node<T> prev;


    public Node(Node<T> prev, T task, Node<T> next) {
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
