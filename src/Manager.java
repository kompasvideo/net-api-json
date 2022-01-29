import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Subtask> subTasks;
    HashMap<Integer, Epic> epics;

    public Manager() {
        tasks = new HashMap();
        subTasks = new HashMap();
        epics = new HashMap();
    }

    // 2.1 Получение списка всех задач.
    public Collection<Task> getAllTasks(){
        return tasks.values();
    }
    public Collection<Subtask> getAllSubtasks(){
        return subTasks.values();
    }
    public Collection<Epic> getAllEpics(){
        return epics.values();
    }

    // 2.2 Удаление всех задач.
    public void delAllTasks(){
        tasks.clear();
    }
    public void delAllSubtasks(){
        subTasks.clear();
    }
    public void delAllEpics(){
        epics.clear();
    }

    // 2.3 Получение по идентификатору.
    public Task getTask(int id){
        return tasks.get(id);
    }
    public Subtask getSubtask(int id){
        return subTasks.get(id);
    }
    public Epic getEpic(int id){
        return epics.get(id);
    }

    // 2.4 Создание. Сам объект должен передаваться в качестве параметра.
    public void newTask(Task task){
        tasks.put(task.getId(), task);
    }
    public Subtask newSubtask(Subtask subtask){
        subTasks.put(subtask.getId(), subtask);
        return subtask;
    }
    public Epic newEpic(Epic epic){
        return epics.put(epic.getId(), epic);
    }

    // 2.5 Обновление. Новая версия объекта с верным идентификатор передаются в виде параметра.
    public void updateTask(Task task){

    }
    public void updateSubtask(Subtask subtask){
    }
    public void updateEpic(Epic epic){
    }

    // 2.6 Удаление по идентификатору.
    public void delTask(int guid){
        tasks.remove(guid);
    }
    public void delSubtask(int guid){
        subTasks.remove(guid);
    }
    public void delEpic(int guid){
        ArrayList<Integer> ids = getEpic(guid).getSubtaskIds();
        for (int i =0; i < ids.size(); i++) {
            subTasks.remove(ids.get(i));
        }
        epics.remove(guid);
    }

    // 3.1 Получение списка всех подзадач определённого эпика.
    public Collection<Subtask> getSubtasks(Epic epic){
        return epic.getSubtasks();
    }

    // 4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией
    // о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
    public void SetStatusTask(Task task, Status status) {
        task.setStatus(status);
    }
    public void SetStatusSubtask(Subtask subtask, Status status) {
        subtask.setStatus(status);
        SetStatusEpic(subtask);
    }

    /**
     Рассчитываем и устанавливаем статус эпика
     */
    private void SetStatusEpic(Subtask subtask) {
        boolean newStatus = false;
        boolean inProgressStatus = false;
        boolean doneStatus = false;
        for (Subtask lSubtask: getSubtasks(subtask.getParent()) ) {
            switch (lSubtask.getStatus()){
                case NEW:
                    newStatus = true;
                    break;
                case IN_PROGRESS:
                    inProgressStatus = true;
                    break;
                case DONE:
                    doneStatus = true;
                    break;
            }
        }
        if (newStatus){
            if (!inProgressStatus){
                if(!doneStatus){
                    subtask.getParent().setStatus(Status.NEW);
                }
                else{
                    subtask.getParent().setStatus(Status.IN_PROGRESS);
                }
            }
            else{
                subtask.getParent().setStatus(Status.IN_PROGRESS);
            }
        }
        else{
            if(!inProgressStatus) {
                if(doneStatus){
                    subtask.getParent().setStatus(Status.DONE);
                }
                else{
                    subtask.getParent().setStatus(Status.IN_PROGRESS);
                }
            }
            else {
                subtask.getParent().setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
