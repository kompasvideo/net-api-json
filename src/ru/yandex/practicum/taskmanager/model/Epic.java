package ru.yandex.practicum.taskmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Epic extends Task {
    private Collection<Subtask> subtasks = new ArrayList<>();

    @Override
    public String toString() {
        return "Epic{" +
                "\n\t title= " + title +
                "\n\t description= " + description +
                "\n\t id= " + id +
                "\n\t status= " + status +
                "\n\t subtasks= " + subtasks +
                "\n\t}";
    }

    public Epic(String title, String description, int id){
        super(title, description, id);
    }

    // Collection не везде подходит, у него нет метода get(index)
    public Epic(String title, String description, int id, List<Subtask> subtasks){
        super(title, description, id);
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask subtask = subtasks.get(i);
            subtask.setParent(this);
            this.subtasks.add(subtask);
        }
    }

    public Collection<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Возвращяет id всех подзадач
     * @return
     */
    public List<Integer> getSubtaskIds() {
        // Collection не везде подходит, у него нет метода get(index)
        List<Integer> ids = new ArrayList<>();
        for (Subtask subtask: subtasks){
            ids.add(subtask.getId());
        }
        return ids;
    }

}
