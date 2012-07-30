package com.example.tests;

import java.util.ArrayList;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.v2fitnesstracker.*;

public class JournalActivityTester extends ActivityInstrumentationTestCase2<JournalActivity> {
	
	JournalActivity activity;
	
	public JournalActivityTester(String name) {
		super(JournalActivity.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Find views
		activity = getActivity();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testAddEntry() {
		User.getJournal().setEntries(new ArrayList<Entry>());
		final Date dateCreated = new Date();
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				User.getJournal().addEntry(new Entry(1, "Blah", dateCreated));
			}
		});
		// Asserts that the journal is empty
		assertTrue(User.getJournal().getEntries().size() == 0);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the click triggered for a new Entry to be created
		assertTrue(User.getJournal().getEntries().size() == 1);
		assertTrue(User.getJournal().getEntries().get(0).getId() == 1);
		assertTrue(User.getJournal().getEntries().get(0).getContent().equals("Blah"));
		assertTrue(User.getJournal().getEntries().get(0).getDate().equals(dateCreated));
	}
	
	@SmallTest
	public void testRemoveEntry() {
		User.getJournal().setEntries(new ArrayList<Entry>());
		Entry entry = new Entry(1, "Blah", new Date());
		User.getJournal().addEntry(entry);
		
		// Asserts that the entry was added
		assertTrue(User.getJournal().getEntries().size() == 1);
		
		LinearLayout layout = new LinearLayout(activity);
		TextView id = new TextView(activity);
		id.setText("1");
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.removeEntry(v);
			}
		});
		layout.addView(id);
		layout.addView(button);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the entry was removed
		assertTrue(User.getJournal().getEntries().size() == 0);
	}
	
	@SmallTest
	public void testLockUnlock() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				EditText journal = (EditText)activity.findViewById(R.id.journal);
				
				Button button = new Button(activity);
				button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						activity.lockUnlock(v);
					}
				});
				
				// Asserts that the button contains a listener
				assertTrue(button.callOnClick());
				
				// Asserts that the journal has been locked
				assertFalse(journal.isEnabled());
				
				// Calls the listener onClick method again
				assertTrue(button.callOnClick());
				
				// Asserts that the journal has been unlocked
				assertTrue(journal.isEnabled());
			}
		});
		getInstrumentation().waitForIdleSync();
	}
	
	@SmallTest
	public void testClear() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				EditText journal = (EditText)activity.findViewById(R.id.journal);
				journal.setText("HELLO");
				
				Button button = new Button(activity);
				button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						activity.clear(v);
					}
				});
				
				// Asserts that the button contains a listener
				assertTrue(button.callOnClick());
				
				// Asserts that the journal has been locked
				assertEquals(journal.getText().toString(), "");
				// TO-DO: Journal entries must have a textChanged listener
//				assertTrue(User.getJournal().getEntries().get(0).getContent().equals(""));
				
			}
		});
		getInstrumentation().waitForIdleSync();
	}
}

