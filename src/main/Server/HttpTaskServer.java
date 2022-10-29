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
    private static Gson gson = new Gson();
    private static TaskManager httpTaskManager;
    static HttpServer httpServer;

    public HttpTaskServer(TaskManager httpTaskManager) throws IOException {
        createHTTPServer();
        this.httpTaskManager = httpTaskManager;
    }

    public static void createHTTPServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stopHTTPServer() throws IOException {
        this.httpServer.stop(0);
    }

    static class TasksHandler extends InMemoryTaskManager implements HttpHandler {
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
                            System.out.println(123);
                            break;
                    }
                    httpExchange.sendResponseHeaders(200, 0);
                    httpExchange.close();
                    break;
                case "GET":
                    switch (taskType) {
                        case "task":
                            if (index == null) {
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getAllTask()).getBytes());
                                }
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getIndexTask(id)).getBytes());
                                }
                            }
                            break;
                        case "subtask":
                            if (index == null) {
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getAllSubtask()).getBytes());
                                }
                            } else if (splitStrings[splitStrings.length - 1].equals("epic") && splitStrings.length == 4) {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.allSubtaskEpic(id)).getBytes());
                                }
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getIndexSubtask(id)).getBytes());
                                }
                            }
                            break;
                        case "epic":
                            if (index == null) {
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getAllEpic()).getBytes());
                                }
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                try (OutputStream os = httpExchange.getResponseBody()) {
                                    httpExchange.sendResponseHeaders(200, 0);
                                    os.write(gson.toJson(httpTaskManager.getIndexEpic(id)).getBytes());
                                }
                            }
                            break;
                        case "history":
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                httpExchange.sendResponseHeaders(200, 0);
                                os.write(gson.toJson(httpTaskManager.getHistory()).getBytes());
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "DELETE":
                    switch (taskType) {
                        case "task":
                            if (index == null) {
                                httpTaskManager.deleteAllTask();
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                httpTaskManager.deleteTask(id);
                            }
                            break;
                        case "subtask":
                            if (index == null) {
                                httpTaskManager.deleteAllSubtask();
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                httpTaskManager.deleteSubtask(id);
                            }
                            break;
                        case "epic":
                            if (index == null) {
                                httpTaskManager.deleteAllEpic();
                            } else {
                                String[] splitIndex = index.split("=");
                                int id = Integer.parseInt(splitIndex[1]);
                                httpTaskManager.deleteEpic(id);
                            }
                            break;
                        default:
                            break;
                    }
                    httpExchange.sendResponseHeaders(201, 0);
                    httpExchange.close();
                    break;
                default:
                    break;
            }
        }
    }
}
