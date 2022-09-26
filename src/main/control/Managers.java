package main.control;

public class Managers {
    /*
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
*/
    public static TaskManager getDefault() {
        return new FileBackedTasksManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
