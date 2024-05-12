package com.example.todolist.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ToDoList_table")
public class ToDo
{
	@ColumnInfo(name = "todoId")
	@PrimaryKey(autoGenerate = true)
	private int todoId;

	@ColumnInfo(name = "todoTitle")
	private String todoTitle;

	@ColumnInfo(name = "todoDesc")
	private String todoDesc;

	@ColumnInfo(name = "todoTime")
	private String todoTime;

	@ColumnInfo(name = "todoDate")
	private String todoDate;

	public ToDo(String todoTitle, String todoDesc, String todoTime, String todoDate) {
		this.todoTitle = todoTitle;
		this.todoDesc = todoDesc;
		this.todoTime = todoTime;
		this.todoDate = todoDate;
	}

	public ToDo() {
	}

	public int getTodoId() {
		return todoId;
	}

	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}

	public String getTodoDesc() {
		return todoDesc;
	}

	public void setTodoDesc(String todoDesc) {
		this.todoDesc = todoDesc;
	}

	public String getTodoTime() {
		return todoTime;
	}

	public void setTodoTime(String todoTime) {
		this.todoTime = todoTime;
	}

	public String getTodoDate() {
		return todoDate;
	}

	public void setTodoDate(String todoDate) {
		this.todoDate = todoDate;
	}
}
