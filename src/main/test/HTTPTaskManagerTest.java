package main.test;

import com.google.gson.Gson;
import main.Server.HttpTaskServer;
import main.Server.KVServer;
import main.control.HTTPTaskManager;
import main.target.Epic;
import main.target.Subtask;
import main.target.Task;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {
    static KVServer kvServer;
    Gson gson;
    HttpTaskServer httpTaskServer;
    HttpClient client;

    @BeforeEach
    void setUp() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = new HTTPTaskManager();
        //   taskManager.loadFile("http://localhost:8078/");
        httpTaskServer = new HttpTaskServer(taskManager);
        initTask();
        initEpic();
        gson = new Gson();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void setDown() throws IOException {
        kvServer.stop();
        httpTaskServer.stopHTTPServer();
    }

    @Override
    @Test
    void getAllTaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        Task task1 = new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task2 = new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        String print = "Список всех задач \n" + task1 + "\n" + task2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Override
    @Test
    void getAllSubtaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        Subtask subtask1 = new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Subtask subtask2 = new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(5);
        String print = "Список всех подзадач \n" + subtask1 + "\n" + subtask2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Override
    @Test
    void getAllEpicTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        String print = "Список всех эпиков \n" + "3,Приготовить чай,NEW, PT146817H38M,22.12.05 16:12" + "\n" + "7,Зарядить телефон,NEW" + "\n";
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Override
    @Test
    void getIndexTaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task = gson.fromJson(response.body(), Task.class);
        url = URI.create("http://localhost:8080/tasks/task/?id=5");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task2 = gson.fromJson(response.body(), Task.class);
        Task taskTest = new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        taskTest.setIndex(1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, taskTest),
                () -> Assertions.assertNull(task2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(task, "Задача на возвращаются.")
        );
    }

    @Override
    @Test
    void getIndexSubtaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=4");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = gson.fromJson(response.body(), Subtask.class);
        url = URI.create("http://localhost:8080/tasks/subtask/?id=10");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask2 = gson.fromJson(response.body(), Subtask.class);
        Subtask subtaskTest = new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtaskTest.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtaskTest),
                () -> Assertions.assertNull(subtask2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются."),
                () -> Assertions.assertNotNull(subtask.getIndexEpic(), "Индекс не эпика возвращается.")
        );
    }

    @Override
    @Test
    void getIndexEpicTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic?id=7");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic = gson.fromJson(response.body(), Epic.class);
        url = URI.create("http://localhost:8080/tasks/epic/?id=10");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic2 = gson.fromJson(response.body(), Epic.class);
        Epic epicTest = new Epic("Зарядить телефон");
        epicTest.setIndex(7);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epicTest),
                () -> Assertions.assertNull(epic2, "Задачи нe должна вернуться."),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void deleteAllTaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        url = URI.create("http://localhost:8080/tasks/task/");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        Assertions.assertEquals(task, "Список всех задач \n");
    }

    @Override
    @Test
    void deleteAllSubtaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        url = URI.create("http://localhost:8080/tasks/subtask/");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        Assertions.assertEquals(task, "Список всех подзадач \n");
    }

    @Override
    @Test
    void deleteAllEpicTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        url = URI.create("http://localhost:8080/tasks/epic/");
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        Assertions.assertEquals(task, "Список всех эпиков \n");
    }

    @Override
    @Test
    void allSubtaskEpicTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask[] subtasks = gson.fromJson(response.body(), Subtask[].class);
        List<Subtask> listSubtask = List.of(subtasks);
        Subtask subtask2 = new Subtask("Выбрать чай", "Добавить заварку", 3, 55, "22.09.22 00:55");
        subtask2.setIndex(5);
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, listSubtask.size()),
                () -> Assertions.assertEquals(listSubtask.get(1), subtask2),
                () -> Assertions.assertTrue(listSubtask.contains(subtask2)),
                () -> Assertions.assertNotNull(listSubtask, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void creationTaskTest() throws IOException, InterruptedException {
        Task taskTest = new Task("Поездка44", "Упаковать кошку44", 55, "22.10.95 10:45");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task = taskManager.getIndexTask(44);
        Task task1 = new Task("Поездка44", "Упаковать кошку44", 55, "22.10.95 10:45");
        task1.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, task1),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void creationSubtaskTest() throws IOException, InterruptedException {
        Subtask taskTest = new Subtask("Вскипятить воду44", "Поставить чайник44", 3, 55, "22.12.44 16:12");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = taskManager.getIndexSubtask(44);
        Subtask subtask1 = new Subtask("Вскипятить воду44", "Поставить чайник44", 3, 55, "22.12.44 16:12");
        subtask1.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtask1),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void creationEpicTest() throws IOException, InterruptedException {
        Epic taskTest = new Epic("Зарядить телефон");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).version(HttpClient.Version.HTTP_1_1).POST(body).header("Content-Type", "application/json").build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic = taskManager.getIndexEpic(44);
        Epic epic2 = new Epic("Зарядить телефон");
        epic2.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epic2),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void updateTaskTest() throws IOException, InterruptedException {
        Task taskTest = new Task("Поездка44", "Упаковать кошку44", 55, "22.10.95 10:45");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task = taskManager.getIndexTask(44);
        Task task1 = new Task("Поездка44", "Упаковать кошку44", 55, "22.10.95 10:45");
        task1.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(task, task1),
                () -> Assertions.assertNotNull(task, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void updateSubtaskTest() throws IOException, InterruptedException {
        Subtask taskTest = new Subtask("Вскипятить воду44", "Поставить чайник44", 3, 55, "22.12.44 16:12");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = taskManager.getIndexSubtask(44);
        Subtask subtask1 = new Subtask("Вскипятить воду44", "Поставить чайник44", 3, 55, "22.12.44 16:12");
        subtask1.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(subtask, subtask1),
                () -> Assertions.assertNotNull(subtask, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void updateEpicTest() throws IOException, InterruptedException {
        Epic taskTest = new Epic("Зарядить телефон");
        taskTest.setIndex(44);
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        String json = gson.toJson(taskTest);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).header("Content-Type", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic = taskManager.getIndexEpic(44);
        Epic epic2 = new Epic("Зарядить телефон");
        epic2.setIndex(44);
        Assertions.assertAll(
                () -> Assertions.assertEquals(epic, epic2),
                () -> Assertions.assertNotNull(epic, "Задача не возвращаются.")
        );
    }

    @Override
    @Test
    void deleteTaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = taskManager.getAllTask();
        Task task2 = new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        String print = "Список всех задач \n" + task2 + "\n";
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(task, print),
                () -> Assertions.assertNull(taskManager.getIndexTask(1), "Задачи нe должна вернуться.")
        );
    }

    @Override
    @Test
    void deleteSubtaskTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=5");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Subtask> tasks = taskManager.allSubtaskEpic(3);
        Subtask subtask1 = new Subtask("Вскипятить воду", "Поставить чайник", 3, 55, "22.12.05 16:12");
        subtask1.setIndex(4);
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexSubtask(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertEquals(1, tasks.size()),
                () -> Assertions.assertTrue(tasks.contains(subtask1)),
                () -> Assertions.assertNotNull(tasks, "Задача не возвращаются.")
        );
        ;
    }

    @Override
    @Test
    void deleteEpicTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Subtask> tasks = taskManager.allSubtaskEpic(3);
        Assertions.assertAll(
                () -> Assertions.assertNull(taskManager.getIndexEpic(3), "Задачи нe должна вернуться."),
                () -> Assertions.assertNull(tasks, "Задачи нe должна вернуться.")
        );
    }

    @Test
    void HistoryTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
    }

    @Override
    @Test
    void getHistoryEmptyTest() throws IOException, InterruptedException {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexTask(2);
        taskManager.getIndexTask(1);
        taskManager.deleteAllEpic();
        taskManager.deleteAllTask();
        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task[] history = gson.fromJson(response.body(), Task[].class);
        List<Task> taskHistory = List.of(history);
        Assertions.assertEquals(0, taskHistory.size());
    }

    @Override
    @Test
    void getHistoryDuplicationTest() throws IOException, InterruptedException {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexTask(2);
        taskManager.getIndexTask(1);
        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task[] history = gson.fromJson(response.body(), Task[].class);
        List<Task> taskHistory = List.of(history);
        boolean dublication = false;
        for (int i = 0; i < taskHistory.size(); i++) {
            for (int j = i + 1; j < taskHistory.size(); j++) {
                if (taskHistory.get(i) == taskHistory.get(j)) {
                    dublication = true;
                }
            }
        }
        Assertions.assertFalse(dublication, "Повторений не должно быть");
    }

    @Override
    @Test
    void getHistoryDeleteTest() throws IOException, InterruptedException {
        taskManager.getIndexTask(1);
        taskManager.getIndexEpic(7);
        taskManager.getIndexSubtask(5);
        taskManager.getIndexSubtask(4);
        taskManager.getIndexEpic(3);
        taskManager.getIndexTask(2);
        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task[] history = gson.fromJson(response.body(), Task[].class);
        List<Task> taskHistory = List.of(history);
        Task task1 = new Task("Поездка", "Упаковать кошку", 55, "22.10.22 10:45");
        task1.setIndex(1);
        Task task2 = new Task("Переезд", "Собрать коробки", 55);
        task2.setIndex(2);
        Assertions.assertEquals(taskHistory.get(0), task1);
        Assertions.assertEquals(taskHistory.get(taskHistory.size() - 1), task2);
    }

    @Test
    void getPrioritizedTasksTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task = gson.fromJson(response.body(), String.class);
        String print = "[4, Вскипятить воду, NEW, Поставить чайник, 3, PT55M, 22.12.05 16:12, 5, Выбрать чай, NEW, Добавить заварку, 3, PT55M, 22.09.22 00:55, 1, Поездка, NEW, Упаковать кошку, PT55M, 22.10.22 10:45, 2, Переезд, NEW, Собрать коробки, PT55M]";
        Assertions.assertEquals(print, task);
    }
}
