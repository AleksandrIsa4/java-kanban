package main.control;

import main.target.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> history = new ArrayList<>();

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
