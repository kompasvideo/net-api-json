package ru.yandex.practicum.taskmanager.manager;

import java.util.ArrayDeque;
import java.util.List;
import ru.yandex.practicum.taskmanager.model.*;

public interface HistoryManager {
    /** Первый add(Task task) должен помечать задачи как просмотренные */
    void add(Task task);

    /**  а второй getHistory() — возвращать их список. */
    List<Task> getHistory();

    /** удаляет задачу из истории */
    void remove(int taskId);
}
