package com.example.todolist;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databases.ToDo;
import com.example.todolist.databinding.ShowListBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ToDoViewHolder>
{
	private final ArrayList<ToDo> toDos;
	DatePicker datePicker;
	TimePicker timePicker;
	Button setDateTime;
	long date, time;
	public MyAdapter(ArrayList<ToDo> toDos) {
		this.toDos = toDos;
	}

	/** @noinspection ClassEscapesDefinedScope*/
	@NonNull
	@Override
	public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		//view for items in recycle view
		ShowListBinding createListBinding = DataBindingUtil.inflate(
				LayoutInflater.from(parent.getContext()),
				R.layout.show_list,
				parent,
				false
		);
		return new ToDoViewHolder(createListBinding);
	}

	/** @noinspection ClassEscapesDefinedScope*/
	@Override
	public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
		// Get the ToDo object at the current position in the list
		ToDo currentToDo = toDos.get(position);

		// Set the ToDo object to the data binding of the ViewHolder
		holder.createListBinding.setTodo(currentToDo);

		// Set a long click listener on the cardView in the item view
		holder.createListBinding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// Create a PopupMenu anchored to the long-clicked view (v)
				PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
				// Inflate the menu resource into the PopupMenu
				popupMenu.inflate(R.menu.my_card_menu);

				// Set an item click listener for the PopupMenu
				popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// Check if the clicked item is the "Set Reminder" option
						if (item.getItemId() == R.id.setRemainder) {
							Dialog dialog =  new Dialog(v.getContext());
							dialog.setContentView(R.layout.show_time_date);
							dialog.show();
							// Initialize the date and time pickers
							datePicker = dialog.findViewById(R.id.datePicker);
							timePicker = dialog.findViewById(R.id.timePicker);
							setDateTime = dialog.findViewById(R.id.setDateTime);

							setDateTime.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v)
								{
									if(datePicker != null)
									{
										int year = datePicker.getYear();
										int month = datePicker.getMonth();
										int dayOfMonth = datePicker.getDayOfMonth();
										int hour = timePicker.getCurrentHour();
										int minute = timePicker.getCurrentMinute();

										// Create a Calendar object for the selected date and time
										Calendar selectedCalendar = Calendar.getInstance();
										selectedCalendar.set(year, month, dayOfMonth, hour, minute, 0);

										// Get the current system time
										Calendar currentCalendar = Calendar.getInstance();

										// Check if the selected date and time is in the past
										if (selectedCalendar.before(currentCalendar)) {
											// Display a toast message indicating that a future date and time should be selected
											Toast.makeText(v.getContext(), "Please select a future date and time.", Toast.LENGTH_SHORT).show();
										} else {
											// Display a toast message indicating successful reminder set
											date = selectedCalendar.getTime().getDate();
											time = selectedCalendar.getTimeInMillis();

											//Toast.makeText(v.getContext(), date+"\t"+time, Toast.LENGTH_SHORT).show();
											setRemainderAlarm(v.getContext(), currentToDo.getTodoTitle(), currentToDo.getTodoDesc(), time);
											dialog.dismiss();
											// Here, you might proceed with setting up the reminder
										}
									}
									else
									{
										// Handle the case where datePicker or timePicker is null
										Toast.makeText(v.getContext(), "Date or time picker is not available.", Toast.LENGTH_SHORT).show();
									}
								}
							});

						}
						return false;
					}
				});
				// Show the PopupMenu
				popupMenu.show();

				// Display a toast message indicating the clicked ToDo item
				//Toast.makeText(v.getContext(), currentToDo.getTodoTitle() + " clicked...", Toast.LENGTH_SHORT).show();

				// Indicate that the long click event has been consumed
				return true;
			}
		});

		holder.createListBinding.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent openTodoIntent = new Intent(v.getContext(), OpenAndUpdateToDo.class);
				openTodoIntent.putExtra("todoTitleFromMain", currentToDo.getTodoTitle());
				openTodoIntent.putExtra("todoDescriptionFromMain", currentToDo.getTodoDesc());
				startActivity(v.getContext(), openTodoIntent, null);
			}
		});
	}


	@SuppressLint("ScheduleExactAlarm")
	private void setRemainderAlarm(Context context, String todoTitle, String todoDesc, long time) {
		Calendar currentTime = Calendar.getInstance();
		Calendar alarmTime = Calendar.getInstance();
		alarmTime.setTimeInMillis(time);
		alarmTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR));
		alarmTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH));
		alarmTime.set(Calendar.DAY_OF_MONTH, currentTime.get(Calendar.DAY_OF_MONTH));

		if (currentTime.getTimeInMillis() >= alarmTime.getTimeInMillis()) {
			// Time has already passed, set alarm for the next day
			alarmTime.add(Calendar.DAY_OF_MONTH, 1);

		}

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("todoTitleFromMain", todoTitle);
		intent.putExtra("todoDescriptionFromMain", todoDesc);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (alarmManager != null) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
		}
	}


	@Override
	public int getItemCount()
	{
		if(toDos != null)
		{
			return toDos.size();
		}
		else {
			return 0;
		}
	}

	static class ToDoViewHolder extends RecyclerView.ViewHolder
	{
		private final ShowListBinding createListBinding;

		public ToDoViewHolder(@NonNull ShowListBinding createListBinding)
		{
			super(createListBinding.getRoot());
			this.createListBinding = createListBinding;

		}
	}
}
