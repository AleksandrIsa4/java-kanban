package main.target;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    private int indexEpic;

    public Subtask(String name, String description, int indexEpic, int durationInt, String startTimeString) {
        super(name, description, durationInt, startTimeString);
        this.indexEpic = indexEpic;
    }

    public Subtask(String name, String description, int indexEpic, int durationInt) {
        super(name, description, durationInt);
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
            return index + ", " + name + ", " + status + ", " + description + ", " + indexEpic + ", " + getDuration() + ", " + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        } else {
            return index + ", " + name + ", " + status + ", " + description + ", " + indexEpic + ", " + getDuration();
        }
    }

    @Override
    public boolean equals(Object o) {
        Subtask subtask = (Subtask) o;
        return super.equals(subtask) &&
                Objects.equals(indexEpic, subtask.indexEpic);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 2 * indexEpic;
        return hash;
    }
}