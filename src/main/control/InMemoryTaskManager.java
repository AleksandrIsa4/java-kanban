package main.control;

import main.target.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected static Map<Integer, Task> allTask;
    protected static Map<Integer, Subtask> allSubtask;
    protected static Map<Integer, Epic> allEpic;
    protected static HistoryManager historManager;
    Comparator<Task> comparator = new Comparator<>() {
        @Override
        public int compare(Task t1, Task t2) throws NullPointerException {
            if (t2.getStartTime() != null) {
                try {
                    return t1.getStartTime().compareTo(t2.getStartTime());
                } catch (NullPointerException e) {
                    return 1;
                }
            } else {
                return -1;
            }
        }
    };
    Set<Task> treeTask;

    public InMemoryTaskManager() {
        allTask = new HashMap<>();
        allSubtask = new HashMap<>();
        allEpic = new HashMap<>();
        historManager = Managers.getDefaultHistory();
        treeTask = new TreeSet<>(comparator);
    }

    @Override
    public Task getIndexTask(int index) {
        for (Task indexTask : allTask.values()) {
            if (indexTask.getIndex() == index) {
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
        for (Integer index : allTask.keySet()) {
            historManager.remove(index);
            deleteFromTreeTask(index);
        }
        allTask.clear();
    }

    @Override
    public void deleteAllSubtask() {
        for (Integer index : allSubtask.keySet()) {
            int indexEpic = allSubtask.get(index).getIndexEpic();
            allEpic.get(indexEpic).getActions().remove(allSubtask.get(index));
            checkStatusEpic(allEpic.get(indexEpic));
            checkDurationEpic(allEpic.get(indexEpic));
            historManager.remove(index);
            deleteFromTreeSubtask(index);
        }
        allSubtask.clear();
    }

    @Override
    public void deleteAllEpic() {
        for (Integer index : allEpic.keySet()) {
            for (Subtask subtask : allEpic.get(index).getActions()) {
                historManager.remove(subtask.getIndex());
                deleteFromTreeSubtask(subtask.getIndex());
            }
            historManager.remove(index);
        }
        allSubtask.clear();
        allEpic.clear();
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
        Iterator<Task> value = treeTask.iterator();
        boolean timeStartNo;
        boolean timeFinishNo;
        try {
            while (value.hasNext()) {
                Task t = value.next();
                timeStartNo = task.getStartTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getEndTime());
                timeFinishNo = task.getEndTime().isAfter(t.getStartTime()) && task.getEndTime().isBefore(t.getEndTime());
                if (timeStartNo || timeFinishNo) {
                    System.out.println("Дата занята");
                    return;
                }
            }
        } catch (NullPointerException e) {
        }
        allTask.put(task.getIndex(), task);
        treeTask.add(task);
    }

    @Override
    public void creationSubtask(Subtask subtask) {
        Iterator<Task> value = treeTask.iterator();
        boolean timeStartNo;
        boolean timeFinishNo;
        try {
            while (value.hasNext()) {
                Task t = value.next();
                timeStartNo = subtask.getStartTime().isAfter(t.getStartTime()) && subtask.getStartTime().isBefore(t.getEndTime());
                timeFinishNo = subtask.getEndTime().isAfter(t.getStartTime()) && subtask.getEndTime().isBefore(t.getEndTime());
                if (timeStartNo || timeFinishNo) {
                    System.out.println("Дата занята");
                    return;
                }
            }
        } catch (NullPointerException e) {
        }
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
        checkDurationEpic(allEpic.get(subtask.getIndexEpic()));
        treeTask.add(subtask);
    }

    @Override
    public void creationEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }

    @Override
    public void updateTask(Task task) {
        Iterator<Task> value = treeTask.iterator();
        boolean timeStartNo;
        boolean timeFinishNo;
        try {
            while (value.hasNext()) {
                Task t = value.next();
                timeStartNo = task.getStartTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getEndTime());
                timeFinishNo = task.getEndTime().isAfter(t.getStartTime()) && task.getEndTime().isBefore(t.getEndTime());
                if (timeStartNo || timeFinishNo) {
                    System.out.println("Дата занята");
                    return;
                }
            }
        } catch (NullPointerException e) {
        }
        allTask.put(task.getIndex(), task);
        treeTask.add(task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Iterator<Task> value = treeTask.iterator();
        boolean timeStartNo;
        boolean timeFinishNo;
        try {
            while (value.hasNext()) {
                Task t = value.next();
                timeStartNo = subtask.getStartTime().isAfter(t.getStartTime()) && subtask.getStartTime().isBefore(t.getEndTime());
                timeFinishNo = subtask.getEndTime().isAfter(t.getStartTime()) && subtask.getEndTime().isBefore(t.getEndTime());
                if (timeStartNo || timeFinishNo) {
                    System.out.println("Дата занята");
                    return;
                }
            }
        } catch (NullPointerException e) {
        }
        allSubtask.put(subtask.getIndex(), subtask);
        allEpic.get(subtask.getIndexEpic()).setActions(subtask);
        checkStatusEpic(allEpic.get(subtask.getIndexEpic()));
        checkDurationEpic(allEpic.get(subtask.getIndexEpic()));
        treeTask.add(subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        allEpic.put(epic.getIndex(), epic);
    }

    @Override
    public void deleteTask(int index) {
        deleteFromTreeTask(index);
        if (allTask.containsKey(index)) {
            allTask.remove(index);
            historManager.remove(index);
        }
    }

    @Override
    public void deleteSubtask(int index) {
        deleteFromTreeSubtask(index);
        if (allSubtask.containsKey(index)) {
            int indexEpic = allSubtask.get(index).getIndexEpic();
            allEpic.get(indexEpic).getActions().remove(allSubtask.get(index));
            allSubtask.remove(index);
            checkStatusEpic(allEpic.get(indexEpic));
            checkDurationEpic(allEpic.get(indexEpic));
            historManager.remove(index);
        }
    }

    @Override
    public void deleteEpic(int index) {
        if (allEpic.containsKey(index)) {
            for (Subtask subtask : allEpic.get(index).getActions()) {
                deleteFromTreeSubtask(subtask.getIndex());
                allSubtask.remove(subtask.getIndex());
                historManager.remove(subtask.getIndex());
            }
            allEpic.remove(index);
            historManager.remove(index);
        }
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
                checkDurationEpic(allEpic.get(subTask.getIndexEpic()));
            }
        }
    }

    public void checkStatusEpic(Epic epic) {
        boolean positionDone = true;
        boolean positionNEW = true;
        epic.setStatus(Status.NEW);
        if (!epic.getActions().isEmpty()) {
            for (Subtask subtask : epic.getActions()) {
                if (subtask.getStatus() != Status.DONE) {
                    positionDone = false;
                }
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

    public void checkDurationEpic(Epic epic) throws NullPointerException {
        LocalDateTime startTime = LocalDateTime.MAX;
        LocalDateTime endTime = LocalDateTime.MIN;
        Duration duration;
        if (!epic.getActions().isEmpty()) {
            for (Subtask subtask : epic.getActions()) {
                try {
                    if (subtask.getStartTime().isBefore(startTime)) {
                        startTime = subtask.getStartTime();
                    }
                    if (subtask.getEndTime().isAfter(endTime)) {
                        endTime = subtask.getEndTime();
                    }
                } catch (NullPointerException e) {
                }
            }
            epic.setStartTime(startTime);
            epic.setEndTime(endTime);
            epic.setDuration(Duration.between(startTime, endTime));
        }
    }

    @Override
    public List<Task> getHistory() {
        return historManager.getHistory();
    }

    @Override
    public void save() {
        System.out.println("Нет такого метода в InMemoryTaskManager");
    }

    @Override
    public void loadFile(String file) {
        System.out.println("Нет такого метода в InMemoryTaskManager");
    }

    public void deleteFromTreeSubtask(int index) {
        treeTask.remove(allSubtask.get(index));
    }

    public void deleteFromTreeTask(int index) {
        treeTask.remove(allTask.get(index));
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return treeTask;
    }
}
