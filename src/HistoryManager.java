import java.util.ArrayDeque;
import java.util.List;

public interface HistoryManager {
    /** Первый add(Task task) должен помечать задачи как просмотренные */
    void add(Task task);

    /**  а второй getHistory() — возвращать их список. */
    List<Task> getHistory();
}
