package main;

import main.control.Managers;
import main.control.InterfaceManager.TaskManager;
import main.target.Task;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        TaskManager fileBackedTasksManager = Managers.loadFromFile(new File("./fileTask.csv"));
       fileBackedTasksManager.creationTask(new Task("Переезд", "Собрать коробки", 55,4));
        System.out.println(fileBackedTasksManager.getPrioritizedTasks());

           System.out.println(fileBackedTasksManager.getAllTask());
      //  System.out.println(fileBackedTasksManager.getHistory());
    }
}
