package com.cleanup.todoc;

import android.content.Context;

import com.cleanup.todoc.InterfaceDAO.ProjectDAO;
import com.cleanup.todoc.InterfaceDAO.SaveMyProjectDatabase;
import com.cleanup.todoc.model.Project;

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
import androidx.test.runner.AndroidJUnit4;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ProjectDAOTestI {

    private ProjectDAO mprojectDAO;
    private SaveMyProjectDatabase db1;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db1 = Room.inMemoryDatabaseBuilder(context, SaveMyProjectDatabase.class).build();
        mprojectDAO = db1.mProjectDAO();
        Project project = new Project(0,"sdvff",0);
        db1.mProjectDAO().createProject(project);
    }

    @After
    public void closeDb() throws IOException {
        db1.close();
    }

    @Test
    public void writeProjectAndReadInList() throws InterruptedException {
        Project project = new Project(0,"Projet",0xFFEADAD1);
        db1.mProjectDAO().createProject(project);
        List<Project> byProject = LiveDataTestUtil.getOrAwaitValue(mprojectDAO.getProjects());
        assertThat(byProject.get(1), equalTo(project));
        assertThat(byProject.size(), equalTo(1));
    }
}
