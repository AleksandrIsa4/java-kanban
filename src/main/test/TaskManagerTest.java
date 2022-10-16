package main.test;

import main.control.InterfaceManager.TaskManager;
import main.target.Epic;
import main.target.enumeration.Status;
import main.target.Subtask;
import main.target.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected void initTask() {
        taskManager.creationTask(new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45"));
        taskManager.creationTask(new Task("Переезд", "Собрать коробки", 2, 55));
    }

    protected void initEpic() {
        taskManager.creationEpic(new Epic("Приготовить чай", 3));
        taskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", 5, 3, 55, "22.09.22 00:55"));
        taskManager.creationSubtask(new Subtask("Выбрать чай2", "Добавить заварку2", 6, 3, 55, "22.12.05 16:52"));
        taskManager.creationEpic(new Epic("Зарядить телефон", 7));
    }

    @Test
    void getIndexTaskTest() {
        Task task = taskManager.getIndexTask(1);
        Task task2 = taskManager.getIndexTask(5);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45")),
                () -> Assertions.assertNull(task2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getIndexSubtaskTest() {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Subtask subtask2 = taskManager.getIndexSubtask(10);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12")),
                () -> Assertions.assertNull(subtask2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются."),
                () -> Assertions.assertNotNull(subtask.getIndexEpic(), "Индекс не эпика возвращается.")
        );
    }

    @Test
    void getIndexEpicTest() {
        Epic epic = taskManager.getIndexEpic(7);
        Epic epic2 = taskManager.getIndexEpic(10);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, new Epic("Зарядить телефон", 7)),
                () -> Assertions.assertNull(epic2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void getAllTaskTest() {
        String task = taskManager.getAllTask();
        String print = "Список всех задач \n" + new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45") + "\n" + new Task("Переезд", "Собрать коробки", 2, 55) + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getAllSubtaskTest() {
        String task = taskManager.getAllSubtask();
        String print = "Список всех подзадач \n" + new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12") + "\n" + new Subtask("Выбрать чай", "Добавить заварку", 5, 3, 55, "22.09.22 00:55") + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getAllEpicTest() {
        String task = taskManager.getAllEpic();
        String print = "Список всех эпиков \n" + "3,Приготовить чай,NEW,22.12.05 16:12" + "\n" + "7,Зарядить телефон,NEW" + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void deleteAllTaskTest() {
        taskManager.deleteAllTask();
        Assertions.assertEquals(taskManager.getAllTask(), "Список всех задач \n");
    }

    @Test
    void deleteAllSubtaskTest() {
        taskManager.deleteAllSubtask();
        Assertions.assertEquals(taskManager.getAllSubtask(), "Список всех подзадач \n");
    }

    @Test
    void deleteAllEpicTest() {
        taskManager.deleteAllEpic();
        Assertions.assertEquals(taskManager.getAllEpic(), "Список всех эпиков \n");
    }

    @Test
    void allSubtaskEpicTest() {
        List<Subtask> tasks = taskManager.allSubtaskEpic("Приготовить чай");
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, tasks.size()),
                () -> Assertions.assertTrue(tasks.contains(new Subtask("Выбрать чай", "Добавить заварку", 5, 3, 55, "22.09.22 00:55"))),
                () -> Assertions.assertNotNull(tasks, "Задача не возвращаются.")
        );
    }

    @Test
    void creationTaskTest() {
        Task task = taskManager.getIndexTask(1);
        taskManager.creationTask(new Task("Поездка", "Упаковать кошку", 33, 55, "22.10.22 10:55"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45")),
                () -> Assertions.assertNull(taskManager.getIndexTask(33), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Test
    void creationSubtaskTest() {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12")),
                () -> Assertions.assertNull(taskManager.getIndexSubtask(24), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Test
    void creationEpicTest() {
        Epic epic = taskManager.getIndexEpic(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, new Epic("Зарядить телефон", 7)),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void updateTaskTest() {
        Task task = taskManager.getIndexTask(1);
        taskManager.creationTask(new Task("Поездка", "Упаковать кошку", 3, 55, "22.10.22 10:55"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45")),
                () -> Assertions.assertNull(taskManager.getIndexTask(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Test
    void updateSubtaskTest() {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12")),
                () -> Assertions.assertNull(taskManager.getIndexTask(55), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Test
    void updateEpicTest() {
        Epic epic = taskManager.getIndexEpic(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, new Epic("Зарядить телефон", 7)),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void deleteTaskTest() {
        taskManager.deleteTask(1);
        String task = taskManager.getAllTask();
        String print = "Список всех задач \n" + new Task("Переезд", "Собрать коробки", 2, 55) + "\n";
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться.")
        );
    }

    @Test
    void deleteSubtaskTest() {
        taskManager.deleteSubtask(5);
        List<Subtask> tasks = taskManager.allSubtaskEpic("Приготовить чай");
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexSubtask(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(1, tasks.size()),
                () -> Assertions.assertTrue(tasks.contains(new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12"))),
                () -> Assertions.assertNotNull(tasks, "Задача не возвращаются.")
        );
        ;
    }

    @Test
    void deleteEpicTest() {
        taskManager.deleteEpic(3);
        List<Subtask> tasks = taskManager.allSubtaskEpic("Приготовить чай");
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexEpic(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertNull(tasks, "Задачи нe должна вернуться.")
        );
    }

    @Test
    void assignTaskTest() {
        taskManager.assignTask("Поездка", Status.DONE);
        Assertions.assertEquals(taskManager.getIndexTask(1).getStatus(), Status.DONE);
    }

    @Test
    void assignSubtaskTest() {
        taskManager.assignSubtask("Выбрать чай", Status.IN_PROGRESS);
        Assertions.assertEquals(taskManager.getIndexSubtask(5).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    void getHistoryEmptyTest() {
        taskManager.deleteAllEpic();
        taskManager.deleteAllTask();
        List<Task> taskHistory = taskManager.getHistory();
        System.out.println(taskManager.getHistory());
        Assertions.assertEquals(0, taskHistory.size());
    }

    @Test
    void getHistoryDuplicationTest() {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexTask(2);
        taskManager.getIndexTask(1);
        List<Task> taskHistory = taskManager.getHistory();
        boolean dublication = false;
        for (int i = 0; i < taskHistory.size(); i++) {
            for (int j = i + 1; j < taskHistory.size(); j++) {
                if (taskHistory.get(i) == taskHistory.get(j)) {
                    dublication = true;
                }
            }
        }
        Assertions.assertFalse(dublication, "Повторений не должно быть");
    }

    @Test
    void getHistoryDeleteTest() {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexTask(2);
        List<Task> taskHistory = taskManager.getHistory();
        Assertions.assertEquals(taskHistory.get(0), new Task("Поездка", "Упаковать кошку", 1, 55, "22.10.22 10:45"));
        Assertions.assertEquals(taskHistory.get(3), new Subtask("Вскипятить воду", "Поставить чайник", 4, 3, 55, "22.12.05 16:12"));
        Assertions.assertEquals(taskHistory.get(taskHistory.size() - 1), new Task("Переезд", "Собрать коробки", 2, 55));
    }
}
