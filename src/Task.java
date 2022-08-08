public class Task {
    protected String name;
    protected String description;
    protected int index;
    protected String status = "NEW";

    public Task(String name, String description, int index) {
        this.name = name;
        this.description = description;
        this.index = index;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Название задачи:" + name + ", идентификатор: " + index + ", статус: " + status;
    }
}