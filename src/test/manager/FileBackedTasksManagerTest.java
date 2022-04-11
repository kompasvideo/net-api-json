package test.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.taskmanager.manager.Manager;
import ru.yandex.practicum.taskmanager.manager.TaskManager;
import ru.yandex.practicum.taskmanager.model.Epic;
import ru.yandex.practicum.taskmanager.model.Status;
import ru.yandex.practicum.taskmanager.model.Subtask;
import ru.yandex.practicum.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    public void beforeEach() {
        String fileName = "file.csv";

        manager = Manager.getDefault(fileName);
    }

    @Test
    void getAllTasksTest() {
        super.getAllTasksTest();
    }

    @Test
    void getAllSubtasksTest() {
        super.getAllSubtasksTest();
    }

    @Test
    void getAllEpicsTest() {
        super.getAllEpicsTest();
    }

    @Test
    void delAllTasksTest() {
        super.delAllTasksTest();
    }

    @Test
    void delAllSubtasksTest() {
        super.delAllSubtasksTest();
    }

    @Test
    void delAllEpicsTest() {
        super.delAllEpicsTest();
    }

    @Test
    void getTaskTest() {
        super.getTaskTest();
    }

    @Test
    void getSubtaskTest() {
        super.getSubtaskTest();
    }

    @Test
    void getEpicTest() {
        super.getEpicTest();
    }

    @Test
    void newTaskTest() {
        super.newTaskTest();
    }

    @Test
    void newSubtaskTest() {
        super.newSubtaskTest();
    }

    @Test
    void newEpicTest() {
        super.newEpicTest();
    }

    @Test
    void updateTaskTest() {
        super.updateTaskTest();
    }

    @Test
    void updateSubtaskTest() {
        super.updateSubtaskTest();
    }

    @Test
    void updateEpicTest() {
        super.updateEpicTest();
    }

    @Test
    void delTaskTest() {
        super.delTaskTest();
    }

    @Test
    public void delSubtaskTest() {
        super.delSubtaskTest();
    }

    @Test
    public void delEpicTest() {
        super.delEpicTest();
    }

    @Test
    void getSubtasksTest(){
        super.getSubtasksTest();
    }

    @Test
    void setStatusTaskTest() {
        super.setStatusTaskTest();
    }

    @Test
    void setStatusSubtaskTest() {
        super.setStatusSubtaskTest();
    }

    @Test
    void getTaskTimeTest() {
        super.getTaskTimeTest();
    }

    @Test
    void getSubtaskTimeTest() {
        super.getSubtaskTimeTest();
    }

    @Test
    void getEpicTimeTest() {
        super.getEpicTimeTest();
    }
}