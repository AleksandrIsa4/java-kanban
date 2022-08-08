import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> actions = new ArrayList<>();

    public Epic(String name, int index) {

        super(name, "", index);
    }

    public int getIndex() {
        return index;
    }

    //Проверка статуса эпика в зависимости от статуса его подзадач
    public String checkStatus() {
        boolean position = true;
        if (!actions.isEmpty()) {
            for (Subtask subtask : actions) {
                if (subtask.status.equals("In Progress")) {
                    this.status = "In Progress";
                    return ("In Progress");
                }
                if (!subtask.status.equals("Done")) {
                    position = false;
                }
            }
            if (position) {
                this.status = "Done";
                return ("Done");
            }
        }
        this.status = "NEW";
        return ("NEW");
    }


    @Override
    public String toString() {
        return "Название эпика:" + name + ", идентификатор: " + index + ", статус: " + status;
    }


}
