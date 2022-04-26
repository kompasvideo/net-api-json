package test.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.http.HttpTaskServer;
import ru.yandex.practicum.taskmanager.http.LocalDateTimeAdapter;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskManagerHttpServerTest {
    @Test
    void GetAllTaskTest() throws IOException {
        String fileName = "file2.csv";
        HttpTaskServer server = new HttpTaskServer( fileName);
        server.startServer();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task task1 =  new Task("Задача 1", "Описание задачи 1", 1211888641,
                LocalDateTime.of(2022, 7, 1, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        Task task2 =new Task("Задача 2", "Описание задачи 2", 1211888642,
                LocalDateTime.of(2022, 7, 2, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        Collection<Task> tasks = returnTaskList(url);
        assertEquals(2, tasks.size());
        for (Task task : tasks) {
            if (task.getId() == task1.getId()) {
                assertEquals(task.getTitle(), task1.getTitle());
                assertEquals(task.getDescription(), task1.getDescription());
                assertEquals(task.getStatus(), task1.getStatus());
                assertEquals(task.getStartTime().toString(), task1.getStartTime().toString());
                assertEquals(task.getDuration(), task1.getDuration());
            } else if (task.getId()== task2.getId()) {
                assertEquals(task.getTitle(), task2.getTitle());
                assertEquals(task.getDescription(), task2.getDescription());
                assertEquals(task.getStatus(), task2.getStatus());
                assertEquals(task.getStartTime().toString(), task2.getStartTime().toString());
                assertEquals(task.getDuration(), task2.getDuration());
            }
        }
        server.serverStop();
    }

    @Test
    void GetAllSubTaskTest() throws IOException {
        String fileName = "file2.csv";
        HttpTaskServer server = new HttpTaskServer( fileName);
        server.startServer();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1211888643,
                LocalDateTime.of(2022, 7, 3, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 1211888644,
                LocalDateTime.of(2022, 7, 4, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", 1211888645,
                LocalDateTime.of(2022, 7, 5, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        Collection<Subtask> subtasks = returnSubtaskList(url);
        assertEquals(3, subtasks.size());
        for (Subtask subtask : subtasks) {
            if (subtask.getId() == subtask1.getId()) {
                assertEquals(subtask.getTitle(), subtask1.getTitle());
                assertEquals(subtask.getDescription(), subtask1.getDescription());
                assertEquals(subtask.getStatus(), subtask1.getStatus());
                assertEquals(subtask.getStartTime().toString(), subtask1.getStartTime().toString());
                assertEquals(subtask.getDuration(), subtask1.getDuration());
            } else if (subtask.getId()== subtask2.getId()) {
                assertEquals(subtask.getTitle(), subtask2.getTitle());
                assertEquals(subtask.getDescription(), subtask2.getDescription());
                assertEquals(subtask.getStatus(), subtask2.getStatus());
                assertEquals(subtask.getStartTime().toString(), subtask2.getStartTime().toString());
                assertEquals(subtask.getDuration(), subtask2.getDuration());
            } else if (subtask.getId()== subtask3.getId()) {
                assertEquals(subtask.getTitle(), subtask3.getTitle());
                assertEquals(subtask.getDescription(), subtask3.getDescription());
                assertEquals(subtask.getStatus(), subtask3.getStatus());
                assertEquals(subtask.getStartTime().toString(), subtask3.getStartTime().toString());
                assertEquals(subtask.getDuration(), subtask3.getDuration());
            }
        }
        server.serverStop();
    }

    @Test
    void GetAllEpicsTest() throws IOException {
        String fileName = "file2.csv";
        HttpTaskServer server = new HttpTaskServer( fileName);
        server.startServer();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        List<Subtask> subtasksTest = new ArrayList<>();
        subtasksTest.add(new Subtask("Подзадача 1", "Описание подзадачи 1", 1211888643,
                LocalDateTime.of(2022, 7, 3, 9, 0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasksTest.add(new Subtask("Подзадача 2", "Описание подзадачи 2", 1211888644,
                LocalDateTime.of(2022, 7, 4, 9, 0),
                Duration.of(1, ChronoUnit.DAYS)));
        subtasksTest.add(new Subtask("Подзадача 3", "Описание подзадачи 3", 1211888645,
                LocalDateTime.of(2022, 7, 5, 9, 0),
                Duration.of(1, ChronoUnit.DAYS)));
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", 1211888646, subtasksTest);

        // создание эпика без подзадач
        subtasksTest.clear();
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", 121188847, subtasksTest);
        Collection<Epic> epics = returnEpicsList(url);
        assertEquals(2, epics.size());
        for (Epic epic : epics) {
            if (epic.getId() == epic1.getId()) {
                assertEquals(epic.getTitle(), epic1.getTitle());
                assertEquals(epic.getDescription(), epic1.getDescription());
                assertEquals(epic.getStatus(), epic1.getStatus());
                assertEquals(epic.getStartTime().toString(), epic1.getStartTime().toString());
                assertEquals(epic.getDuration(), epic1.getDuration());
            } else if (epic.getId()== epic2.getId()) {
                assertEquals(epic.getTitle(), epic2.getTitle());
                assertEquals(epic.getDescription(), epic2.getDescription());
                assertEquals(epic.getStatus(), epic2.getStatus());
                assertEquals(epic.getStartTime().toString(), epic2.getStartTime().toString());
                assertEquals(epic.getDuration(), epic2.getDuration());
            }
        }
        server.serverStop();
    }

    private Collection<Task> returnTaskList(URI url) {
        Collection<Task> tasks = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).
                GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            tasks = gson.fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
            }.getType());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + url + "', возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return tasks;
    }

    private Collection<Subtask> returnSubtaskList(URI url) {
        Collection<Subtask> subtasks = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).
                GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            subtasks = gson.fromJson(response.body(), new TypeToken<ArrayList<Subtask>>() {
            }.getType());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + url + "', возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return subtasks;
    }

    private Collection<Epic> returnEpicsList(URI url) {
        Collection<Epic> epics = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).
                GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            epics = gson.fromJson(response.body(), new TypeToken<ArrayList<Epic>>() {
            }.getType());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + url + "', возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return epics;
    }
}
