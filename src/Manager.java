public class Manager  {
    private static InMemoryTaskManager inMemoryTaskManager;

    /** Метод getDefault() будет без параметров. Он должен возвращать объект-менеджер,
     * поэтому типом его возвращаемого значения будет TaskManager. */
    public static TaskManager getDefault(){
        inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    /** Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().
     * Он должен возвращать объект InMemoryHistoryManager — историю просмотров. */
    public static HistoryManager getDefaultHistory() {
        return inMemoryTaskManager;
    }
}
