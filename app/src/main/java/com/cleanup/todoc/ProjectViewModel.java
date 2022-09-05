package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repertories.ProjectDateRepertory;
import com.cleanup.todoc.repertories.TaskDataRepertory;
import com.cleanup.todoc.ui.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {

    private TaskDataRepertory taskDataSource;

    private ProjectDateRepertory projectDateSource;

    private Executor executor;

    private final MediatorLiveData<List<Task>> mainViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<MainActivity.SortMethod> sortLiveData = new MutableLiveData<>(MainActivity.SortMethod.OLD_FIRST);

    @Nullable

    private LiveData<Project> currentProject;

    public ProjectViewModel(TaskDataRepertory taskDataSource, ProjectDateRepertory projectDateSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDateSource = projectDateSource;
        this.executor = executor;

        LiveData<List<Task>> tasksLiveData = taskDataSource.getTasks();

        mainViewStateMediatorLiveData.addSource(tasksLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(sortLiveData.getValue(), tasks);
            }
        });

        mainViewStateMediatorLiveData.addSource(sortLiveData, new Observer<MainActivity.SortMethod>() {
            @Override
            public void onChanged(MainActivity.SortMethod sortMethod) {
                combine(sortMethod, tasksLiveData.getValue());
            }
        });
    }

    public LiveData<Project> getProject() {
        return this.currentProject;
    }

    public LiveData<List<Task>> getTask() {

        return mainViewStateMediatorLiveData;
    }

    public void insertTask(Task task) {

        executor.execute(() -> {
            taskDataSource.insertTasks(task);
        });

    }

    public void updateTask(Task task) {

        executor.execute(() -> taskDataSource.updateTasks(task));
    }

    public void deleteTask(long taskId) {

        executor.execute(() -> taskDataSource.deleteTasks(taskId));
    }


    /**/
    public void onSortChanged(MainActivity.SortMethod sortMethod) {

        sortLiveData.setValue(sortMethod);

    }

    private void combine(MainActivity.SortMethod sortMethod, List<Task> tasks) {

        if (tasks == null) {
            return;
        }

        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;

        }
        mainViewStateMediatorLiveData.setValue(tasks);

    }


}
