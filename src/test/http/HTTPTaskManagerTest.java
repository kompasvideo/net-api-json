package test.http;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.http.HTTPTaskManager;
import ru.yandex.practicum.taskmanager.http.KVServer;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HTTPTaskManagerTest {
    private final KVServer kvServer = new KVServer();

    private TaskManager taskManager;

    public HTTPTaskManagerTest() throws IOException {
    }

    @BeforeEach
    void start() throws URISyntaxException, IOException, InterruptedException {
        kvServer.start();
        String uri = "http://localhost:8078";
        taskManager = Manager.getDefault(uri);
    }

    @AfterEach
    void stop() {
        kvServer.stop();
    }

    @Test
    void savedTaskToServer() throws ValidationTimeException {
        Task task =  new Task("Задача 1", "Описание задачи 1", 1211888641,
                LocalDateTime.of(2022, 7, 1, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        taskManager.newTask(task);
        Assertions.assertEquals(
                task,
                ((HTTPTaskManager) taskManager).load(String.valueOf(task.getId())),
                "Задачи различаются");
    }

    @Test
    void savedSubtaskToServer() {
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1211888643,
                LocalDateTime.of(2022, 7, 3, 9, 0),
                Duration.of(1, ChronoUnit.DAYS));
        taskManager.newSubtask(subtask);
        Task task = ((HTTPTaskManager) taskManager).load(String.valueOf(subtask.getId()));
        Assertions.assertEquals(
                subtask.toString(),
                ((HTTPTaskManager) taskManager).load(String.valueOf(subtask.getId())).toString(),
                "Подзадачи разные ");

    }

    @Test
    void savedEpicToServer() {
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
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", 1211888646, subtasksTest);
        String epic1 = epic.toString();
        taskManager.newEpic(epic);
        String epic2 = ((HTTPTaskManager) taskManager).load(String.valueOf(epic.getId())).toString();
        Assertions.assertEquals(
                epic.toString(),
                ((HTTPTaskManager) taskManager).load(String.valueOf(epic.getId())).toString(),
                "Эпики разные"
        );

    }
}
