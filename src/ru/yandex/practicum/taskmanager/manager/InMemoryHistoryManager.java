package ru.yandex.practicum.taskmanager.manager;

import ru.yandex.practicum.taskmanager.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{


    private Map<Integer, Node<Task>> map = new HashMap<>();

    transient int size = 0;

    /**
     * Pointer to first node.
     */
    transient Node<Task> first;

    /**
     * Pointer to last node.
     */
    transient Node<Task> last;
    /**
     * Добавляет Task в History
     * */
    @Override
    public void add(Task task) {
        linkLast(task);
    }


    void linkLast(Task e) {
        if (! map.containsKey(((Task)e).getId())) {
            addNode(e);
        } else {
            Node<Task> old = map.get(((Task)e).getId());
            removeNode(old);
            addNode(e);
            //old.item = e;
        }
    }

    private void addNode(Task e) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        map.put(((Task) e).getId(), last);
    }

    /**
     * Отображает последние просмотренные пользователем задачи
     */
    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> node = first;
        while(node != null) {
            tasks.add(node.item);
            node = node.next;
        }
        return tasks;
    }

    @Override
    public void remove(int taskId){
        Node<Task> old = map.get(taskId);
        removeNode(old);
    }

    boolean removeNode(Node<Task> e) {
        final Node<Task> prev = e.prev;
        final Node<Task> next = e.next;
        if (next != null) {
            next.prev = prev;
        } else {
            last = prev;
        }
        if (prev != null) {
            prev.next = next;
        } else {
            first = next;
        }
        //e = null;
        return true;
    }
}
