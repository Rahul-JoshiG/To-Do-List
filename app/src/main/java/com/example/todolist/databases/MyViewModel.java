package com.example.todolist.databases;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel
{

	private final Repository myRepository;

	public MyViewModel(@NonNull Application application) {
		super(application);
		this.myRepository = new Repository(application);
	}

	public LiveData<List<ToDo>> getAllToDo()
	{
		//Live Data
		return myRepository.getAllToDoList();
	}

	public void addNewToDo(ToDo toDo)
	{
		myRepository.addList(toDo);
	}

	public void deleteToDo(ToDo toDo)
	{
		myRepository.deleteList(toDo);
	}

	public void updateToDo(ToDo toDo)
	{
		myRepository.updateList(toDo);
	}

}
