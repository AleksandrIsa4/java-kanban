package main.test;

import main.control.InterfaceManager.TaskManager;
import main.target.Epic;
import main.target.enumeration.Status;
import main.target.Subtask;
import main.target.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected void initTask() {
        Task task1=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        taskManager.creationTask(task1);
        Task task2=new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        taskManager.creationTask(task2);

    }

    protected void initEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(3);
        taskManager.creationEpic(epic1);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        taskManager.creationSubtask(subtask1);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(5);
        taskManager.creationSubtask(subtask2);
        Subtask subtask3=new Subtask("Выбрать чай2", "Добавить заварку2", 3, 55, "22.12.05 16:52");
        subtask3.setIndex(6);
        taskManager.creationSubtask(subtask3);
        Epic epic2=new Epic("Зарядить телефон");
        epic2.setIndex(7);
        taskManager.creationEpic(epic2);
    }

    @Test
    void getIndexTaskTest() throws IOException, InterruptedException {
        Task task = taskManager.getIndexTask(1);
        Task task2 = taskManager.getIndexTask(5);
        Task taskTest=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        taskTest.setIndex(1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, taskTest),
                () -> Assertions.assertNull(task2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getIndexSubtaskTest() throws IOException, InterruptedException {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Subtask subtask2 = taskManager.getIndexSubtask(10);
        Subtask subtaskTest=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtaskTest.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtaskTest),
                () -> Assertions.assertNull(subtask2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются."),
                () -> Assertions.assertNotNull(subtask.getIndexEpic(), "Индекс не эпика возвращается.")
        );
    }

    @Test
    void getIndexEpicTest() throws IOException, InterruptedException {
        Epic epic = taskManager.getIndexEpic(7);
        Epic epic2 = taskManager.getIndexEpic(10);
        Epic epicTest=new Epic("Зарядить телефон");
        epicTest.setIndex(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epicTest),
                () -> Assertions.assertNull(epic2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void getAllTaskTest() throws IOException, InterruptedException {
        String task = taskManager.getAllTask();
        Task task1=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task2=new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        String print = "Список всех задач \n" + task1 + "\n" + task2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getAllSubtaskTest() throws IOException, InterruptedException {
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(5);
        String task = taskManager.getAllSubtask();
        String print = "Список всех подзадач \n" + subtask1 + "\n" + subtask2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void getAllEpicTest() throws IOException, InterruptedException {
        String task = taskManager.getAllEpic();
        String print = "Список всех эпиков \n" + "3,Приготовить чай,NEW, PT146817H38M,22.12.05 16:12" + "\n" + "7,Зарядить телефон,NEW" + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Test
    void deleteAllTaskTest() throws IOException, InterruptedException {
        taskManager.deleteAllTask();
        Assertions.assertEquals(taskManager.getAllTask(), "Список всех задач \n");
    }

    @Test
    void deleteAllSubtaskTest() throws IOException, InterruptedException {
        taskManager.deleteAllSubtask();
        Assertions.assertEquals(taskManager.getAllSubtask(), "Список всех подзадач \n");
    }

    @Test
    void deleteAllEpicTest() throws IOException, InterruptedException {
        taskManager.deleteAllEpic();
        Assertions.assertEquals(taskManager.getAllEpic(), "Список всех эпиков \n");
    }

    @Test
    void allSubtaskEpicTest() throws IOException, InterruptedException {
        List<Subtask> tasks = taskManager.allSubtaskEpic(3);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(5);
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, tasks.size()),
                () -> Assertions.assertEquals(tasks.get(1), subtask2),
                () -> Assertions.assertTrue(tasks.contains(subtask2)),
                () -> Assertions.assertNotNull(tasks, "Задача не возвращаются.")
        );
    }

    @Test
    void creationTaskTest() throws IOException, InterruptedException {
        Task task = taskManager.getIndexTask(1);
        Task task1=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task33=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:55");
        task33.setIndex(33);
        taskManager.creationTask(task33);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, task1),
                () -> Assertions.assertNull(taskManager.getIndexTask(33), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Test
    void creationSubtaskTest() throws IOException, InterruptedException {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtask1),
                () -> Assertions.assertNull(taskManager.getIndexSubtask(24), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Test
    void creationEpicTest() throws IOException, InterruptedException {
        Epic epic = taskManager.getIndexEpic(7);
        Epic epic2=new Epic("Зарядить телефон");
        epic2.setIndex(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epic2),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void updateTaskTest() throws IOException, InterruptedException {
        Task task = taskManager.getIndexTask(1);
        Task task1=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task33=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:55");
        task33.setIndex(33);
        taskManager.creationTask(task33);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, task1),
                () -> Assertions.assertNull(taskManager.getIndexTask(33), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Test
    void updateSubtaskTest() throws IOException, InterruptedException {
        Subtask subtask = taskManager.getIndexSubtask(4);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtask1),
                () -> Assertions.assertNull(taskManager.getIndexTask(55), "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Test
    void updateEpicTest() throws IOException, InterruptedException {
        Epic epic = taskManager.getIndexEpic(7);
        Epic epic2=new Epic("Зарядить телефон");
        epic2.setIndex(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epic2),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Test
    void deleteTaskTest() throws IOException, InterruptedException {
        taskManager.deleteTask(1);
        String task = taskManager.getAllTask();
        Task task2=new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        String print = "Список всех задач \n" + task2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться.")
        );
    }

    @Test
    void deleteSubtaskTest() throws IOException, InterruptedException {
        taskManager.deleteSubtask(5);
        List<Subtask> tasks = taskManager.allSubtaskEpic(3);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexSubtask(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(1, tasks.size()),
                () -> Assertions.assertTrue(tasks.contains(subtask1)),
                () -> Assertions.assertNotNull(tasks, "Задача не возвращаются.")
        );
        ;
    }

    @Test
    void deleteEpicTest() throws IOException, InterruptedException {
        taskManager.deleteEpic(3);
        List<Subtask> tasks = taskManager.allSubtaskEpic(3);
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
    void getHistoryEmptyTest() throws IOException, InterruptedException {
        taskManager.deleteAllEpic();
        taskManager.deleteAllTask();
        List<Task> taskHistory = taskManager.getHistory();
        System.out.println(taskManager.getHistory());
        Assertions.assertEquals(0, taskHistory.size());
    }

    @Test
    void getHistoryDuplicationTest() throws IOException, InterruptedException {
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
    void getHistoryDeleteTest() throws IOException, InterruptedException {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexTask(2);
        List<Task> taskHistory = taskManager.getHistory();
        Task task1=new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task2=new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Assertions.assertEquals(taskHistory.get(0), task1);
        Assertions.assertEquals(taskHistory.get(3), subtask1);
        Assertions.assertEquals(taskHistory.get(taskHistory.size() - 1), task2);
    }
}
