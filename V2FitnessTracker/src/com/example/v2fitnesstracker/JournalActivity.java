package com.example.v2fitnesstracker;

import java.sql.SQLException;
import java.util.Date;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.databases.DatabaseHelper;
import com.example.entities.Entry;
import com.example.entities.Journal;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class JournalActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {

	private User user;
	private Journal journal;
	private RuntimeExceptionDao<Journal, Integer> journalDao;
	private RuntimeExceptionDao<Entry, Integer> entryDao;
	
	public final int ID_POSITION = 0;
	public final int ISLOCKED_POSITION = 1;
	public final int ENTRY_POSITION = 3; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = HomeActivity.user;
        journalDao = getHelper().getRuntimeJournalDao();
        setJournal();
        entryDao = getHelper().getRuntimeEntryDao();
        setContentView(R.layout.activity_journal);
        setNavigationButtons();
        updateView();
    }

	private void setJournal() {
		for(Journal j : journalDao.queryForAll()) {
			if(j.getUser() == user) journal = j;
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_journal, menu);
        return true;
    }
    
    // Updates the View for every journal entry that the User has
    public void updateView() {
    	LinearLayout overallLayout = (LinearLayout)(findViewById(R.id.journal_overallLayout));
    	overallLayout.removeViews(3, overallLayout.getChildCount() - 3);
    	for(Entry e : entryDao.queryForAll()) {
    		if(e.getJournal() == journal)
    			overallLayout.addView(createEntryRow(e));
    	}
    }
    
    /* Returns the id of the View (LinearLayout) passed in the parameter.
     * view is the View component (a LinearLayout) containing the id field. 
     */
    private int findId(View view) {
		LinearLayout viewParent = (LinearLayout)view.getParent();
		View idView = viewParent.getChildAt(0);
		// The id field is a TextView located at index 0 of the layout.
		if(idView instanceof TextView) {
			return Integer.parseInt(((TextView) idView).getText().toString());
		}
		return -1;
	}

    /*
     *  Creates a new Journal entry and adds it to the user.
     *  view is the View component that was clicked.
     */
    public void newEntry(View view) {
    	LinearLayout overallLayout = ((LinearLayout)findViewById(R.id.journal_overallLayout));
    	Date dateCreated = new Date();
    	Entry newEntry = new Entry();
    	newEntry.setDate(dateCreated);
    	newEntry.setJournal(journal);
    	journalDao.update(journal);
    	entryDao.create(newEntry);
    	
    	LinearLayout entryLayout = createLinearLayout(LinearLayout.VERTICAL);
    	View[] views = new View[] { createHiddenIdView(newEntry), createIsJournalLockedView(newEntry), 
    			createDateTextView(dateCreated), createNewEntryView(dateCreated, ""), createButtonView() };
    	addViewsToLayout(views, entryLayout);
    	overallLayout.addView(entryLayout);
    }
    
    /*
     * Returns a LinearLayout containing
     * the necessary fields for a Journal entry.
     */
    public LinearLayout createEntryRow(Entry entry) {
    	LinearLayout entryLayout = createLinearLayout(LinearLayout.VERTICAL);
    	View[] views = new View[] { createHiddenIdView(entry), createIsJournalLockedView(entry), 
    			createDateTextView(entry.getDate()), createNewEntryView(entry.getDate(), entry.getContent()), createButtonView() };
    	addViewsToLayout(views, entryLayout);
    	return entryLayout;
    }
    
    /*
     * Removes an Entry from the user.
     * view is the View component that was clicked.
     */
    public void removeEntry(View view) {
    	LinearLayout parent = (LinearLayout)view.getParent().getParent();
    	int id = -1;
    	View child = parent.getChildAt(ID_POSITION);
    	id = Integer.parseInt(((TextView) child).getText().toString());
    	try {
			entryDao.delete(findEntryById(id));
			journalDao.update(journal);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	parent.removeAllViews();
    	parent.setVisibility(View.GONE);
    }
    
    private Entry findEntryById(int id) throws SQLException {
    	for(Entry e : entryDao.queryForAll()) {
    		if(e.getJournal().getId() == journal.getId()) {
    			if(e.getId() == id) return e;
    		}
    	}
    	return null;
    }
    
    /*
     * Sets the TextChangedListener of an EditText.
     * entry is the EditText to be modified.
     */
    private void setEntryListener(final EditText entryTextField) {
		entryTextField.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					Entry entry = findEntryById(findId(entryTextField));
					entry.setContent(entryTextField.getText().toString());
					entryDao.update(entry);
					journalDao.update(journal);
				}
				catch(NullPointerException e) {
					e.printStackTrace();
				} catch(NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
    	});
	}
    
    private TextView createDateTextView(Date dateCreated) {
    	TextView dateText = new TextView(this);
    	dateText.setTextColor(Color.RED);
    	dateText.setText(dateCreated.toString());
    	return dateText;
    }
    
    private LinearLayout createButtonView() {
    	LinearLayout layout = createLinearLayout(LinearLayout.HORIZONTAL); 
		View[] views = new View[] { createLockUnlockButtonView(), createClearButtonView(), 
				createRemoveButtonView() };
		addViewsToLayout(views, layout);
    	return layout;
	}
    
    private Button createRemoveButtonView() {
    	Button remove = new Button(this);
    	remove.setText("Remove");
    	remove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeEntry(v);
			}
		});
    	return remove;
    }
    
    private Button createClearButtonView() {
    	Button clear = new Button(this);
    	clear.setText("Clear");
    	clear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clear(v);
			}
		});
    	return clear;
    }
    
    private Button createLockUnlockButtonView() {
    	Button lockUnlock = new Button(this);
    	lockUnlock.setText("Lock");
    	lockUnlock.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lockUnlock(v);
			}
		});
    	return lockUnlock;
    }
    
    private TextView createIsJournalLockedView(Entry entry) {
    	TextView locked = ActivityFactory.createTextView(this, 10, "false");
    	locked.setVisibility(View.GONE);
    	return locked;
    }
    
    private TextView createHiddenIdView(Entry entry) {
    	TextView id = ActivityFactory.createTextView(this, 10, entry.getId() + ""); 
    	id.setVisibility(View.GONE);
    	return id;
    }
    
    private EditText createNewEntryView(Date dateCreated, String content) {
    	final EditText entry = new EditText(this);
    	entry.setPadding(10, 10, 10, 10);
    	entry.setBackgroundColor(Color.WHITE);
    	entry.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	entry.setHint("Write a message here...");
    	entry.setText(content);
    	entry.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    	setEntryListener(entry);
    	return entry;
    }
    
    public void lockUnlock(View view) {
    	LinearLayout layout = (LinearLayout)view.getParent().getParent();
    	EditText journal = (EditText)layout.getChildAt(ENTRY_POSITION);
    	TextView isLocked = (TextView)layout.getChildAt(ISLOCKED_POSITION);
    	
    	boolean enable = (isLocked.getText().equals("true"))? true : false; 
    	journal.setEnabled(enable);
    	((Button)view).setText((enable)? "Lock" : "Unlock");
    	isLocked.setText((enable)? "false" : "true");
    }
    
    public void clear(View view) {
    	LinearLayout layout = (LinearLayout)view.getParent().getParent();
    	((EditText)layout.getChildAt(ENTRY_POSITION)).setText("");
    }
    
    public void setNavigationButtons() {
    	Button home = (Button)findViewById(R.id.navigation_home);
    	Button exercise = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, exercise, nutrition, journal, logout);
    }
	
	// Shows an AlertDialog with the message passed in the parameter
	public void showAlertMessage(String message, boolean cancelable) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(cancelable);
		ad.setMessage(message);
		ad.show();
	}

	// Adds the View elements inside the array into the layout
	public void addViewsToLayout(View[] views, ViewGroup layout) {
		if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
	}

	// Returns a LinearLayout with the orientation passed in as parameter
	public LinearLayout createLinearLayout(int orientation) {
		LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
	}
}
