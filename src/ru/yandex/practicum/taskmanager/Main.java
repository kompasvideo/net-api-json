package ru.yandex.practicum.taskmanager;

import java.util.ArrayList;
import java.util.List;
import ru.yandex.practicum.taskmanager.manager.*;
import ru.yandex.practicum.taskmanager.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Manager.getDefault();
        int id = manager.hashCode();
        // Collection не везде подходит, у него нет метода get(index)
        List<Subtask> subtasks = new ArrayList<>();

        // Создаём 2 задач
        int taskId = manager.newTask(new Task("Задача 1", "Описание задачи 1", ++id));
        int task2Id = manager.newTask(new Task("Задача 2", "Описание задачи 2", ++id));

        // создание эпика с 3 подзадачами
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id)));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id)));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id)));
        int epicId = manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));

        // создание эпика без подзадач
        subtasks.clear();
        manager.newEpic(new Epic("Эпик 2","Описание эпика 2", ++id, subtasks));

        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Task task: manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nВызываем getTask()");
        System.out.println(taskId);
        manager.getTask(taskId);
        viewHisory();

        System.out.println("\nВызываем getTask()");
        System.out.println(task2Id);
        manager.getTask(task2Id);
        viewHisory();

        System.out.println("\nВызываем getTask()");
        System.out.println(task2Id);
        manager.getTask(task2Id);
        viewHisory();

        System.out.println("\nВызываем getTask()");
        System.out.println(taskId);
        manager.getTask(taskId);
        viewHisory();

        System.out.println("\nВызываем getTask()");
        /** Вызываем getEpic()*/
        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic.getId());
            manager.getEpic(epic.getId());
        }
        /** Вызываем getTask()*/
        for (Task task: manager.getAllTasks()) {
            System.out.println(task.getId());
            manager.getTask(task.getId());
        }
        /** Вызываем getSubTask()*/
        for (Subtask subtask: manager.getAllSubtasks()) {
            System.out.println(subtask.getId());
            manager.getSubtask(subtask.getId());
        }
        /** Вызываем getEpic()*/
        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic.getId());
            manager.getEpic(epic.getId());
        }
        viewHisory();
        List<Task> tasks;

        System.out.println("\nУдаляем задачу");
        System.out.println(taskId);
        manager.delTask(taskId);
        viewHisory();

        System.out.println("\nУдаляем эпик");
        System.out.println(epicId);
        manager.delEpic(epicId);
        viewHisory();
    }

    private static void viewHisory() {
        System.out.println("\nИстория");
        List<Task> tasks = Manager.getDefaultHistory().getHistory();
        for (int i = 0;  i < tasks.size(); i++) {
            System.out.print(tasks.get(i).getId());
            if (i < (tasks.size() -1)) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}
