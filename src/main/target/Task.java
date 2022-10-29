package main.target;

import main.target.enumeration.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int index = 0;
    protected Status status;
    protected Duration duration;
    protected int durationInt;
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;


    public Task(String name, String description, int durationInt, String startTimeString) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
        this.durationInt = durationInt;
        startTime = LocalDateTime.parse(startTimeString, formatter);
    }

    public Task(String name, String description, int durationInt) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
        this.durationInt = durationInt;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        if (duration == null) {
            duration = Duration.ofMinutes((long) durationInt);
            return duration;
        } else {
            return duration;
        }
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        endTime = startTime.plus(Duration.ofMinutes((long) durationInt));
        return endTime;
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return index + ", " + name + ", " + status + ", " + description + ", " + getDuration() + ", " + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        } else {
            return index + ", " + name + ", " + status + ", " + description + ", " + getDuration();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (startTime != null) {
            return Objects.equals(index, task.index) &&
                    Objects.equals(name, task.name) &&
                    Objects.equals(description, task.description) &&
                    Objects.equals(status, task.status) &&
                    //    Objects.equals(duration, task.duration) &&
                    Objects.equals(durationInt, task.durationInt) &&
                    Objects.equals(startTime, task.startTime);
        } else {
            return Objects.equals(index, task.index) &&
                    Objects.equals(name, task.name) &&
                    Objects.equals(description, task.description) &&
                    Objects.equals(status, task.status) &&
                    Objects.equals(durationInt, task.durationInt);

        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 * index;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode();
        }
        if (status != null) {
            hash = hash + status.hashCode();
        }
        hash = hash * 5 * durationInt;
        if (startTime != null) {
            hash = hash + startTime.hashCode();
        }
        return hash;
    }
}