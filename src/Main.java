import java.util.ArrayList;

public class Main {
    private static Manager manager;
    private static ArrayList<Subtask> subtasks = new ArrayList<>();

    public static void main(String[] args) {

        manager = new Manager();
        int id = manager.hashCode();

        // Создаём 2 задачи
        manager.newTask(new Task("Задача 1", "Описание задачи 1", ++id));
        manager.newTask(new Task("Задача 2", "Описание задачи 2", ++id));

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

        System.out.println("\nМеняем статус задач на IN_PROGRESS");
        for (Task task: manager.getAllTasks()) {
            manager.SetStatusTask(task, Status.IN_PROGRESS);
        }
        System.out.println("\nНовый список задач с новым статусом");
        for (Task task: manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nМеняем статус подзадач на DONE");
        for (Subtask subtask: manager.getAllSubtasks()) {
            manager.SetStatusSubtask(subtask, Status.DONE);
        }
        System.out.println("\nНовый список подзадач и эпиков с новым статусом");
        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask: manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("\nУдаляем задачу");
        for (Task task: manager.getAllTasks()) {
            System.out.println(task);
            manager.delTask(task.id);
            break;
        }

        System.out.println("\nУдаляем эпик");
        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic);
            manager.delEpic(epic.id);
            break;
        }

        System.out.println("\nНовый список задач, эпиков и подзадач");
        for (Epic epic: manager.getAllEpics()) {
            System.out.println(epic);
        }
        for (Task task: manager.getAllTasks()) {
            System.out.println(task);
        }
        for (Subtask subtask: manager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }
}
