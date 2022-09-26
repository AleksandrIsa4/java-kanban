package main.control;

import main.target.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    @Override
    public void save() {
        StringBuilder str = new StringBuilder();
        str.append("id,type,name,status,description,epic");
        for (Map.Entry<Integer, Task> entry : allTask.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            str.append("," + Type.TASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + ",");
        }
        for (Map.Entry<Integer, Epic> entry : allEpic.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            str.append("," + Type.EPIC + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + ",");
        }
        for (Map.Entry<Integer, Subtask> entry : allSubtask.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            str.append("," + Type.SUBTASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + "," + entry.getValue().getIndexEpic() + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("\n\n");
        str.append(historyToString(historManager));
        str.deleteCharAt(str.length() - 1);
        try (Writer fileWriter = new FileWriter("fileTask.csv")) {
            fileWriter.write(str.toString());
        } catch (IOException e) {
            System.out.println("Невозможно создать файл");
        }
    }

    private static String historyToString(HistoryManager manager) {
        String str = "";
        for (Task history : manager.getHistory()) {
            str += history.getIndex() + ",";
        }
        return str;
    }

    public void fromString(String value) {
        String[] task = value.split(",");
        switch (Type.valueOf(task[1])) {
            case TASK:
                super.creationTask(new Task(task[2], task[4], Integer.parseInt(task[0])));
                assignTask(task[2], Status.valueOf(task[3]));
                break;
            case EPIC:
                super.creationEpic(new Epic(task[2], Integer.parseInt(task[0])));
                break;
            case SUBTASK:
                super.creationSubtask(new Subtask(task[2], task[4], Integer.parseInt(task[0]), Integer.parseInt(task[5])));
                assignSubtask(task[2], Status.valueOf(task[3]));
                break;
            default:
                break;
        }
    }

    @Override
    public void loadFromFile(String file) {
        try {
            Path filePath = Path.of(file);
            String content = Files.readString(filePath);
            String[] split = content.split("\n");
            if (split[0].equals("id,type,name,status,description,epic") && split[split.length - 2].equals("")) {
                for (int i = 1; i < split.length - 2; i++) {
                    fromString(split[i]);
                }
                historyFromString(split[split.length - 1]);
            } else {
                System.out.println("Неправильный формат файла");
            }
        } catch (IOException e) {
            System.out.println("Невозможно считать файл");
        }
    }

    private static void historyFromString(String value) {
        String[] history = value.split(",");
        for (String view : history) {
            for (Map.Entry<Integer, Task> entry : allTask.entrySet()) {
                if (Integer.parseInt(view) == entry.getKey()) {
                    historManager.add(entry.getValue());
                }
            }
            for (Map.Entry<Integer, Epic> entry : allEpic.entrySet()) {
                if (Integer.parseInt(view) == entry.getKey()) {
                    historManager.add(entry.getValue());
                }
            }
            for (Map.Entry<Integer, Subtask> entry : allSubtask.entrySet()) {
                if (Integer.parseInt(view) == entry.getKey()) {
                    historManager.add(entry.getValue());
                }
            }
        }
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void creationTask(Task task) {
        super.creationTask(task);
        save();
    }

    @Override
    public void creationSubtask(Subtask subtask) {
        super.creationSubtask(subtask);
        save();
    }

    @Override
    public void creationEpic(Epic epic) {
        super.creationEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTask(int index) {
        super.deleteTask(index);
        save();
    }

    @Override
    public void deleteSubtask(int index) {
        super.deleteSubtask(index);
        save();
    }

    @Override
    public void deleteEpic(int index) {
        super.deleteEpic(index);
        save();
    }

    @Override
    public void assignTask(String name, Status status) {
        super.assignTask(name, status);
        save();
    }

    @Override
    public void assignSubtask(String name, Status status) {
        super.assignSubtask(name, status);
        save();
    }

    @Override
    public Task getIndexTask(int index) {
        Task task = super.getIndexTask(index);
        save();
        return task;
    }

    @Override
    public Subtask getIndexSubtask(int index) {
        Subtask task = super.getIndexSubtask(index);
        save();
        return task;
    }

    @Override
    public Epic getIndexEpic(int index) {
        Epic task = super.getIndexEpic(index);
        save();
        return task;
    }
}
