import java.util.*;

public class InMemoryTaskManager implements TaskManager, HistoryManager {
    private HashMap<Integer, Task> tasks = new HashMap();
    private HashMap<Integer, Subtask> subTasks = new HashMap();
    private HashMap<Integer, Epic> epics = new HashMap();
    private static ArrayDeque<Task> queue = new ArrayDeque<>(10);
    private final static int MAX_QUEUE = 10;

    public InMemoryTaskManager() {
    }

    // 2.1 Получение списка всех задач.
    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subTasks.values();
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    // 2.2 Удаление всех задач.
    @Override
    public void delAllTasks() {
        tasks.clear();
    }

    @Override
    public void delAllSubtasks() {
        subTasks.clear();
    }

    @Override
    public void delAllEpics() {
        epics.clear();
    }

    // 2.3 Получение по идентификатору.
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subTask = subTasks.get(id);
        add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        add(epic);
        return epic;
    }

    // 2.4 Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void newTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Subtask newSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
        return subtask;
    }

    @Override
    public Epic newEpic(Epic epic) {
        return epics.put(epic.getId(), epic);
    }

    // 2.5 Обновление. Новая версия объекта с верным идентификатор передаются в виде параметра.
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // 2.6 Удаление по идентификатору.
    @Override
    public void delTask(int guid) {
        tasks.remove(guid);
    }

    @Override
    public void delSubtask(int guid) {
        subTasks.remove(guid);
    }

    @Override
    public void delEpic(int guid) {
        ArrayList<Integer> ids = getEpic(guid).getSubtaskIds();
        for (int i = 0; i < ids.size(); i++) {
            subTasks.remove(ids.get(i));
        }
        epics.remove(guid);
    }

    // 3.1 Получение списка всех подзадач определённого эпика.
    @Override
    public Collection<Subtask> getSubtasks(Epic epic) {
        return epic.getSubtasks();
    }

    // 4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией
    // о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
    @Override
    public void setStatusTask(Task task, Status status) {
        task.setStatus(status);
    }

    @Override
    public void setStatusSubtask(Subtask subtask, Status status) {
        subtask.setStatus(status);
        setStatusEpic(subtask);
    }

    /**
     * Рассчитываем и устанавливаем статус эпика
     */
    void setStatusEpic(Subtask subtask) {
        Epic epic = subtask.getParent();
        int newStatus = 0;
        int doneStatus = 0;
        int count = getSubtasks(epic).size();
        for (Subtask lSubtask : getSubtasks(epic)) {
            switch (lSubtask.getStatus()) {
                case NEW:
                    newStatus++;
                    break;
                case DONE:
                    doneStatus++;
                    break;
            }
        }
        if (count == 0) {
            epic.setStatus(Status.NEW);
        } else if (newStatus == count) {
            epic.setStatus(Status.NEW);
        } else if (doneStatus == count) {
            epic.setStatus(Status.DONE);
        } else epic.setStatus(Status.IN_PROGRESS);
    }

    /**
     * Отображает последние просмотренные пользователем задачи
     */
    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        for (Task task: queue) {
            tasks.add(task);
        }
        return tasks;
    }

    /** Добавляет Task в History */
    @Override
    public void add(Task task) {
        if (queue.size() < MAX_QUEUE) {
            queue.offer(task);
        }
        else {
            queue.poll();
            queue.offer(task);
        }
    }
}