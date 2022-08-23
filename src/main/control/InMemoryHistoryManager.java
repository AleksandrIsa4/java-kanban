package main.control;

import main.target.Task;
import java.util.LinkedList;
import java.util.List;

public final class InMemoryHistoryManager implements HistoryManager {

    private LinkedList<Task> history = new LinkedList<>();

    public void add(Task task) {
        history.add(task);
        if (history.size() < 11) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

    public List<Task> getHistory() {
        return history;
    }


}
