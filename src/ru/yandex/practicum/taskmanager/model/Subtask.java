package ru.yandex.practicum.taskmanager.model;


public class Subtask extends Task {
    private Epic parent;

    public Subtask(String title, String description, int id) {
        super(title, description, id);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t}";
    }

    public Epic getParent() {
        return parent;
    }

    public void setParent(Epic parent) {
        this.parent = parent;
    }
}
