package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databases.MyViewModel;
import com.example.todolist.databases.ToDo;
import com.example.todolist.databases.ToDoDatabase;
import com.example.todolist.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	//array list to store the data from the database
	private final ArrayList<ToDo> toDoArrayList = new ArrayList<>();

	//adapter
	private MyAdapter myAdapter;
	RecyclerView recyclerView;

	//Binding
	ActivityMainBinding activityMainBinding;
	MainActivityClickHandler handlers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
		{
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		handlers = new MainActivityClickHandler(this);
		activityMainBinding.setOnClickHandler(handlers);

		recyclerView = activityMainBinding.recycleView;
		recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
		recyclerView.setHasFixedSize(true);

		activityMainBinding.moreOptions.setOnClickListener(v -> {
			PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
			popupMenu.getMenuInflater().inflate(R.menu.list_to_grid, popupMenu.getMenu());

			popupMenu.setOnMenuItemClickListener(item -> {
				if(item.getItemId() == R.id.listView)
				{
					recyclerView = activityMainBinding.recycleView;
					recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
					recyclerView.setHasFixedSize(true);
					return true;
				}
				else if(item.getItemId() == R.id.gridView)
				{
					recyclerView = activityMainBinding.recycleView;
					recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
					recyclerView.setHasFixedSize(true);
					return true;
				}
				else
				{
					return false;
				}
			});
			popupMenu.show();
		});

		//Adapter
		myAdapter = new MyAdapter(toDoArrayList);

		//Data source
		ToDoDatabase.getInstance(this);

		//view Model
		MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
		/*
		 * below code extract all the data from the
		 * database and store all of them into
		 * array list and set it to recycler view
		 * */
		viewModel.getAllToDo().observe(this,
				new Observer<List<ToDo>>() {
					@SuppressLint("NotifyDataSetChanged")
					@Override
					public void onChanged(List<ToDo> toDos) {
						toDoArrayList.clear();

						toDoArrayList.addAll(toDos);
						myAdapter.notifyDataSetChanged();
					}
				});
		recyclerView.setAdapter(myAdapter);
		/*
		 * below code is about the what to do when
		 * use swap the list to left side
		 * basically it delete the list
		 * also give pop dialog box
		 * */
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
				return false;
			}
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
				ToDo t = toDoArrayList.get(viewHolder.getAdapterPosition());

				Dialog dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.delete_pop_up_screen);
				dialog.setCancelable(false);

				MaterialButton yesButton = dialog.findViewById(R.id.yes);
				MaterialButton noButton = dialog.findViewById(R.id.no);

				yesButton.setOnClickListener(v -> {
					viewModel.deleteToDo(t);
					dialog.dismiss();
				});

				noButton.setOnClickListener(v -> {
					dialog.dismiss();
					refreshScreen(myAdapter);
				});
				dialog.show();
			}
		}).attachToRecyclerView(recyclerView);


		/*
		 * below code is used to start new activity when
		 * FAB button clicked which is used to create new
		 * to do list
		 * */
		activityMainBinding.addList.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
			startActivity(intent);
		});
	}
	public void refreshScreen(MyAdapter myAdapter)
	{
		/*
		* this method is used to refresh the screen means
		* when the user delete the list but it rejects the permission
		* than update database's value show into recycler view
		* */
		MyViewModel viewModel = new ViewModelProvider(this)
				.get(MyViewModel.class);
		toDoArrayList.clear();
		// Fetch new data from the ViewModel
		viewModel.getAllToDo().observe(MainActivity.this, new Observer<List<ToDo>>() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onChanged(List<ToDo> toDos) {
				toDoArrayList.addAll(toDos);
				myAdapter.notifyDataSetChanged();
			}
		});
	}
}