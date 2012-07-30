package com.example.v2fitnesstracker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public interface V2Activity {
	void showAlertMessage(String message, boolean cancelable);
	void addViewsToLayout(View[] views, ViewGroup layout);
	LinearLayout createLinearLayout(int orientation);
	void setNavigationButtons();
}
