package main.test;

import static org.junit.jupiter.api.Assertions.*;

import main.control.InMemoryTaskManager;
import main.control.InterfaceManager.TaskManager;
import main.target.Epic;
import main.target.enumeration.Status;
import main.target.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EpicTest {
    private TaskManager taskManager;
    private int taskId;
    private List<Subtask> actions;

    @BeforeEach
    void BeforeAll() {
        taskManager = new InMemoryTaskManager();
        taskId = 1;
        actions = new ArrayList<>();
    }

    @Test
    void emptyEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(taskId);
        taskManager.creationEpic(epic1);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        actions = savedEpic.getActions();
        assertEquals(0, actions.size(), "Неверное количество задач.");
    }

    @Test
    void newEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(taskId);
        taskManager.creationEpic(epic1);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(taskId + 1);
        taskManager.creationSubtask(subtask1);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(taskId + 2);
        taskManager.creationSubtask(subtask2);
        Subtask subtask3=new Subtask("Выбрать чай2", "Добавить заварку2", 3, 55, "22.12.05 16:52");
        subtask3.setIndex(taskId + 3);
        taskManager.creationEpic(epic1);
        taskManager.creationSubtask(subtask1);
        taskManager.creationSubtask(subtask2);
        taskManager.creationSubtask(subtask3);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(Status.NEW, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void doneEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(taskId);
        taskManager.creationEpic(epic1);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(taskId + 1);
        taskManager.creationSubtask(subtask1);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.12.05 17:12");
        subtask2.setIndex(taskId + 2);
        taskManager.creationSubtask(subtask2);
        Subtask subtask3=new Subtask("Выбрать чай2", "Добавить заварку2", 3, 55, "22.12.05 18:12");
        subtask3.setIndex(taskId + 3);
        taskManager.creationEpic(epic1);
        taskManager.creationSubtask(subtask1);
        taskManager.creationSubtask(subtask2);
        taskManager.creationSubtask(subtask3);
        taskManager.assignSubtask("Вскипятить воду", Status.DONE);
        taskManager.assignSubtask("Выбрать чай", Status.DONE);
        taskManager.assignSubtask("Выбрать чай2", Status.DONE);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.DONE, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void newDoneEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(taskId);
        taskManager.creationEpic(epic1);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(taskId + 1);
        taskManager.creationSubtask(subtask1);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.12.05 17:12");
        subtask2.setIndex(taskId + 2);
        taskManager.creationSubtask(subtask2);
        Subtask subtask3=new Subtask("Выбрать чай2", "Добавить заварку2", 3, 55, "22.12.05 18:12");
        subtask3.setIndex(taskId + 3);
        taskManager.creationEpic(epic1);
        taskManager.creationSubtask(subtask1);
        taskManager.creationSubtask(subtask2);
        taskManager.creationSubtask(subtask3);
        taskManager.assignSubtask("Вскипятить воду", Status.DONE);
        taskManager.assignSubtask("Выбрать чай", Status.DONE);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.IN_PROGRESS, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void progressEpic() {
        Epic epic1=new Epic("Приготовить чай");
        epic1.setIndex(taskId);
        taskManager.creationEpic(epic1);
        Subtask subtask1=new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(taskId + 1);
        taskManager.creationSubtask(subtask1);
        Subtask subtask2=new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.12.05 17:12");
        subtask2.setIndex(taskId + 2);
        taskManager.creationSubtask(subtask2);
        Subtask subtask3=new Subtask("Выбрать чай2", "Добавить заварку2", 3, 55, "22.12.05 18:12");
        subtask3.setIndex(taskId + 3);
        taskManager.creationEpic(epic1);
        taskManager.creationSubtask(subtask1);
        taskManager.creationSubtask(subtask2);
        taskManager.creationSubtask(subtask3);
        taskManager.assignSubtask("Вскипятить воду", Status.IN_PROGRESS);
        taskManager.assignSubtask("Выбрать чай", Status.IN_PROGRESS);
        taskManager.assignSubtask("Выбрать чай2", Status.IN_PROGRESS);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.IN_PROGRESS, savedEpic.getStatus(), "Неверный статус задачи.");
    }
}