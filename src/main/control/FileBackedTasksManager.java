package main.control;

import main.target.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    @Override
    public void save() {
        StringBuilder str = new StringBuilder();
        str.append("id,type,name,status,description,epic");
        for (Map.Entry<Integer, Task> entry : allTask.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            if (entry.getValue().getStartTime() != null) {
                str.append("," + Type.TASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + "," + entry.getValue().getDuration() + "," + entry.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")) + ",");
            } else {
                str.append("," + Type.TASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + "," + entry.getValue().getDuration() + ",");
            }
        }
        for (Map.Entry<Integer, Epic> entry : allEpic.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            if (entry.getValue().getStartTime() != null) {
                str.append("," + Type.EPIC + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDuration() + "," + entry.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")) + ",");

            } else {
                str.append("," + Type.EPIC + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDuration() + ",");
            }
        }
        for (Map.Entry<Integer, Subtask> entry : allSubtask.entrySet()) {
            str.append("\n" + String.valueOf(entry.getKey()));
            if (entry.getValue().getStartTime() != null) {
                str.append("," + Type.SUBTASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + "," + entry.getValue().getIndexEpic() + "," + entry.getValue().getDuration() + "," + entry.getValue().getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")) + ",");
            } else {
                str.append("," + Type.SUBTASK + "," + entry.getValue().getName() + "," + entry.getValue().getStatus() + "," + entry.getValue().getDescription() + "," + entry.getValue().getIndexEpic() + "," + entry.getValue().getDuration() + ",");
            }
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
        StringBuilder str = new StringBuilder();
        for (Task history : manager.getHistory()) {
            try {
                str.append(history.getIndex() + ",");
            } catch (NullPointerException e) {
                str.append(",");
            }
        }
        return str.toString();
    }

    public void fromString(String value) {
        String[] task = value.split(",");
        int index = Integer.parseInt(task[0]);
        Type type = Type.valueOf(task[1]);
        String nameTask = task[2];
        Status statusTask = Status.valueOf(task[3]);
        String descriptionTask;
        int duration;
        String startTime;
        switch (type) {
            case TASK:
                descriptionTask = task[4];
                duration = Duration.parse(task[5]).toMinutesPart();
                try {
                    startTime = task[6];
                    super.creationTask(new Task(nameTask, descriptionTask, index, duration, startTime));
                    assignTask(nameTask, statusTask);
                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    super.creationTask(new Task(nameTask, descriptionTask, index, duration));
                    assignTask(nameTask, statusTask);
                    break;
                }
            case EPIC:
                super.creationEpic(new Epic(nameTask, index));
                break;
            case SUBTASK:
                descriptionTask = task[4];
                int indexEpic = Integer.parseInt(task[5]);
                duration = Duration.parse(task[6]).toMinutesPart();
                try {
                    startTime = task[7];
                    super.creationSubtask(new Subtask(nameTask, descriptionTask, index, indexEpic, duration, startTime));
                    assignSubtask(nameTask, statusTask);
                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    super.creationSubtask(new Subtask(nameTask, descriptionTask, index, indexEpic, duration));
                    assignSubtask(nameTask, statusTask);
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public void loadFile(String file) {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Неправильный формат файла");
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
