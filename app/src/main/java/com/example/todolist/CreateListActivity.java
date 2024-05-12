package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.databases.MyViewModel;
import com.example.todolist.databases.ToDo;
import com.example.todolist.databinding.ActivityCreateListBinding;

public class CreateListActivity extends AppCompatActivity {
	private ActivityCreateListBinding activityCreateListBinding;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_create_list);

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

		ToDo todo = new ToDo();
		activityCreateListBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_list);

		AddNewToDoHandler handler = new AddNewToDoHandler(todo, this, myViewModel);
		activityCreateListBinding.setTodo(todo);
		activityCreateListBinding.setClickHandler(handler);

		activityCreateListBinding.listTitle.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				isEmpty(s);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		activityCreateListBinding.listDescription.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				isEmpty(s);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		activityCreateListBinding.previousScreen.setOnClickListener(v -> {
			Intent intent = new Intent(CreateListActivity.this, MainActivity.class);
			startActivity(intent);
		});

	}
	void isEmpty(CharSequence data)
	{
		if(data.length()>0){
			activityCreateListBinding.saveList.setVisibility(View.VISIBLE);
		}
		else{
			activityCreateListBinding.saveList.setVisibility(View.INVISIBLE);
		}
	}
}