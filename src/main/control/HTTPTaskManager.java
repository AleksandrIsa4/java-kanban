package main.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.client.KVTaskClient;
import main.target.Epic;
import main.target.Subtask;
import main.target.Task;

import java.util.*;

public class HTTPTaskManager extends FileBackedTasksManager {

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    private KVTaskClient kvTaskClient;

    // создает клиент с указанным URL
    public HTTPTaskManager() {
        gson = gsonBuilder.create();
        loadFile("http://localhost:8078/");
    }

    // передача и сохранение всех задач и истории в формате индекса в виде списка на сервер
    @Override
    protected void save() {
        try {
            List<Task> taskList = new ArrayList<>();
            for (Task task : allTask.values()) {
                taskList.add(task);
            }
            String strTask = gson.toJson(taskList);
            kvTaskClient.put("task", strTask);
            List<Epic> epicList = new ArrayList<>();
            for (Epic epic : allEpic.values()) {
                epicList.add(epic);
            }
            String strEpic = gson.toJson(epicList);
            kvTaskClient.put("epic", strEpic);
            List<Subtask> subtaskList = new ArrayList<>();
            for (Subtask subtask : allSubtask.values()) {
                subtaskList.add(subtask);
            }
            String strSubtask = gson.toJson(subtaskList);
            kvTaskClient.put("subtask", strSubtask);
            List<Integer> historykList = new ArrayList<>();
            for (Task history : historManager.getHistory()) {
                historykList.add(history.getIndex());
            }
            String strHistory = gson.toJson(historykList);
            kvTaskClient.put("history", strHistory);
        } catch (Exception e) {
            System.out.println("Не получается сохранить на сервере");
            return;
        }
    }

    /*
         Загрузка и проверка на присутствии задач и истории с сервера (строка в Json->массив задач->список задач)
         Для сохранения задач используется InMemoryTaskManager, чтобы не удалять другие типы задач, так как при создании задач в родительском классе присутствует метод save
     */
    @Override
    public void loadFile(String URL) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        kvTaskClient = new KVTaskClient(URL);
        String taskJson = kvTaskClient.load("task");
        if (!(taskJson.equals(""))) {
            Task[] tasks = gson.fromJson(taskJson, Task[].class);
            List<Task> listTask = List.of(tasks);
            for (Task task : listTask) {
                inMemoryTaskManager.creationTask(task);
            }
        }
        String epicJson = kvTaskClient.load("epic");
        if (!(epicJson.equals(""))) {
            Epic[] epics = gson.fromJson(epicJson, Epic[].class);
            List<Epic> listEpic = List.of(epics);
            for (Epic epic : listEpic) {
                inMemoryTaskManager.creationEpic(epic);
            }
        }
        String subtaskJson = kvTaskClient.load("subtask");
        if (!(subtaskJson.equals(""))) {
            Subtask[] subtasks = gson.fromJson(subtaskJson, Subtask[].class);
            List<Subtask> listSubtask = List.of(subtasks);
            for (Subtask subtask : listSubtask) {
                inMemoryTaskManager.creationSubtask(subtask);
            }
        }
        String historyJson = kvTaskClient.load("history");
        if (!(historyJson.equals(""))) {
            Integer[] history = gson.fromJson(historyJson, Integer[].class);
            List<Integer> list = List.of(history);
            for (Integer index : list) {
                for (Map.Entry<Integer, Task> entry : allTask.entrySet()) {
                    if (index == entry.getKey()) {
                        historManager.add(entry.getValue());
                    }
                }
                for (Map.Entry<Integer, Epic> entry : allEpic.entrySet()) {
                    if (index == entry.getKey()) {
                        historManager.add(entry.getValue());
                    }
                }
                for (Map.Entry<Integer, Subtask> entry : allSubtask.entrySet()) {
                    if (index == entry.getKey()) {
                        historManager.add(entry.getValue());
                    }
                }
            }
        }
    }
}
