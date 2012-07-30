package com.example.v2fitnesstracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NavigationFactory {
	
	private Context context;
	
	public NavigationFactory(Context context) {
		this.context = context;
	}
	
	public void setNavigationOnClick(Button home, Button exercise, Button nutrition, 
			Button journal, Button logout) {
		setOnClickHomeButton(home);
		setOnClickExerciseButton(exercise);
		setOnClickNutritionButton(nutrition);
		setOnClickJournalButton(journal);
		setOnClickLogoutButton(logout);
	}
	
	private void setOnClickLogoutButton(Button logout) {
		logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.LOGIN);
			}
		});
	}
	
	private void setOnClickJournalButton(Button journal) {
		journal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.JOURNAL);
			}
		});
	}

	private void setOnClickNutritionButton(Button nutrition) {
		nutrition.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.NUTRITION);
			}
		});
	}

	private void setOnClickExerciseButton(Button exercise) {
		exercise.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.EXERCISE);
			}
		});
	}

	private void setOnClickHomeButton(Button home) {
		home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.HOME);
			}
		});
	}
	
	private void navigate(Pages page) {
		Activity activity = (Activity)context;
		Intent navigateIntent = null;
		switch(page) {
			case HOME:
				navigateIntent = new Intent(context, HomeActivity.class);
				break;
			case EXERCISE:
				navigateIntent = new Intent(context, ExerciseActivity.class);
				break;
			case NUTRITION:
				navigateIntent = new Intent(context, NutritionActivity.class);
				break;
			case JOURNAL:
				navigateIntent = new Intent(context, JournalActivity.class);
				break;
			case LOGIN:
				navigateIntent = new Intent(context, LoginActivity.class);
				break;
		}
		activity.startActivity(navigateIntent);
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}
