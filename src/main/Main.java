package main;

import main.control.Managers;
import main.control.TaskManager;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        TaskManager fileBackedTasksManager = Managers.loadFromFile(new File("./fileTask.csv"));
    }
}
