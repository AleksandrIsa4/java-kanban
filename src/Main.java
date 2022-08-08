public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        int indexEpic;
        manager.creationTask("Переезд", "Собрать коробки");
        manager.creationTask("Поездка", "Упаковать кошку");
        indexEpic = manager.creationEpic("Приготовить чай");
        manager.creationSubtask("Вскипятить воду", "Поставить чайник", indexEpic);
        manager.creationSubtask("Выбрать чай", "Добавить заварку", indexEpic);
        indexEpic = manager.creationEpic("Зарядить телефон");
        manager.creationSubtask("Поставить телефон на зарядку", "Подключить телефон к зарядке", indexEpic);
    }
}
