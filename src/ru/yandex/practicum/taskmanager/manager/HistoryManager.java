package ru.yandex.practicum.taskmanager.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.practicum.taskmanager.model.*;

public interface HistoryManager {
    /** Первый add(Task task) должен помечать задачи как просмотренные */
    void add(Task task);

    /**  а второй getHistory() — возвращать их список. */
    // List оставлен по подсказке в задании
    List<Task> getHistory();

    /** удаляет задачу из истории */
    void remove(int taskId);

    Map<Integer, Node<Task>> map = new HashMap<>();


    String toString(HistoryManager manager);

    // и static List<Integer> fromString(String value) для сохранения и восстановления менеджера истории из CSV.
    static List<Integer> fromString(String value) {
        String[] mas = value.split(",");
        List<Integer> list = new ArrayList<>();
        for (String str : mas) {
            list.add(Integer.parseInt(str));
        }
        return list;
    }

}
