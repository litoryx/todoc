package com.cleanup.todoc.repertories;

import com.cleanup.todoc.InterfaceDAO.TaskDAO;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskDataRepertory {

    private TaskDAO taskDAO;

    public TaskDataRepertory(TaskDAO taskDAO){this.taskDAO= taskDAO; }

    public LiveData<List<Task>> getTasks(){return this.taskDAO.getTasks();}

    public void insertTasks(Task task){taskDAO.insertTasks(task);}

    public void deleteTasks(Long Id){taskDAO.deleteTasks(Id);}

    public void updateTasks(Task task){taskDAO.updateTasks(task);}

}
