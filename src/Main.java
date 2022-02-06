import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Manager.getDefault();
        int id = manager.hashCode();
        ArrayList<Subtask> subtasks = new ArrayList<>();

        // Создаём 7 задач
        manager.newTask(new Task("Задача 1", "Описание задачи 1", ++id));
        manager.newTask(new Task("Задача 2", "Описание задачи 2", ++id));
        manager.newTask(new Task("Задача 3", "Описание задачи 3", ++id));
        manager.newTask(new Task("Задача 4", "Описание задачи 4", ++id));
        manager.newTask(new Task("Задача 5", "Описание задачи 5", ++id));
        manager.newTask(new Task("Задача 6", "Описание задачи 6", ++id));
        manager.newTask(new Task("Задача 7", "Описание задачи 7", ++id));

        // создание эпика с 2 подзадачами
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 1","Описание подзадачи 1",++id)));
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 2","Описание подзадачи 2",++id)));
        manager.newEpic(new Epic("Эпик 1","Описание эпика 1", ++id, subtasks));

        // создание эпика с 1 подзадачей
        subtasks.clear();
        subtasks.add(manager.newSubtask(new Subtask("Подзадача 3","Описание подзадачи 3",++id)));
        manager.newEpic(new Epic("Эпик 2","Описание эпика 2", ++id, subtasks));

        for (Epic epic: manager.getAllEpics()) {
            ArrayList<Subtask> ar = epic.getSubtasks();
            System.out.println(ar.size());
        }

        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Task task: manager.getAllTasks()) {
            System.out.println(task);
        }
        for (Subtask subtask: manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

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
