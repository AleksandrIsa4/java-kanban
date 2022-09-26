package main;

import main.control.Managers;
import main.control.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        inMemoryTaskManager.loadFromFile("./fileTask.csv");
    }
}
