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

    // Напишите статические методы static String toString(HistoryManager manager)
    static String toString(HistoryManager manager) {
        String str ="";
        Node<Task> first = null;
        for (Node<Task> node: map.values()) {
            if (node.prev == null) {
                first = node;
                break;
            }
        }
        if (first == null) {
            return str;
        }
        Node<Task> next = first;
        while (true) {
            if (str.equals("")) {
                str += next.item.getId();
            } else {
                str += "," + next.item.getId();
            }
            next = next.next;
            if (next == null) {
                break;
            }
        }
        return str;
    }

    // и static List<Integer> fromString(String value) для сохранения и восстановления менеджера истории из CSV.
    static List<Integer> fromString(String value) {
        String[] mas = value.split(",");
        List<Integer> list = new ArrayList<>();
        try {
            for (String str : mas) {
                list.add(Integer.parseInt(str));
            }
        } catch (Exception ex) {
            System.out.println("Ошибка 1");
            ex.printStackTrace();
        }
        return list;
    }

}
