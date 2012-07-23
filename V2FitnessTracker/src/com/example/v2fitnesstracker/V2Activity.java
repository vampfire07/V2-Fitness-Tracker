package com.example.v2fitnesstracker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface V2Activity {
	void setPageView();
	void showErrorMessage(String message);
	void addViewsToLayout(View[] views, ViewGroup layout);
	ImageView createImageView(int imageResource, ImageView.ScaleType scaleType);
	ImageView createLogo();
	LinearLayout createLinearLayout(int orientation);
}
