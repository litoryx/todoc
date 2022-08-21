package com.cleanup.todoc.InterfaceDAO;

import com.cleanup.todoc.model.Project;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ProjectDAO {

    @Query("select * from Project")
    LiveData<List<Project>> getProjects();

    @Insert
    long createProject(Project project);

}
