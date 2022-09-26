package main.target;

public class Subtask extends Task {
    private int indexEpic;

    public Subtask(String name, String description, int index, int indexEpic) {
        super(name, description, index);
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
        return index + "," + name + "," + status + "," + description + "," + indexEpic;
    }
}
