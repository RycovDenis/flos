package kz.dev.home.flos.abstractions;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kz.dev.home.flos.datamodels.Task;

import kz.dev.home.flos.interfaces.TaskDao;
@Database(entities ={Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}