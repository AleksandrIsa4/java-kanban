package main.control;

import main.target.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> allTask = new HashMap<>();
    private HashMap<Integer, Subtask> allSubtask = new HashMap<>();
    private HashMap<Integer, Epic> allEpic = new HashMap<>();
    private HistoryManager historManager = Managers.getDefaultHistory();

    @Override
    public Task getIndexTask(int index) {
        for (Task indexTask : allTask.values()) {
            if (indexTask.getIndex() == index) {
                //    recordHistory(indexTask);
                historManager.add(indexTask);
                return indexTask;
            }
        }
        return null;
    }

    @Override
    public Subtask getIndexSubtask(int index) {
        for (Subtask indexTask : allSubtask.values()) {
            if (indexTask.getIndex() == index) {
                //          recordHistory(indexTask);
                historManager.add(indexTask);
                return indexTask;
            }
        }
        return null;
    }

    @Override
    public Epic getIndexEpic(int index) {
        for (Epic indexTask : allEpic.values()) {
            if (indexTask.getIndex() == index) {
                historManager.add(indexTask);
                //         recordHistory(indexTask);
                return indexTask;
            }
        }
        return null;
    }

    @Override
    public String getAllTask() {
        String print = "Список всех задач \n";
        for (Task task : allTask.values()) {
            print += task + "\n";
        }
        return print;
    }

    @Override
    public String getAllSubtask() {
        String print = "Список всех подзадач \n";
        for (Subtask task : allSubtask.values()) {
            print += task + "\n";
        }
        return print;
    }

    @Override
    public String getAllEpic() {
        String print = "Список всех эпиков \n";
        for (Epic task : allEpic.values()) {
            print += task + "\n";
        }
        return print;
    }

    @Override
    public void deleteAllTask() {
        allTask.clear();
    }

    @Override
    public void deleteAllSubtask() {
        allSubtask.clear();
        for (Epic epic : allEpic.values()) {
            epic.getActions().clear();
            checkStatusEpic(epic);
        }
    }

    @Override
    public void deleteAllEpic() {
        allEpic.clear();
        allSubtask.clear();
    }


    @Override
    public ArrayList<Subtask> allSubtaskEpic(String name) {
        for (Epic specificEpic : allEpic.values()) {
            if (name.equals(specificEpic.getName())) {
                return (specificEpic.getActions());
            }
        }
        return null;
    }

    @Override
    public void creationTask(Task task) {
        allTask.put(task.getIndex(), task);
    }

    @Override
    public void creationSubtask(Subtask subtask) {
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
    }

    @Override
    public void creationEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }

    @Override
    public void updateTask(Task task) {
        allTask.put(task.getIndex(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
    }

    @Override
    public void updateEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }

    @Override
    public void deleteTask(int index) {
        allTask.remove(index);
    }

    @Override
    public void deleteSubtask(int index) {
        int indexEpic = allSubtask.get(index).getIndexEpic();
        allEpic.get(indexEpic).getActions().remove(allSubtask.get(index));
        allSubtask.remove(index);
        checkStatusEpic(allEpic.get(indexEpic));
    }

    @Override
    public void deleteEpic(int index) {
        allEpic.remove(index);
    }

    @Override
    public void assignTask(String name, Status status) {
        for (Task task : allTask.values()) {
            if (name.equals(task.getName())) {
                task.setStatus(status);
            }
        }
    }

    @Override
    public void assignSubtask(String name, Status status) {
        for (Subtask subTask : allSubtask.values()) {
            if (name.equals(subTask.getName())) {
                subTask.setStatus(status);
                checkStatusEpic(allEpic.get(subTask.getIndexEpic()));
            }
        }
    }

    @Override
    public void checkStatusEpic(Epic epic) {
        boolean positionDone = true;
        boolean positionNEW = true;
        epic.setStatus(Status.NEW);
        if (!epic.getActions().isEmpty()) {
            for (Subtask subtask : epic.getActions()) {
                //  if (!subtask.getStatus().equals("DONE")) {
                if (subtask.getStatus() != Status.DONE) {
                    positionDone = false;
                }
                //     if (!subtask.getStatus().equals("NEW")) {
                if (subtask.getStatus() != Status.NEW) {
                    positionNEW = false;
                }
            }
            if (positionDone) {
                epic.setStatus(Status.DONE);
                return;
            }
            if (positionNEW) {
                epic.setStatus(Status.NEW);
                return;
            }
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historManager.getHistory();
    }
}