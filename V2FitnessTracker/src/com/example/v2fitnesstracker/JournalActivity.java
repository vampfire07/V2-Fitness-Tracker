package com.example.v2fitnesstracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class JournalActivity extends Activity implements V2Activity {

	private EditText journal;
	private boolean isJournalLocked;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_journal, menu);
        return true;
    }

	public void setPageView() {
		ScrollView overallLayout = new ScrollView(this);
		LinearLayout linearOverallLayout = createLinearLayout(LinearLayout.VERTICAL);
		View[] views = new View[] { createLogo(), new NavigationFactory(this).createNavigationButtons(), createContentView() };
		addViewsToLayout(views, linearOverallLayout);
		overallLayout.addView(linearOverallLayout);
		setContentView(overallLayout);
	}
	
	private LinearLayout createContentView() {
		LinearLayout contentLayout = createLinearLayout(LinearLayout.VERTICAL);
		View[] views = new View[] { createJournal(), createLockUnlockButton(), createClearButton() };
		addViewsToLayout(views, contentLayout);
		return contentLayout;
	}
	
	private EditText createJournal() {
		journal = ActivityFactory.createEditText(this, 14, "");
		journal.setSingleLine(false);
		journal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return journal;
	}
	
	private Button createLockUnlockButton() {
		Button lock = new Button(this);
		lock.setTextSize(10);
    	lock.setText("Lock/Unlock");
    	lock.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean focus = (isJournalLocked)? true : false;
				journal.setEnabled(focus);
				isJournalLocked = (isJournalLocked)? false : true;
			}
    	});
		return lock;
	}
	
	private Button createClearButton() {
		Button clear = new Button(this);
		clear.setTextSize(10);
    	clear.setText("Clear");
    	clear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				journal.setText("");
			}
    	});
		return clear;
	}

	public void showErrorMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
	}

	public void addViewsToLayout(View[] views, ViewGroup layout) {
		if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
	}

	public ImageView createImageView(int imageResource, ScaleType scaleType) {
		ImageView view = new ImageView(this);
    	view.setImageResource(imageResource);
    	view.setScaleType(scaleType);
    	return view;
	}

	public ImageView createLogo() {
		return createImageView(R.drawable.logo, ScaleType.FIT_XY);
	}

	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}
}
