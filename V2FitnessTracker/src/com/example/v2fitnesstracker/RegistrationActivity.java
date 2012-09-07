package com.example.v2fitnesstracker;

import java.io.IOException;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.databases.DatabaseHelper;
import com.example.entities.Journal;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class RegistrationActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	public final static String USERNAME_PACKAGE = "com.example.v2fitnesstracker.USERNAME";
	public final static String PASSWORD_PACKAGE = "com.example.v2fitnesstracker.PASSWORD";

	// Database Access Objects (DAOs)
	private RuntimeExceptionDao<User, Integer> userDao;
	private RuntimeExceptionDao<Journal, Integer> journalDao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		userDao = getHelper().getRuntimeUserDao();
		journalDao = getHelper().getRuntimeJournalDao();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_registration, menu);
		return true;
	}

	public void register(View view) throws IOException, ClassNotFoundException {
		// An Intent to start an activity called HomeActivity
		Intent loginIntent = new Intent(this, HomeActivity.class);

		String username = ((EditText) findViewById(R.id.user_username))
				.getText().toString();
		String password = ((EditText) findViewById(R.id.user_password))
				.getText().toString();

		if (username.length() == 0 || password.length() == 0) {
			showErrorMessage("Both username and password fields must not be empty.");
			return;
		}

		// Checks for duplicate usernames
		if(checkDuplicateUsername(username)) return;
		
		// Creates a new instance of user
		User user = createNewUser(username, password);
		
		// Stores the user into the database
		userDao.create(user);
		
		loginIntent.putExtra("user_extra", user);
		
		// Finishes this Activity and starts a new one
		finish();
		startActivity(loginIntent);
	}
	
	public void back(View view) {
		// Get the Intent that started this Activity
		Intent backToLogin = new Intent(this, LoginActivity.class);

		// Close this Activity and start the Activity defined by the Intent
		finish();
		startActivity(backToLogin);
	}

	// Checks for duplicate usernames
	private boolean checkDuplicateUsername(String username) {
		for(User u : userDao.queryForAll()) {
			if(u.getUsername().equalsIgnoreCase(username)) {
				showErrorMessage("Username is already taken. Choose a different one.");
				return true;	
			}
		}
		return false;
	}

	// Creates a User upon registration
	private User createNewUser(String username, String password) {
		User user = new User(username, password, new Date());
		Journal journal = new Journal();
		journal.setUser(user);
		journalDao.create(journal);
		user.setJournal(journal);
		journalDao.update(journal);
		return user;
	}

	// Shows an error with the message passed in the parameter
	public void showErrorMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(true);
		ad.setMessage(message);
		ad.show();
	}

	// Returns the String content of the EditText provided its id
	public String getTextFromEditText(int id) {
		try {
			EditText editText = (EditText) findViewById(id);
			return editText.getText().toString();
		} catch (ClassCastException e) {
			return "";
		}
	}

	// Returns false if the content is empty or null
	public void addToIntent(Intent intent, int id, final String pack) {
		EditText edittext = (EditText) findViewById(id);
		String content = edittext.getText().toString();
		intent.putExtra(pack, content);
	}

	// Returns true if the View with the id passed in's text is empty or null
	public boolean isEmpty(int id) {
		EditText edittext = (EditText) findViewById(id);
		String content = edittext.getText().toString();
		if (content == null || content.length() == 0) {
			return true;
		}
		return false;
	}

}
