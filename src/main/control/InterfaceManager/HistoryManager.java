package main.control.InterfaceManager;

import main.target.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void remove(int index);
}
