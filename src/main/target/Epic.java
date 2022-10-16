package main.target;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Subtask> actions = new ArrayList<>();

    public Epic(String name, int index) {
        super(name, " ", index, 0);
    }

    public ArrayList<Subtask> getActions() {
        return actions;
    }

    public void setActions(Subtask subtask) {
        actions.add(subtask);
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return index + "," + name + "," + status + "," + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        } else {
            return index + "," + name + "," + status;
        }
    }

    @Override
    public boolean equals(Object o) {
        Epic epic = (Epic) o;
        return super.equals(epic);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
