package com.example.v2fitnesstracker;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public abstract class ActivityFactory {
	
	public static TextView createTextView(Context context, int textSize, String text) {
		TextView textView = new TextView(context);
		textView.setTextSize(textSize);
		textView.setText(text);
		return textView;
	}
	
	public static EditText createEditText(Context context, int textSize, String hint) {
		EditText editText = new EditText(context);
		editText.setTextSize(textSize);
		editText.setHint(hint);
		return editText;
	}
}
