package com.example.v2fitnesstracker;

import java.util.Date;
import java.util.List;

import android.app.Activity;
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

//TO-DO: Journal entries must have a textChanged listener
public class JournalActivity extends Activity implements V2Activity {

	private boolean isJournalLocked;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        setNavigationButtons();
        addFirstEntry();
    }
    
    private void addFirstEntry() {
    	Date dateCreated = new Date();
    	User.getJournal().addEntry(new Entry(User.getJournal().entryId++, "", dateCreated));
    	setDateFirstEntry(dateCreated);
    	setEntryListener((EditText)findViewById(R.id.journal));
    }

	private void setDateFirstEntry(Date dateCreated) {
        TextView initialDate = ((TextView)findViewById(R.id.journal_initialDate));
        initialDate.setText(dateCreated.toString());
        initialDate.setTextColor(Color.RED);
	}

	private TextView createDateTextView(Date dateCreated) {
		TextView dateText = new TextView(this);
		dateText.setTextColor(Color.RED);
        dateText.setText(dateCreated.toString());
		return dateText;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_journal, menu);
        return true;
    }
    
    public void newEntry(View view) {
    	LinearLayout overallLayout = ((LinearLayout)findViewById(R.id.journal_overallLayout));
    	LinearLayout entryLayout = createLinearLayout(LinearLayout.VERTICAL);
    	Date dateCreated = new Date();
    	Entry newEntry = new Entry(User.getJournal().entryId++, "", dateCreated);
    	View[] views = new View[] { createHiddenIdView(newEntry), createDateTextView(dateCreated), 
    			createNewEntryView(dateCreated), createRemoveButtonView() };
    	addViewsToLayout(views, entryLayout);
    	overallLayout.addView(entryLayout);
    	User.getJournal().addEntry(newEntry);
    }
    
    public void removeEntry(View view) {
    	LinearLayout parent = (LinearLayout)view.getParent();
    	long id = -1;
    	for(int i = 0; i < parent.getChildCount(); i++) {
    		View child = parent.getChildAt(i);
    		if(child instanceof TextView) {
    			id = Long.parseLong(((TextView) child).getText().toString());
    			break;
    		}
    	}
    	List<Entry> tempEntries = User.getJournal().getEntries();
    	tempEntries.remove(User.getJournal().findEntryById(id));
    	User.getJournal().setEntries(tempEntries);
    	parent.removeAllViews();
    	parent.setVisibility(View.GONE);
    }
    
    private Button createRemoveButtonView() {
		Button removeButton = new Button(this);
		removeButton.setText("Remove");
    	removeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				removeEntry(v);
			}
		});
    	return removeButton;
	}
    
    private TextView createHiddenIdView(Entry entry) {
    	TextView id = ActivityFactory.createTextView(this, 10, entry.getId() + ""); 
    	id.setVisibility(View.GONE);
    	return id;
    }
    
    private long findId(View view) {
		LinearLayout viewParent = (LinearLayout)view.getParent();
		View idView = viewParent.getChildAt(0);
		// The id field is a TextView located at index 0 of the layout.
		if(idView instanceof TextView) {
			return Long.parseLong(((TextView) idView).getText().toString());
		}
		return -1;
	}
    
    private EditText createNewEntryView(Date dateCreated) {
    	final EditText entry = new EditText(this);
    	entry.setPadding(10, 10, 10, 10);
    	entry.setBackgroundColor(Color.WHITE);
    	entry.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    	entry.setHint("Write a message here...");
    	entry.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    	setEntryListener(entry);
    	return entry;
    }

	private void setEntryListener(final EditText entry) {
		entry.addTextChangedListener(new TextWatcher() {
    		public void afterTextChanged(Editable s) {
				try {
					User.getJournal().findEntryById(findId(entry)).setContent(entry.getText().toString());
				}
				catch(NullPointerException e) {
				}
				catch(NumberFormatException e) {
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
    
    public void lockUnlock(View view) {
    	EditText journal = (EditText)findViewById(R.id.journal);
    	boolean enable = (isJournalLocked)? true : false; 
    	journal.setEnabled(enable);
    	((Button)view).setText((isJournalLocked)? "Lock" : "Unlock");
    	isJournalLocked = !enable;
    }
    
    public void clear(View view) {
    	((EditText)findViewById(R.id.journal)).setText("");
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
	
//	private Button createLockUnlockButton() {
//		Button lock = new Button(this);
//		lock.setTextSize(10);
//    	lock.setText("Lock/Unlock");
//    	lock.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				boolean focus = (isJournalLocked)? true : false;
//				journal.setEnabled(focus);
//				isJournalLocked = (isJournalLocked)? false : true;
//			}
//    	});
//		return lock;
//	}
//	
//	private Button createClearButton() {
//		Button clear = new Button(this);
//		clear.setTextSize(10);
//    	clear.setText("Clear");
//    	clear.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				journal.setText("");
//			}
//    	});
//		return clear;
//	}

}
