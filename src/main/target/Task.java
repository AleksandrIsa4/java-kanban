package main.target;

public class Task {
    private String name;
    private String description;
    private int index;
    private String status;

    public Task(String name, String description, int index) {
        this.name = name;
        this.description = description;
        this.index = index;
        status = "NEW";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Название задачи: " + name + ", идентификатор: " + index + ", статус: " + status;
    }
}