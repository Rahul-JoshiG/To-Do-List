package com.example.todolist;

import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class AfterAlarmPopScreenActivity extends AppCompatActivity
{
	String todoTitle, todoDesc;
	Button dismissAlarm, openTodo;
	TextView titleOfTodo;
	AfterAlarmPopScreenActivity afterAlarmPopScreenActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_after_alarm_pop_screen);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		dismissAlarm = findViewById(R.id.stopAlarm);
		openTodo = findViewById(R.id.openToDo);

		todoTitle = getIntent().getStringExtra("todoTitleFromAlarm");
		todoDesc = getIntent().getStringExtra("todoDescriptionFromAlarm");
		titleOfTodo = findViewById(R.id.titleOfToDo);

		titleOfTodo.setText(todoTitle);

		dismissAlarm.setOnClickListener(v -> {
			AlarmReceiver.stopRingtone();
			finish();
		});


		openTodo.setOnClickListener(v -> {
			AlarmReceiver.stopRingtone();
			Intent intent = new Intent(this, OpenAndUpdateToDo.class);
			intent.putExtra("todoTitleFromAlarm", todoTitle);
			intent.putExtra("todoDescriptionFromAlarm", todoDesc);
			startActivity(intent);
		});

	}
}