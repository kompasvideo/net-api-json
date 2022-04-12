package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.checks.CheckEpics;
import ru.yandex.practicum.taskmanager.checks.CheckHistory;
import ru.yandex.practicum.taskmanager.checks.CheckSubTasks;
import ru.yandex.practicum.taskmanager.checks.CheckTasks;
import ru.yandex.practicum.taskmanager.exceptions.ManagerSaveException;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.model.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private String fileName;

    public FileBackedTasksManager(HistoryManager historyManager, String fileName) {
        super(historyManager);
        this.fileName = fileName;
    }

    // 2.3 Получение по идентификатору.
    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    // 2.4 Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public int newTask(Task task) throws ValidationTimeException {
        int id = super.newTask(task);
        save();
        return id;
    }

    @Override
    public Subtask newSubtask(Subtask subtask) {
        Subtask subtask1 = super.newSubtask(subtask);
        save();
        return subtask1;
    }

    @Override
    public int newEpic(Epic epic) {
        int id = super.newEpic(epic);
        save();
        return id;
    }


    void save() {
        try (Writer fileWriter = new FileWriter(fileName)) {
                fileWriter.write("id,type,name,status,description,epic,startTime,duration\r\n");
            for (Task task: tasks.values()) {
                fileWriter.write(task.toString(task));
            }
            for (Epic epic1: epics.values()) {
                fileWriter.write(epic1.toString(epic1));
                for (Subtask subTask: epic1.getSubtasks()) {
                    fileWriter.write(subTask.toString(subTask));
                }
            }
            fileWriter.write("\r\n");
            fileWriter.write(historyManager.toString(historyManager));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static FileBackedTasksManager loadFromFile(File file) {
        HistoryManager historyManager = new InMemoryHistoryManager();
        String fileName = "file.csv";
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(historyManager,fileName);

        BufferedReader bufReader = null;
        try (Reader fileReader = new FileReader(fileName)) {
            bufReader = new BufferedReader(fileReader);
            bufReader.readLine();
            String str;
            String[] strArray;
            while(true) {
                str = bufReader.readLine();
                if (str == null) {
                    break;
                }
                if (str.isEmpty()) {
                    break;
                }
                strArray =  str.split(",");
                LocalDateTime startTime =  LocalDateTime.MIN;
                if (strArray[6].equals("null")) {
                    startTime = null;
                } else {
                    startTime = LocalDateTime.parse(strArray[6]);
                }
                Duration duration = Duration.ZERO;
                if (strArray[7].equals("null")) {
                    duration = null;
                } else {
                    duration = Duration.parse(strArray[7]);
                }
                switch (strArray[1]) {
                    case "TASK":
                        Task task = new Task(Integer.parseInt(strArray[0]), EnumTask.TASK,strArray[2],
                                getStatus(strArray[3]),strArray[4],startTime, duration);
                        fileBackedTasksManager.tasks.put(Integer.parseInt(strArray[0]), task);
                        break;
                    case "SUBTASK":
                        Subtask subtask = new Subtask(Integer.parseInt(strArray[0]), EnumTask.TASK,strArray[2],
                                getStatus(strArray[3]),strArray[4],Integer.parseInt(strArray[5]), startTime, duration);
                        fileBackedTasksManager.subTasks.put(Integer.parseInt(strArray[0]), subtask);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(strArray[2],strArray[4],Integer.parseInt(strArray[0]),
                                null);
                        fileBackedTasksManager.epics.put(Integer.parseInt(strArray[0]),epic);
                        break;
                }
            }
            addSubtasksToEpic(fileBackedTasksManager);
            str = bufReader.readLine();
            List<Integer> ids = HistoryManager.fromString(str);
            addTasksToHistory(ids, fileBackedTasksManager);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new ManagerSaveException("Ошибка в методе Save", e);
        }
        return fileBackedTasksManager;
    }

    static Status getStatus(String str) {
        switch (str) {
            case "NEW":
                return Status.NEW;
            case "IN_PROGRESS":
                return Status.IN_PROGRESS;
            case "DONE":
                return Status.DONE;
        }
        return Status.DONE;
    }

    /**
     * Создаёт заново историю просмотра
     * @param ids
     */
    static void addTasksToHistory(List<Integer> ids, FileBackedTasksManager fileBackedTasksManager) {
        for (int id: ids ) {
            if (fileBackedTasksManager.tasks.containsKey(id)) {
                historyManager.add(fileBackedTasksManager.tasks.get(id));
            }
            if (fileBackedTasksManager.subTasks.containsKey(id)) {
                historyManager.add(fileBackedTasksManager.subTasks.get(id));
            }
            if (fileBackedTasksManager.epics.containsKey(id)) {
                historyManager.add(fileBackedTasksManager.epics.get(id));
            }
        }
    }

    /**
     * Добавляет Subtasks в Epic
     */
    static void addSubtasksToEpic( FileBackedTasksManager fileBackedTasksManager) {
        for (Subtask subtask : fileBackedTasksManager.subTasks.values() ) {
            Epic epic = fileBackedTasksManager.getEpic(subtask.getParentId());
            epic.addSubtask(subtask);
        }
    }


    public static void main(String[] args) {
        try {
            String fileName = "file.csv";

            TaskManager manager = Manager.getDefault(fileName);
            int id = manager.hashCode();
            List<Subtask> subtasks = new ArrayList<>();

            // 1. Заведите несколько разных задач, эпиков и подзадач.
            // Создаём 2 задач
            int taskId = manager.newTask(new Task("Задача 1", "Описание задачи 1", ++id,
                    LocalDateTime.of(2022, 7, 1, 9, 0),
                    Duration.of(1, ChronoUnit.DAYS)));
            int task2Id = manager.newTask(new Task("Задача 2", "Описание задачи 2", ++id,
                    LocalDateTime.of(2022, 7, 2, 9, 0),
                    Duration.of(1, ChronoUnit.DAYS)));

            // создание эпика с 3 подзадачами
            subtasks.add(manager.newSubtask(new Subtask("Подзадача 1", "Описание подзадачи 1", ++id,
                    LocalDateTime.of(2022, 7, 3, 9, 0),
                    Duration.of(1, ChronoUnit.DAYS))));
            subtasks.add(manager.newSubtask(new Subtask("Подзадача 2", "Описание подзадачи 2", ++id,
                    LocalDateTime.of(2022, 7, 4, 9, 0),
                    Duration.of(1, ChronoUnit.DAYS))));
            subtasks.add(manager.newSubtask(new Subtask("Подзадача 3", "Описание подзадачи 3", ++id,
                    LocalDateTime.of(2022, 7, 5, 9, 0),
                    Duration.of(1, ChronoUnit.DAYS))));
            int epicId = manager.newEpic(new Epic("Эпик 1", "Описание эпика 1", ++id, subtasks));

            // создание эпика без подзадач
            subtasks.clear();
            manager.newEpic(new Epic("Эпик 2", "Описание эпика 2", ++id, subtasks));


            // 2. Запросите некоторые из них, чтобы заполнилась история просмотра.
            manager.getTask(taskId);
            manager.getEpic(epicId);

            // 3. Создайте новый FileBackedTasksManager менеджер из этого же файла.
            FileBackedTasksManager fileBackedTasksManager = null;
            File file = new File(fileName);
            fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

            // 4.Проверьте, что история просмотра восстановилась верно и все задачи, эпики, подзадачи,
            // которые были в старом, есть в новом менеджере.
            CheckTasks.chekTasks(manager, fileBackedTasksManager);
            CheckSubTasks.chekSubTasks(manager, fileBackedTasksManager);
            CheckEpics.chekEpics(manager, fileBackedTasksManager);
            CheckHistory.chekHistory(manager, fileBackedTasksManager);

            System.out.println("Выведите список задач в порядке приоритета");
            // Выведите список задач в порядке приоритета
            Collection<Task> tasks = manager.getPrioritizedTasks();
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
        catch (ValidationTimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
