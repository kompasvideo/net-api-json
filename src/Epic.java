import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtasks = new ArrayList<>();

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

    public Epic(String title, String description, int id)
    {
        super(title, description, id);
    }

    public Epic(String title, String description, int id, ArrayList<Subtask> subtasks)
    {
        super(title, description, id);
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask subtask = subtasks.get(i);
            subtask.setParent(this);
            this.subtasks.add(subtask);
        }
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Возвращяет id всех подзадач
     * @return
     */
    public ArrayList<Integer> getSubtaskIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Subtask subtask: subtasks){
            ids.add(subtask.getId());
        }
        return ids;
    }

}
