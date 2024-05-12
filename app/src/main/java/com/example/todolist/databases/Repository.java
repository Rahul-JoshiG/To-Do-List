package com.example.todolist.databases;

import android.app.Application;
import android.os.Looper;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository
{
	private final ToDoDAO toDoDAO;
	ExecutorService executor;
	Handler handler;
	public Repository(Application application)
	{
		ToDoDatabase toDoDatabase = ToDoDatabase.getInstance(application);
		this.toDoDAO = toDoDatabase.getDao();

		//used to background Database operations
		executor = Executors.newSingleThreadExecutor();

		//used to updating UI
		handler = new Handler(Looper.getMainLooper());
	}

	public void addList(ToDo toDo)
	{

		executor.execute(new Runnable() {
			@Override
			public void run() {
				toDoDAO.insert(toDo);
			}
		});
	}

	public void deleteList(ToDo toDo)
	{
		executor.execute(new Runnable() {
			@Override
			public void run() {
				toDoDAO.delete((toDo));
			}
		});
	}

	public void updateList(ToDo toDo)
	{
		executor.execute(new Runnable() {
			@Override
			public void run() {
				toDoDAO.update(toDo);
			}
		});
	}

	public LiveData<List<ToDo>> getAllToDoList()
	{
		return toDoDAO.getAllList();
	}

}
