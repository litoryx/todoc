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
import static org.junit.Assert.assertThat;


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
        Project project = new Project(1,"dsds",0);
        db.mProjectDAO().createProject(project);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeTaskAndReadInList() throws Exception {

        Task task = new Task(0,1,"Linge",15/02/21);
        db.mTaskDAO().insertTasks(task);

        List<Task> byTask = LiveDataTestUtil.getOrAwaitValue(taskDAO.getTasks());
        assertThat(byTask.size(), equalTo(1));
        assertEquals(byTask.get(0), task);
    }
}
