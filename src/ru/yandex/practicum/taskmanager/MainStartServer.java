package ru.yandex.practicum.taskmanager;

import ru.yandex.practicum.taskmanager.http.HttpTaskServer;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;

import java.io.IOException;
import java.net.URI;

public class MainStartServer {
    public static void main(String[] args) throws IOException {
        String fileName = "file.csv";
        HttpTaskServer server = new HttpTaskServer( fileName);
        server.startServer();
        URI url = URI.create(("http://localhost:8080"));
        //HttpTaskManager taskManager = new HttpTaskManager("TaskSavedBackup", url);
        //KVClient client = new KVClient(url);
    }
}
