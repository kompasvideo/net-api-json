package ru.yandex.practicum.taskmanager.http;

import ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager;
import ru.yandex.practicum.taskmanager.manager.HistoryManager;

public class HTTPTaskManager extends FileBackedTasksManager {
    public HTTPTaskManager(HistoryManager historyManager, String url) {
        super(historyManager, url);

    }


}
