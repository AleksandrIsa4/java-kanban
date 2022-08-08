import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubtask = new HashMap<>();
    HashMap<Integer, Epic> allEpic = new HashMap<>();
    int index = 1;

    public void printAllTask() {
        System.out.println("Список всех задач");
        for (Task print : allTask.values()) {
            System.out.println(print);
        }
    }

    public void printAllSubtask() {
        System.out.println("Список всех подзадач");
        for (Subtask print : allSubtask.values()) {
            System.out.println(print);
        }
    }

    public void printAllEpic() {
        System.out.println("Список всех эпиков");
        for (Epic print : allEpic.values()) {
            System.out.println(print);
        }
    }

    public void deleteAllTask() {
        allTask.clear();
    }

    public void deleteAllSubtask() {
        allSubtask.clear();
        for (Epic epic : allEpic.values()) {
            epic.actions.clear();
            epic.checkStatus();
        }
    }

    public void deleteAllEpic() {
        allEpic.clear();
        allSubtask.clear();
    }

    //Получение задачи по index
    public Task getIndexTask(int index) {
        for (Task indexTask : allTask.values()) {
            if (indexTask.index == index) {
                return indexTask;
            }
        }
        return null;
    }
    //Получение подзадачи по index
    public Subtask getIndexSubtask(int index) {
        for (Subtask indexTask : allSubtask.values()) {
            if (indexTask.index == index) {
                return indexTask;
            }
        }
        return null;
    }

    //Получение эпика по index
    public Epic getIndexEpic(int index) {
        for (Epic indexTask : allEpic.values()) {
            if (indexTask.getIndex() == index) {
                return indexTask;
            }
        }
        return null;
    }

    //Получение всех подзадач по name эпика
    public void allSubtaskEpic(String name) {
        System.out.println("Подзадачи эпика " + name + ":");
        for (Epic specificEpic : allEpic.values()) {
            if (name.equals(specificEpic.name)) {
                System.out.println(specificEpic.actions);
            }
        }
    }

    //Создание задачи с названием и описанием
    public void creationTask(String name, String description) {
        allTask.put(index, new Task(name, description, index));
        index++;
    }

    //Создание подзадачи с названием и описанием и индексом его эпика
    public void creationSubtask(String name, String description, int indexEpic) {
        Subtask subtask = new Subtask(name, description, index, indexEpic);
        index++;
        allSubtask.put(index, subtask);
        for (Epic epic : allEpic.values()) {
            if (epic.getIndex() == indexEpic) {
                epic.actions.add(subtask);
            }
        }
    }

    //Создание эпика с названием
    public int creationEpic(String name) {
        allEpic.put(index, new Epic(name, index));
        index++;
        return (index - 1);

    }

    public void deleteTask(int index) {
        allTask.remove(index);
    }

    public void deleteSubtask(int index) {
        allSubtask.remove(index);
    }

    public void deleteEpic(int index) {
        allEpic.remove(index);
    }

    //передача нового состояния задачи
    public void assignTask(String name, String status) {
        for (Task task : allTask.values()) {
            if (name.equals(task.name)) {
                task.setStatus(status);
            }
        }
    }

    //передача нового состояния подзадачи
    public void assignSubtask(String name, String status) {
        for (Subtask subTask : allSubtask.values()) {
            if (name.equals(subTask.name)) {
                subTask.setStatus(status);
                getIndexEpic(subTask.indexEpic).checkStatus();
            }
        }
    }


}
