package main.control;

import main.target.Epic;
import main.target.Subtask;
import main.target.Task;

import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> allTask = new HashMap<>();
    HashMap<Integer, Subtask> allSubtask = new HashMap<>();
    HashMap<Integer, Epic> allEpic = new HashMap<>();

    public String getAllTask() {
        String print = "Список всех задач \n";
        for (Task task : allTask.values()) {
            print += task + "\n";
        }
        return print;
    }

    public String getAllSubtask() {
        String print = "Список всех подзадач \n";
        for (Subtask task : allSubtask.values()) {
            print += task + "\n";
        }
        return print;
    }

    public String getAllEpic() {
        String print = "Список всех эпиков \n";
        for (Epic task : allEpic.values()) {
            print += task + "\n";
        }
        return print;
    }

    public void deleteAllTask() {
        allTask.clear();
    }

    public void deleteAllSubtask() {
        allSubtask.clear();
        for (Epic epic : allEpic.values()) {
            epic.getActions().clear();
            checkStatusEpic(epic);
        }
    }

    public void deleteAllEpic() {
        allEpic.clear();
        allSubtask.clear();
    }

    //Получение задачи по index
    public Task getIndexTask(int index) {
        for (Task indexTask : allTask.values()) {
            if (indexTask.getIndex() == index) {
                return indexTask;
            }
        }
        return null;
    }

    //Получение подзадачи по index
    public Subtask getIndexSubtask(int index) {
        for (Subtask indexTask : allSubtask.values()) {
            if (indexTask.getIndex() == index) {
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
            if (name.equals(specificEpic.getName())) {
                System.out.println(specificEpic.getActions());
            }
        }
    }

    //Добавление задачи в список
    public void creationTask(Task task) {
        allTask.put(task.getIndex(), task);
    }

    //Добавление подзадачи в список
    public void creationSubtask(Subtask subtask) {
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
    }

    //Добавление эпика в список
    public void creationEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }

    // Обновление задачи
    public void updateTask(Task task) {
        allTask.put(task.getIndex(), task);
    }

    // Обновление подзадачи
    public void updateSubtask(Subtask subtask) {
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
    }

    // Обновление эпика
    public void updateEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }


    public void deleteTask(int index) {
        allTask.remove(index);
    }

    public void deleteSubtask(int index) {
        int indexEpic = allSubtask.get(index).getIndexEpic();
        allEpic.get(indexEpic).getActions().remove(allSubtask.get(index));
        allSubtask.remove(index);
        checkStatusEpic(allEpic.get(indexEpic));
    }

    public void deleteEpic(int index) {
        allEpic.remove(index);
    }

    //передача нового состояния задачи
    public void assignTask(String name, String status) {
        for (Task task : allTask.values()) {
            if (name.equals(task.getName())) {
                task.setStatus(status);
            }
        }
    }

    //передача нового состояния подзадачи
    public void assignSubtask(String name, String status) {
        for (Subtask subTask : allSubtask.values()) {
            if (name.equals(subTask.getName())) {
                subTask.setStatus(status);
                checkStatusEpic(allEpic.get(subTask.getIndexEpic()));
            }
        }
    }

    //обновление статуса эпика
    public void checkStatusEpic(Epic epic) {
        boolean positionDone = true;
        boolean positionNEW = true;
        epic.setStatus("NEW");
        if (!epic.getActions().isEmpty()) {
            for (Subtask subtask : epic.getActions()) {
                if (!subtask.getStatus().equals("Done")) {
                    positionDone = false;
                }
                if (!subtask.getStatus().equals("NEW")) {
                    positionNEW = false;
                }
            }
            if (positionDone) {
                epic.setStatus("Done");
                return;
            }
            if (positionNEW) {
                epic.setStatus("NEW");
                return;
            }
            epic.setStatus("In Progress");
        }
    }
}
