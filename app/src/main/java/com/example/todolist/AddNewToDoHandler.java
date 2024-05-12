package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.view.View;
import android.widget.Toast;
import com.example.todolist.databases.MyViewModel;
import com.example.todolist.databases.ToDo;
import java.util.Calendar;

public class AddNewToDoHandler
{
	ToDo todo;
	Context context;
	MyViewModel myViewModel;
	public AddNewToDoHandler(ToDo todo, Context context, MyViewModel myViewModel) {
		this.todo = todo;
		this.context = context;
		this.myViewModel = myViewModel;
	}

	public String  setTime()
	{
		Calendar calendar = Calendar.getInstance();
		@SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(calendar.getTime());
	}

	public String setDate()
	{
		Calendar calendar = Calendar.getInstance();
		@SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
		return dateFormat.format(calendar.getTime());
	}

	public void onAddBtnClicked(View ignoredView)
	{
		if(todo.getTodoTitle() == null || todo.getTodoDesc() == null)
		{
			Toast.makeText(context, "Please Fill Details...", Toast.LENGTH_SHORT).show();
		}
		else{
			Intent i = new Intent(context, MainActivity.class);
			ToDo t = new ToDo(todo.getTodoTitle(), todo.getTodoDesc(), setTime(), setDate());

			myViewModel.addNewToDo(t);
			context.startActivity(i);
		}
	}
}
