package com.cleanup.todoc.InterfaceDAO;

import android.content.Context;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)

public abstract class SaveMyProjectDatabase extends RoomDatabase {

    private static volatile SaveMyProjectDatabase INSTANCE;

    public abstract TaskDAO mTaskDAO();

    public abstract ProjectDAO mProjectDAO();

    public static SaveMyProjectDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (SaveMyProjectDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            SaveMyProjectDatabase.class, "MyDatabase.db")

                            .addCallback(prepopulateDatabase())

                            .build();

                }

            }

        }

        return INSTANCE;

    }

    private static RoomDatabase.Callback prepopulateDatabase() {

        return new RoomDatabase.Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);

                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.mProjectDAO().createProject(
                        new Project(1, "Badass", 0)));

            }

        };

    }

}
