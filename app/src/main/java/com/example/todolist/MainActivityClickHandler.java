package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainActivityClickHandler
{
	Context context;

	public MainActivityClickHandler(Context context) {
		this.context = context;
	}

	public void onFABClicked(View view)
	{
		Intent intent = new Intent(view.getContext(), CreateListActivity.class);
		context.startActivity(intent);
	}
}
