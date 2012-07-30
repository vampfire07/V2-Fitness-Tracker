package com.example.v2fitnesstracker;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public abstract class ActivityFactory {
	
	/*
	 *  Returns a TextView (text field) based on the parameters passed in.
	 *  context is the Context where the View is to be created in.
	 */
	public static TextView createTextView(Context context, int textSize, String text) {
		TextView textView = new TextView(context);
		textView.setTextSize(textSize);
		textView.setText(text);
		return textView;
	}
	
	/*
	 *  Returns an EditText (editable text field) based on the parameters passed in.
	 *  context is the Context where the View is to be created in.
	 *  hint is the value displayed on the text field, but is not the content.
	 */
	public static EditText createEditText(Context context, int textSize, String hint) {
		EditText editText = new EditText(context);
		editText.setTextSize(textSize);
		editText.setHint(hint);
		return editText;
	}
}
