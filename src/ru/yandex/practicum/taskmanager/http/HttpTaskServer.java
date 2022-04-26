package ru.yandex.practicum.taskmanager.http;
import ru.yandex.practicum.taskmanager.checks.CheckEpics;
import ru.yandex.practicum.taskmanager.checks.CheckHistory;
import ru.yandex.practicum.taskmanager.checks.CheckSubTasks;
import ru.yandex.practicum.taskmanager.checks.CheckTasks;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.*;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Status;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;

public class HttpTaskServer  extends FileBackedTasksManager {
    private static final int PORT = 8080;
    private HttpServer server;
    //private final KVServer kvServer;
    static TaskManager manager;

    public HttpTaskServer( String fileName) throws IOException {
        super(new InMemoryHistoryManager(),fileName);
        manager = this;
        server = HttpServer.create();
        //kvServer = new KVServer();
    }

    public void startServer() throws IOException {
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        server.start();
       // kvServer.start();
        System.out.println("Cервер открыт на порту " + PORT);
    }

    public void serverStop(){
        server.stop(0);
    }

    protected static TaskManager getManager() {
        return manager;
    }
}
