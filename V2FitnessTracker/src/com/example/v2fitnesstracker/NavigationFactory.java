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
	
	public LinearLayout createNavigationButtons() {
		LinearLayout layout = new LinearLayout(context);
		Button home = createHomeButton();
		Button exercise = createExerciseButton();
		Button nutrition = createNutritionButton();
		Button journal = createJournalButton();
		Button logout = createLogoutButton();
    	View[] views = new View[] { home, exercise, nutrition, journal, logout };
    	addViewsToLayout(views, layout);
    	return layout;
	}
	
	private void navigate(Pages page) {
		Activity activity = (Activity)context;
		Intent navigateIntent = null;
		switch(page) {
			case HOME:
				navigateIntent = new Intent(context, HomeActivity.class);
				break;
			case EXERCISE:
				navigateIntent = new Intent(context, HomeActivity.class);
				break;
			case NUTRITION:
				navigateIntent = new Intent(context, HomeActivity.class);
				break;
			case JOURNAL:
				navigateIntent = new Intent(context, HomeActivity.class);
				break;
			case LOGIN:
				navigateIntent = new Intent(context, LoginActivity.class);
				break;
		}
		activity.startActivity(navigateIntent);
	}
	
	private Button createHomeButton() {
		Button home = new Button(context);
		home.setTextSize(10);
    	home.setText("Home");
    	home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.HOME);
			}
    	});
		return home;
	}
	
	// TO-DO: change parameter of navigate to their own respective classes
	private Button createExerciseButton() {
		Button exercise = new Button(context);
		exercise.setTextSize(10);
    	exercise.setText("Exercise");
    	exercise.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.EXERCISE);
			}
    	});
		return exercise;
	}
	
	// TO-DO: change parameter of navigate to their own respective classes
	private Button createNutritionButton() {
		Button nutrition = new Button(context);
		nutrition.setTextSize(10);
		nutrition.setText("Nutrition");
		nutrition.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.NUTRITION);
			}
    	});
		return nutrition;
	}
	
	// TO-DO: change parameter of navigate to their own respective classes
	private Button createJournalButton() {
		Button journal = new Button(context);
		journal.setTextSize(10);
		journal.setText("Journal");
		journal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				navigate(Pages.JOURNAL);
			}
    	});
		return journal;
	}
	
	private Button createLogoutButton() {
		Button logout = new Button(context);
		logout.setTextSize(10);
		logout.setText("Logout");
		logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				User.clearData();
				navigate(Pages.LOGIN);
			}
    	});
		return logout;
	}
	
	// Adds the View elements in the array into the layout
    private void addViewsToLayout(View[] views, View layout) {
    	if(views != null && layout != null) {
    		if(layout.getClass() == LinearLayout.class) {
    			for(View v : views) ((LinearLayout) layout).addView(v);
    		}
    	}
    }

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}
