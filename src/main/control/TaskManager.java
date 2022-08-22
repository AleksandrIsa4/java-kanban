package main.control;

import main.target.*;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    String getAllTask();

    String getAllSubtask();

    String getAllEpic();

    void deleteAllTask();

    void deleteAllSubtask();

    void deleteAllEpic();

    //Получение задачи по index
    Task getIndexTask(int index);

    //Получение подзадачи по index
    Subtask getIndexSubtask(int index);


    //Получение эпика по index
    Epic getIndexEpic(int index);

    //Получение всех подзадач по name эпика
    ArrayList<Subtask> allSubtaskEpic(String name);

    //Добавление задачи в список
    // void creationTask(Task task);
    void creationTask(Task task);

    //Добавление подзадачи в список
    void creationSubtask(Subtask subtask);

    //Добавление эпика в список
    void creationEpic(Epic epic);

    // Обновление задачи
    void updateTask(Task task);

    // Обновление подзадачи
    void updateSubtask(Subtask subtask);

    // Обновление эпика
    void updateEpic(Epic epic);

    void deleteTask(int index);

    void deleteSubtask(int index);

    void deleteEpic(int index);

    //передача нового состояния задачи
    void assignTask(String name, Status status);

    //передача нового состояния подзадачи
    void assignSubtask(String name, Status status);

    //обновление статуса эпика
    void checkStatusEpic(Epic epic);

    //история просмотров задач
    List<Task> getHistory();
}
