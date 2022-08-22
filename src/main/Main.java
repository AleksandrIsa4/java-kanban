package main;

import main.control.Managers;
import main.control.TaskManager;
import main.target.Epic;
import main.target.Status;
import main.target.Subtask;
import main.target.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        int indexEpic;
        int index = 1;
        inMemoryTaskManager.creationTask(new Task("Переезд", "Собрать коробки", index));
        index++;
        inMemoryTaskManager.creationTask(new Task("Поездка", "Упаковать кошку", index));
        index++;
        indexEpic = index;
        inMemoryTaskManager.creationEpic(new Epic("Приготовить чай", index));
        index++;
        inMemoryTaskManager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", index, indexEpic));
        index++;
        inMemoryTaskManager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", index, indexEpic));
        index++;
        indexEpic = index;
        inMemoryTaskManager.creationEpic(new Epic("Зарядить телефон", index));
        index++;
        inMemoryTaskManager.creationSubtask(new Subtask("Поставить телефон на зарядку", "Подключить телефон к зарядке", index, indexEpic));
    }
}
