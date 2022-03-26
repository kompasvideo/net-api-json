package ru.yandex.practicum.taskmanager.model;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected EnumTask enumTask;

    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
        this.enumTask = EnumTask.TASK;
    }

    public Task(int id, EnumTask enumTask, String title, Status status, String description ) {
        this.id = id;
        this.enumTask = enumTask;
        this.title = title;
        this.status = status;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return "Task{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Напишите метод сохранения задачи в строку String toString(Task task) или переопределите базовый.
    public String toString(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s\r\n", task.id,task.enumTask, task.title,task.status,
                task.description,"");
    }

    // Напишите метод создания задачи из строки Task fromString(String value).
    public Task fromString(String value) {
        String[] strArray = value.split(",");
        int id = -1;
        try
        {
            id = Integer.parseInt(strArray[0].trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        EnumTask enumTask = EnumTask.TASK;
        switch (strArray[1]) {
            case "TASK":
                enumTask = EnumTask.TASK;
                break;
            case "SUBTASK":
                enumTask = EnumTask.SUBTASK;
                break;
            case "EPIC":
                enumTask = EnumTask.EPIC;
                break;
            default:
                System.out.println("Ошибка 1 в Task: public Task fromString(String value) ");
        }
        Status status = Status.NEW;
        switch (strArray[3]) {
            case "NEW":
                status = Status.NEW;
                break;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "EPIC":
                status = Status.DONE;
                break;
            default:
                System.out.println("Ошибка 2 в Task: public Task fromString(String value) ");
        }
        return new Task(id, enumTask, strArray[2], status,strArray[4]);
    }

}
