package main.target;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> actions = new ArrayList<>();

    public Epic(String name, int index) {

        super(name, " ", index);
    }

    public ArrayList<Subtask> getActions() {
        return actions;
    }

    public void setActions(Subtask subtask) {
        actions.add(subtask);
    }

    @Override
    public String toString() {
        return index + "," + name + "," + status;
    }
}
