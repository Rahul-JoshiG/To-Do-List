package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.todolist.databases.MyViewModel;
import com.example.todolist.databases.ToDo;
import com.example.todolist.databinding.ActivityOpenAndUpdateToDoBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class OpenAndUpdateToDo extends AppCompatActivity {

	ActivityOpenAndUpdateToDoBinding binding;
	MyViewModel myViewModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_open_and_update_to_do);

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		// Retrieve data from AlarmReceiveActivity
		String titleFromAlarm = getIntent().getStringExtra("todoTitleFromAlarm");
		String descriptionFromAlarm = getIntent().getStringExtra("todoDescriptionFromAlarm");

		// Retrieve data from MainActivity
		String titleFromMain = getIntent().getStringExtra("todoTitleFromMain");
		String descriptionFromMain = getIntent().getStringExtra("todoDescriptionFromMain");

		// Choose the data to display based on priority
		String displayTitle = (titleFromAlarm != null) ? titleFromAlarm : titleFromMain;
		String displayDescription = (descriptionFromAlarm != null) ? descriptionFromAlarm : descriptionFromMain;

		// Set the data to the EditText fields
		if (displayTitle != null && displayDescription != null) {
			binding.listTitle.setText(displayTitle);
			binding.listDescription.setText(displayDescription);
		}

		boolean isEmpty = isAnyEmpty(binding.listTitle.getText().toString(), binding.listDescription.getText().toString());

		binding.previousScreen.setOnClickListener(v -> {
			Intent toMainActivityIntent = new Intent(OpenAndUpdateToDo.this, MainActivity.class);
			startActivity(toMainActivityIntent);
		});

		/*if(isEmpty)
		{
			Calendar calendar = Calendar.getInstance();
			String newDate = calendar.getTime().toString();
			String newTime = calendar.getTime().toString();
			binding.saveList.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String newTitle = displayTitle.toString();
					String newDesc = displayDescription.toString();
					ToDo toDo = new ToDo(newTitle, newDesc, newTime, newDate);

					myViewModel.updateToDo(toDo);
				}
			});
		}*/
	}

	private boolean isAnyEmpty(String titleText, String descriptionText) {
		if (titleText.isEmpty() || descriptionText.isEmpty()) {
			binding.saveList.setVisibility(View.INVISIBLE);
			return false;
		} else {
			binding.saveList.setVisibility(View.VISIBLE);
			return true;
		}
	}

}