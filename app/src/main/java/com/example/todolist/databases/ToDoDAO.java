package com.example.todolist.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDAO {
	@Insert
	void insert(ToDo toDo);

	@Delete
	void delete(ToDo toDo);

	@Update
	void update(ToDo toDo);

	@Query("SELECT * FROM ToDoList_table")
	LiveData<List<ToDo>> getAllList();
}
