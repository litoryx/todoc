package com.cleanup.todoc;

import android.content.Context;

import com.cleanup.todoc.InterfaceDAO.SaveMyProjectDatabase;
import com.cleanup.todoc.InterfaceDAO.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TaskDAOTestI {

    private TaskDAO taskDAO;
    private SaveMyProjectDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SaveMyProjectDatabase.class).build();
        taskDAO = db.mTaskDAO();
        Task task = new Task(1,0,"sdvff",0);
        db.mTaskDAO().insertTasks(task);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeTaskAndReadInList() throws Exception {
        Task task = new Task(0,1,"Linge",2);
        db.mTaskDAO().insertTasks(task);
        List<Task> byTask = LiveDataTestUtil.getOrAwaitValue(taskDAO.getTasks());
        assertThat(byTask.get(0), equalTo(task));
        assertThat(byTask.size(), equalTo(1));
    }

    @Test
    public void testUpdateTask() throws InterruptedException {
        Task task = new Task(0,1,"Linge",1);
        Task taskU = new Task(0,1,"Papy",0);
        db.mTaskDAO().insertTasks(task);
        db.mTaskDAO().updateTasks(taskU);
        List<Task> byTask = LiveDataTestUtil.getOrAwaitValue(taskDAO.getTasks());
        assertThat(taskU, equalTo(byTask.get(0)));
    }

    @Test
    public void testRemoveTask() throws InterruptedException {
        Task task = new Task(0,1,"Linge",3);
        long taskId = task.getId();
        db.mTaskDAO().insertTasks(task);
        db.mTaskDAO().deleteTasks(taskId);
        List<Task> byTask = LiveDataTestUtil.getOrAwaitValue(taskDAO.getTasks());
        assertTrue(byTask.isEmpty());
    }
}
