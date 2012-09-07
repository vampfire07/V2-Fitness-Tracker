package com.example.v2fitnesstracker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entities.User;
import com.example.facebook.FacebookConnector;
import com.example.facebook.SessionEvents;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookActivity extends Activity {

	private static final String FACEBOOK_APPID = "278245685621907";
	private static final String FACEBOOK_PERMISSION = "publish_stream";
	private static final String TAG = "V2FitnessTracker";

	private final Handler mFacebookHandler = new Handler();
	private TextView loginStatus;
	private FacebookConnector facebookConnector;
	private Facebook facebook = new Facebook(FACEBOOK_APPID);

	final Runnable mUpdateFacebookNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(), "Posted to Wall!",
					Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);

		facebook.authorize(this, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onCancel() {
			}
		});

		this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this,
				getApplicationContext(), new String[] { FACEBOOK_PERMISSION });

		loginStatus = (TextView) findViewById(R.id.facebook_login_status);
		Button postButton = (Button) findViewById(R.id.facebook_btn_post);
		Button clearCredentialsButton = (Button) findViewById(R.id.facebook_btn_clear_credentials);

		postButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				postMessage();
			}
		});

		clearCredentialsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Logs the User out of Facebook and updates the login status.
				clearCredentials();
				updateLoginStatus();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
		this.facebookConnector.getFacebook().authorizeCallback(requestCode,
				resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateLoginStatus();
	}

	public void updateLoginStatus() {
		loginStatus.setText("Logged into Facebook : "
				+ facebookConnector.getFacebook().isSessionValid());
	}

	private StringBuilder getFacebookMsg() {
		StringBuilder msg = new StringBuilder();
		User user = HomeActivity.user;
		double userBMIIndex = HomeActivity.calculateBMIIndex();
		String userBMIClassification = HomeActivity.calculateBMIClassification(userBMIIndex);
		
		if(userBMIClassification.equals("Underweight")) {
			msg.append(user.getUsername() + " is a little lightweight lately. Time to get the right nutrients!\n");
		}
		else if(userBMIClassification.equals("Normal")) {
			msg.append(user.getUsername() + " is on the right track to a fit body!\n");
		}
		else if(userBMIClassification.equals("Overweight")) {
			msg.append(user.getUsername() + " may be taking more than the body can handle. Be careful!\n");
		}
		else if(userBMIClassification.equals("Obese")) {
			msg.append(user.getUsername() + " needs to reassess his/her goals and plans. V2 Fitness Tracker can help!\n");
		}
		msg.append("Weight until goal reached: " + (user.getGoalWeight() - user.getWeight() + " lbs.\n"));
		msg.append("Posted from V2 Fitness Tracker at " + new Date() + "\n");
		return msg;
	}

	public void postMessage() {

		if (facebookConnector.getFacebook().isSessionValid()) {
			postMessageInThread();
			return;
		} else {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {

				@Override
				public void onAuthSucceed() {
					postMessageInThread();
					return;
				}

				@Override
				public void onAuthFail(String error) {
					Log.w("FACEBOOK_ACTIVITY", "Auth failed.");
				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}
	}

	private void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {

				try {
					facebookConnector.postMessageOnWall(getFacebookMsg().toString());
//					try {
//						JSONObject attachment = new JSONObject();
//				        attachment.put("message", "V2 Fitness Tracker");
//				        attachment.put("name", HomeActivity.user.getUsername());
//						
//				        JSONObject media = new JSONObject();
//				        media.put("type", "image");
//				        media.put("src", R.drawable.logo);
//				        media.put("href", Utils.s(R.string.url_dotzmag));
//				        attachment.put("media", new JSONArray().put(media));
//
//				        Bundle params = new Bundle();
//				        params.putString("attachment", attachment.toString());
//				        mFacebook.dialog(mActivity, "stream.publish", params, new PostPhotoDialogListener());
//				        //mAsyncRunner.request("me/feed", params, "POST", new WallPostRequestListener(), null);
//
//				    } catch (JSONException e) {
//				        Log.e("FACEBOOK", e.getLocalizedMessage(), e);
//				    }
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TAG, "Error sending message.", ex);
				}
			}
		};
		t.start();
	}

	private void clearCredentials() {
		try {
			facebookConnector.getFacebook().logout(getApplicationContext());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
