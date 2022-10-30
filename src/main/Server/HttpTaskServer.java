package main.Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.control.InMemoryTaskManager;
import main.control.InterfaceManager.TaskManager;
import main.target.Epic;
import main.target.Subtask;
import main.target.Task;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private Gson gson;
    private TaskManager httpTaskManager;
    private HttpServer httpServer;

    public HttpTaskServer(TaskManager httpTaskManager) throws IOException {
        gson = new Gson();
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();
        this.httpTaskManager = httpTaskManager;
    }

    public void stopHTTPServer() throws IOException {
        this.httpServer.stop(0);
    }

    class TasksHandler extends InMemoryTaskManager implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String[] splitStrings = path.split("/");
            if (splitStrings.length == 2 && splitStrings[1].equals("tasks")) {
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getPrioritizedTasks().toString()).getBytes());
                    return;
                }
            }
            String taskType = splitStrings[2];
            String index = httpExchange.getRequestURI().getQuery();
            switch (method) {
                case "POST":
                    postRequest(taskType, httpExchange);
                    break;
                case "GET":
                    if (index == null) {
                        getRequest(taskType, httpExchange);
                    } else if (splitStrings[splitStrings.length - 1].equals("epic") && splitStrings.length == 4) {
                        getRequestEpicSubtask(taskType, httpExchange, index);
                    } else {
                        getRequestIndex(taskType, httpExchange, index);
                    }
                    break;
                case "DELETE":
                    if (index == null) {
                        deleteRequest(taskType, httpExchange);
                    } else {
                        deleteRequestIndex(taskType, httpExchange, index);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void postRequest(String taskType, HttpExchange httpExchange) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(httpExchange.getRequestBody(), DEFAULT_CHARSET);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        StringBuilder body = new StringBuilder();
        while (bufferedReader.ready()) {
            body.append(bufferedReader.readLine());
        }
        bufferedReader.close();
        streamReader.close();
        String bodyString = body.toString();
        switch (taskType) {
            case "task":
                Task task = gson.fromJson(bodyString, Task.class);
                httpTaskManager.creationTask(task);
                break;
            case "subtask":
                Subtask subtask = gson.fromJson(bodyString, Subtask.class);
                httpTaskManager.creationSubtask(subtask);
                break;
            case "epic":
                Epic epic = gson.fromJson(bodyString, Epic.class);
                httpTaskManager.creationEpic(epic);
                break;
            default:
                break;
        }
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.close();
    }

    private void getRequest(String taskType, HttpExchange httpExchange) throws IOException {
        switch (taskType) {
            case "task":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getAllTask()).getBytes());
                }
                break;
            case "subtask":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getAllSubtask()).getBytes());
                }
            case "epic":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getAllEpic()).getBytes());
                }
            case "history":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getHistory()).getBytes());
                }
                break;
            default:
                break;
        }
    }

    private void getRequestIndex(String taskType, HttpExchange httpExchange, String index) throws IOException {
        String[] splitIndex = index.split("=");
        int id = Integer.parseInt(splitIndex[1]);
        switch (taskType) {
            case "task":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getIndexTask(id)).getBytes());
                }
                break;
            case "subtask":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getIndexSubtask(id)).getBytes());
                }
                break;
            case "epic":
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(200, 0);
                    os.write(gson.toJson(httpTaskManager.getIndexEpic(id)).getBytes());
                }
                break;
            default:
                break;
        }
    }

    private void getRequestEpicSubtask(String taskType, HttpExchange httpExchange, String index) throws IOException {
        String[] splitIndex = index.split("=");
        int id = Integer.parseInt(splitIndex[1]);
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(httpTaskManager.allSubtaskEpic(id)).getBytes());
        }
    }

    private void deleteRequest(String taskType, HttpExchange httpExchange) throws IOException {
        switch (taskType) {
            case "task":
                httpTaskManager.deleteAllTask();
                break;
            case "subtask":
                httpTaskManager.deleteAllSubtask();
                break;
            case "epic":
                httpTaskManager.deleteAllEpic();
                break;
            default:
                break;
        }
        httpExchange.sendResponseHeaders(201, 0);
        httpExchange.close();
    }

    private void deleteRequestIndex(String taskType, HttpExchange httpExchange, String index) throws IOException {
        String[] splitIndex = index.split("=");
        int id = Integer.parseInt(splitIndex[1]);
        switch (taskType) {
            case "task":
                httpTaskManager.deleteTask(id);
                break;
            case "subtask":
                httpTaskManager.deleteSubtask(id);
                break;
            case "epic":
                httpTaskManager.deleteEpic(id);
                break;
            default:
                break;
        }
        httpExchange.sendResponseHeaders(201, 0);
        httpExchange.close();
    }
}
