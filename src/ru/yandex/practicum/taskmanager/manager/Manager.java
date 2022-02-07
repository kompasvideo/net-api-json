package ru.yandex.practicum.taskmanager.manager;


public class Manager  {
    private static InMemoryTaskManager inMemoryTaskManager;
    public static HistoryManager historyManager;

    /** Метод getDefault() будет без параметров. Он должен возвращать объект-менеджер,
     * поэтому типом его возвращаемого значения будет ru.yandex.practicum.taskmanager.manager.TaskManager. */
    public static TaskManager getDefault(){
        historyManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager(historyManager);
        return inMemoryTaskManager;
    }

    /** Добавьте в служебный класс Managers статический метод ru.yandex.practicum.taskmanager.manager.HistoryManager getDefaultHistory().
     * Он должен возвращать объект InMemoryHistoryManager — историю просмотров. */
    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
