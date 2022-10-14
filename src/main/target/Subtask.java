package main.target;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    private int indexEpic;

    public Subtask(String name, String description, int index, int indexEpic, int durationInt, String startTimeString) {
        super(name, description, index, durationInt, startTimeString);
        this.indexEpic = indexEpic;
    }

    public Subtask(String name, String description, int index, int indexEpic, int durationInt) {
        super(name, description, index, durationInt);
        this.indexEpic = indexEpic;
    }

    public int getIndexEpic() {
        return indexEpic;
    }

    public void setIndexEpic(int indexEpic) {
        this.indexEpic = indexEpic;
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return index + "," + name + "," + status + "," + description + "," + indexEpic + "," + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        } else {
            return index + "," + name + "," + status + "," + description + "," + indexEpic;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(index, subtask.index) &&
                Objects.equals(name, subtask.name) &&
                Objects.equals(description, subtask.description) &&
                Objects.equals(indexEpic, subtask.indexEpic);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        hash = hash * 31 * index * indexEpic;

        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }
}