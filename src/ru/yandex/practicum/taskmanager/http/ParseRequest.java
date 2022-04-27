package ru.yandex.practicum.taskmanager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.taskmanager.manager.HistoryManager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class ParseRequest {
    static String response;
    static Gson gson;
    static GsonBuilder gsonBuilder = new GsonBuilder();

    public static String parseGetRequest(TaskManager manager, String query, String path) {
        gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        switch (path) {
            case "/tasks/task/":
                Collection<Task> tasks = manager.getAllTasks();
                response = gson.toJson(tasks);
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Task task = manager.getTask(id);
                    response = gson.toJson(task);
                } else {
                    response = "Нет такой задачи ";
                }
                break;
            case "/tasks/subtask/":
                Collection<Subtask> subtasks = manager.getAllSubtasks();
                response = gson.toJson(subtasks);
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Subtask subtask = manager.getSubtask(id);
                    response = gson.toJson(subtask);
                } else {
                    response = "Нет такой подзадачи ";
                }
                break;
            case "/tasks/epic/":
                List<Epic> epics = manager.getAllEpics();
                response = gson.toJson(epics);
                if (query != null) {
                    String[] mas = query.split("=");
                    int id = Integer.parseInt(mas[1]);
                    Epic epic = manager.getEpic(id);
                    response = gson.toJson(epic);
                } else {
                    response = "Нет такой подзадачи ";
                }
                break;
            case "/tasks/history":
                List<Task> tasksH = manager.getHistoryManager().getHistory();
                response =gson.toJson(tasksH);
                break;
            case "/tasks/":
                Collection<Task> tasksC =  manager.getPrioritizedTasks();
                response = gson.toJson(tasksC);
                break;
            default:
                if (path.startsWith("/tasks/task/?id=")) {
                    String[] mas = path.split("=");
                    if (mas.length == 2) {
                        if (mas[1].length() > 0 ) {
                            Task task = manager.getTask(Integer.parseInt(mas[1]));
                            response = gson.toJson(task);
                        }
                    }
                }
                break;
        }
        return response;
    }
    public static String parsePostRequest(TaskManager manager,InputStream body, String path) {

        return "";
    }
    public static String parseDeleteRequest(TaskManager manager, String query, String path) {

        return "";
    }
}
