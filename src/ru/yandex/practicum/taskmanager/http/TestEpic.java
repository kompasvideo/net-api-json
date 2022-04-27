package ru.yandex.practicum.taskmanager.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.taskmanager.checks.CheckEpics;
import ru.yandex.practicum.taskmanager.checks.CheckHistory;
import ru.yandex.practicum.taskmanager.checks.CheckSubTasks;
import ru.yandex.practicum.taskmanager.checks.CheckTasks;
import ru.yandex.practicum.taskmanager.exceptions.ManagerSaveException;
import ru.yandex.practicum.taskmanager.exceptions.ValidationTimeException;
import ru.yandex.practicum.taskmanager.manager.*;
import ru.yandex.practicum.taskmanager.model.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestEpic extends InMemoryTaskManager {

    private String fileName;

    public TestEpic(HistoryManager historyManager, String fileName) {
        super(historyManager);
        this.fileName = fileName;
        loadFromFile();
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


    static ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager loadFromFile(File file) {
        HistoryManager historyManager = new InMemoryHistoryManager();
        String fileName = "file.csv";
        ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager fileBackedTasksManager = new ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager(historyManager,fileName);

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
            List<Integer> ids = fromString(str);
            addTasksToHistory(ids, fileBackedTasksManager);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new ManagerSaveException("Ошибка в методе Save", e);
        }
        return fileBackedTasksManager;
    }

    void loadFromFile() {
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
                        tasks.put(Integer.parseInt(strArray[0]), task);
                        break;
                    case "SUBTASK":
                        Subtask subtask = new Subtask(Integer.parseInt(strArray[0]), EnumTask.SUBTASK,strArray[2],
                                getStatus(strArray[3]),strArray[4],Integer.parseInt(strArray[5]), startTime, duration);
                        subTasks.put(Integer.parseInt(strArray[0]), subtask);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(strArray[2],strArray[4],Integer.parseInt(strArray[0]),
                                null);
                        epics.put(Integer.parseInt(strArray[0]),epic);
                        break;
                }
            }
            addSubtasksToEpic();
            str = bufReader.readLine();
            List<Integer> ids = fromString(str);
            addTasksToHistory(ids);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new ManagerSaveException("Ошибка в методе Save", e);
        }
    }

    //    из задания на ФП спринта 5
    //    Как сохранять задачи в файл и считывать их из него
    //    Создайте enum с типами задач.
    //    Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый.
    //    Напишите метод создания задачи из строки Task fromString(String value).
    //    Напишите статические методы static String toString(HistoryManager manager) и static List<Integer>
    //    fromString(String value) для сохранения и восстановления менеджера истории из CSV.
    static List<Integer> fromString(String value) {
        String[] mas = value.split(",");
        List<Integer> list = new ArrayList<>();
        for (String str : mas) {
            list.add(Integer.parseInt(str));
        }
        return list;
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
    static void addTasksToHistory(List<Integer> ids, ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager fileBackedTasksManager) {
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

    void addTasksToHistory(List<Integer> ids) {
        for (int id: ids ) {
            if (tasks.containsKey(id)) {
                historyManager.add(tasks.get(id));
            }
            if (subTasks.containsKey(id)) {
                historyManager.add(subTasks.get(id));
            }
            if (epics.containsKey(id)) {
                historyManager.add(epics.get(id));
            }
        }
    }

    /**
     * Добавляет Subtasks в Epic
     */
    static void addSubtasksToEpic( ru.yandex.practicum.taskmanager.manager.FileBackedTasksManager fileBackedTasksManager) {
        for (Subtask subtask : fileBackedTasksManager.subTasks.values() ) {
            Epic epic = fileBackedTasksManager.getEpic(subtask.getParentId());
            epic.addSubtask(subtask);
        }
    }
    void addSubtasksToEpic() {
        for (Subtask subtask : subTasks.values() ) {
            Epic epic = getEpic(subtask.getParentId());
            epic.addSubtask(subtask);
        }
    }


    public static void main(String[] args) {
        try {
            String fileName = "file.csv";

            TaskManager manager = Manager.getDefault(fileName);
//            List<Subtask> subtasks = new ArrayList<>();
//            // создание эпика с 3 подзадачами
//            subtasks.add(manager.newSubtask(new Subtask("Подзадача 1", "Описание подзадачи 1", 1,
//                    LocalDateTime.of(2022, 7, 3, 9, 0),
//                    Duration.of(1, ChronoUnit.DAYS))));
//            subtasks.add(manager.newSubtask(new Subtask("Подзадача 2", "Описание подзадачи 2", 2,
//                    LocalDateTime.of(2022, 7, 4, 9, 0),
//                    Duration.of(1, ChronoUnit.DAYS))));
//            subtasks.add(manager.newSubtask(new Subtask("Подзадача 3", "Описание подзадачи 3", 3,
//                    LocalDateTime.of(2022, 7, 5, 9, 0),
//                    Duration.of(1, ChronoUnit.DAYS))));
//            int epicId = manager.newEpic(new Epic("Эпик 1", "Описание эпика 1", 4, subtasks));
//
//            // создание эпика без подзадач
//            subtasks.clear();
//            manager.newEpic(new Epic("Эпик 2", "Описание эпика 2", 5, subtasks));


            String response;
            Gson gson;
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = new GsonBuilder().
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            List<Epic> epics = manager.getAllEpics();
            System.out.println(epics);
            Collection<Task> tasks = manager.getAllTasks();
            System.out.println(tasks);
            response = gson.toJson(tasks);
            Collection<Subtask> subtasks = manager.getAllSubtasks();
            System.out.println(subtasks);
            response = gson.toJson(subtasks);
            //epics.
            response = gson.toJson(epics);
            System.out.println("- 1 -");
            System.out.println(response);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

