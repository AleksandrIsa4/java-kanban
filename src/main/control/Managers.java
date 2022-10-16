package main.control;

import main.control.InterfaceManager.HistoryManager;
import main.control.InterfaceManager.TaskManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTasksManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.loadFile(file.getName());
        return fileBackedTasksManager;
    }
}