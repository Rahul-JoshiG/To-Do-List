package com.example.todolist.databases;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ToDo.class, version = 3)
public abstract class ToDoDatabase extends RoomDatabase
{
	public abstract ToDoDAO getDao();
	private static ToDoDatabase databaseInstance;
	public static synchronized ToDoDatabase getInstance(Context context)
	{
		if(databaseInstance == null)
		{
			databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
					ToDoDatabase.class,
					"Todo_db")
					.fallbackToDestructiveMigration()
					.build();
		}
		return databaseInstance;
	}
}
