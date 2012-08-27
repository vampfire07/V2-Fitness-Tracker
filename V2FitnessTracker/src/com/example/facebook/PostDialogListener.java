package com.example.facebook;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class PostDialogListener extends BaseDialogListener{

	private Context context;
	
	public PostDialogListener(Context ctx) {
		context = ctx;
	}
	
	@Override
    public void onComplete(Bundle values) {
        final String postId = values.getString("post_id");
        if (postId != null) {
        	Toast.makeText(context, "Message posted on the wall.", Toast.LENGTH_LONG).show();
        } else {
        	Toast.makeText(context, "No message posted on the wall.", Toast.LENGTH_LONG).show();
        }
    }
}
