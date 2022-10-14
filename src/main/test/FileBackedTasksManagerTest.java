package main.test;

import main.control.FileBackedTasksManager;
import main.control.Managers;
import main.target.Epic;
import main.target.Subtask;
import main.target.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File srcFile;

    @BeforeEach
    void setUp() {
        srcFile = new File("./fileTaskTest.csv");
        taskManager = Managers.loadFromFile(srcFile);
    }

    @Test
    void loadFromFileTest() {
        // пустой список задач
        taskManager = Managers.loadFromFile(new File("./fileTask2.csv"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(taskManager.getAllTask(), "Список всех задач \n"),
                () -> Assertions.assertEquals(taskManager.getAllSubtask(), "Список всех подзадач \n"),
                () -> Assertions.assertEquals(taskManager.getAllEpic(), "Список всех эпиков \n")
        );
        // эпик без подзадач
        taskManager = Managers.loadFromFile(new File("./fileTask3.csv"));
        List<Subtask> tasks = taskManager.allSubtaskEpic("Зарядить телефон");
        Epic epic = taskManager.getIndexEpic(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic.getName(), "Зарядить телефон"),
                () -> Assertions.assertEquals(0, tasks.size())
        );
        // Пустой список истории.
        taskManager = Managers.loadFromFile(new File("./fileTask4.csv"));
        List<Task> tasksHistory = taskManager.getHistory();
        Assertions.assertEquals(0, tasksHistory.size());
    }
}