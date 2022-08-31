package com.cleanup.todoc.InterfaceDAO;

import android.database.Cursor;

import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDAO {

    @Query("select * from Task")
    LiveData<List<Task>> getTasks();

    @Query("delete from Task where id = :taskId")
    int deleteTasks(long taskId);

    @Insert
    long insertTasks(Task task);

    @Update
    int updateTasks(Task task);
}
