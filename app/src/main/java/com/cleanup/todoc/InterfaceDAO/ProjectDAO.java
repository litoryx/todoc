package com.cleanup.todoc.InterfaceDAO;

import com.cleanup.todoc.model.Project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface ProjectDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);


}
