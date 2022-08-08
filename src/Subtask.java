public class Subtask extends Task {

    protected int indexEpic;

    public Subtask(String name, String description, int index, int indexEpic) {
        super(name, description, index);
        this.indexEpic = indexEpic;
    }

    @Override
    public String toString() {
        return "Название подзадачи:" + name + ", идентификатор: " + index + ", статус: " + status; // просто возвращаем поля класса
    }

}
