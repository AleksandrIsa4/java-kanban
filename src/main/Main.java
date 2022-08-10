package main;

import main.control.Manager;
import main.target.Epic;
import main.target.Subtask;
import main.target.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        int indexEpic;
        int index = 1;
        manager.creationTask(new Task("Переезд", "Собрать коробки", index));
        index++;
        manager.creationTask(new Task("Поездка", "Упаковать кошку", index));
        index++;
        indexEpic = index;
        manager.creationEpic(new Epic("Приготовить чай", index));
        index++;
        manager.creationSubtask(new Subtask("Вскипятить воду", "Поставить чайник", index, indexEpic));
        index++;
        manager.creationSubtask(new Subtask("Выбрать чай", "Добавить заварку", index, indexEpic));
        index++;
        indexEpic = index;
        manager.creationEpic(new Epic("Зарядить телефон", index));
        index++;
        manager.creationSubtask(new Subtask("Поставить телефон на зарядку", "Подключить телефон к зарядке", index, indexEpic));
    }
}
