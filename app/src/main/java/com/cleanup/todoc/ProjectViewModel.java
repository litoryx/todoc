package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repertories.ProjectDateRepertory;
import com.cleanup.todoc.repertories.TaskDataRepertory;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {

    private TaskDataRepertory taskDataSource;

    private ProjectDateRepertory projectDateSource;

    private Executor executor;

@Nullable

    private LiveData<Project> currentProject;

    public ProjectViewModel(TaskDataRepertory taskDataSource, ProjectDateRepertory projectDateSource, Executor executor)
    {
        this.taskDataSource = taskDataSource;
        this.projectDateSource = projectDateSource;
        this.executor = executor;
    }

    public void init(long projectId){

        if(this.currentProject != null){return;}

        currentProject = taskDataSource.getProjetIds(projectId);

    }

    public LiveData<Project> getProject(){return this.currentProject;}

    public LiveData<List<Task>> getTask(){

        return taskDataSource.getTasks();
    }

    public void insertTask(Task task){

        executor.execute(() -> {taskDataSource.insertTasks(task);});

    }

    public  void updateTask(Task task){

        executor.execute(() -> taskDataSource.updateTasks(task));
    }

    public  void deleteTask(long taskId){

        executor.execute(() -> taskDataSource.deleteTasks(taskId));
    }
}
