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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(index, epic.index) &&
                Objects.equals(name, epic.name) &&
                Objects.equals(status, epic.status);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        hash = hash * 31 * index;
        if (status != null) {
            hash = hash + status.hashCode();
        }
        return hash;
    }
}
