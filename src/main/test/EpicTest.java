package main.test;

import static org.junit.jupiter.api.Assertions.*;

import main.control.InMemoryTaskManager;
import main.control.TaskManager;
import main.target.Epic;
import main.target.Status;
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
        taskManager.creationEpic(new Epic("Приготовить чай", taskId));
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        actions = savedEpic.getActions();
        assertEquals(0, actions.size(), "Неверное количество задач.");
    }

    @Test
    void newEpic() {
        taskManager.creationEpic(new Epic("Приготовить чай", taskId));
        taskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", (taskId + 1), taskId, 55, "22.12.05 16:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", (taskId + 2), taskId, 55, "22.12.05 18:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай2", "Добавить заварку2", (taskId + 3), taskId, 55, "22.12.05 20:12"));
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(Status.NEW, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void doneEpic() {
        taskManager.creationEpic(new Epic("Приготовить чай", taskId));
        taskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", (taskId + 1), taskId, 55, "22.12.05 16:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", (taskId + 2), taskId, 55, "22.12.05 17:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай2", "Добавить заварку2", (taskId + 3), taskId, 55, "22.12.05 18:12"));
        taskManager.assignSubtask("Вскипятить воду", Status.DONE);
        taskManager.assignSubtask("Выбрать чай", Status.DONE);
        taskManager.assignSubtask("Выбрать чай2", Status.DONE);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.DONE, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void newDoneEpic() {
        taskManager.creationEpic(new Epic("Приготовить чай", taskId));
        taskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", (taskId + 1), taskId, 55, "22.12.05 16:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", (taskId + 2), taskId, 55, "22.12.05 17:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай2", "Добавить заварку2", (taskId + 3), taskId, 55, "22.12.05 18:12"));
        taskManager.assignSubtask("Вскипятить воду", Status.DONE);
        taskManager.assignSubtask("Выбрать чай", Status.DONE);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.IN_PROGRESS, savedEpic.getStatus(), "Неверный статус задачи.");
    }

    @Test
    void progressEpic() {
        taskManager.creationEpic(new Epic("Приготовить чай", taskId));
        taskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", (taskId + 1), taskId, 55, "22.12.05 16:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", (taskId + 2), taskId, 55, "22.12.05 17:12"));
        taskManager.creationSubtask(new Subtask("Выбрать чай2", "Добавить заварку2", (taskId + 3), taskId, 55, "22.12.05 18:12"));
        taskManager.assignSubtask("Вскипятить воду", Status.IN_PROGRESS);
        taskManager.assignSubtask("Выбрать чай", Status.IN_PROGRESS);
        taskManager.assignSubtask("Выбрать чай2", Status.IN_PROGRESS);
        final Epic savedEpic = taskManager.getIndexEpic(taskId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(Status.IN_PROGRESS, savedEpic.getStatus(), "Неверный статус задачи.");
    }
}