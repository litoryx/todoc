package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.InterfaceDAO.SaveMyProjectDatabase;
import com.cleanup.todoc.ProjectViewModel;
import com.cleanup.todoc.repertories.ProjectDateRepertory;
import com.cleanup.todoc.repertories.TaskDataRepertory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private TaskDataRepertory taskDataSource;

    private ProjectDateRepertory projectDateSource;

    private Executor executor;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context){

    if (factory == null){

        synchronized (ViewModelFactory.class) {

            if (factory == null) { factory = new ViewModelFactory(context);}}}

    return factory;
}

private ViewModelFactory(Context context){

    SaveMyProjectDatabase database = SaveMyProjectDatabase.getInstance(context);

    this.taskDataSource = new TaskDataRepertory(database.mTaskDAO());
    this.projectDateSource = new ProjectDateRepertory(database.mProjectDAO());
    this.executor = Executors.newSingleThreadExecutor();
}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(ProjectViewModel.class)){

            return (T) new ProjectViewModel(taskDataSource, projectDateSource, executor);
        }
        throw new IllegalArgumentException("UNkown viewmodel class");
    }
}
