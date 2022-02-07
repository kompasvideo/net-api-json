package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.model.Task;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static ArrayDeque<Task> queue = new ArrayDeque<>(10);
    private final static int MAX_QUEUE = 10;

    /** Добавляет ru.yandex.practicum.taskmanager.model.Task в History */
    @Override
    public void add(Task task) {
        if (queue.size() < MAX_QUEUE) {
            queue.offer(task);
        }
        else {
            queue.poll();
            queue.offer(task);
        }
    }

    /**
     * Отображает последние просмотренные пользователем задачи
     */
    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        for (Task task: queue) {
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public void remove(Task task){

    }
}
