package com.example.tests;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entities.Entry;
import com.example.entities.User;
import com.example.v2fitnesstracker.JournalActivity;

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
		final User user = new User();
//		user.getJournal().setEntries(new HashSet<Entry>());
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Entry entry = new Entry();
				entry.setId(1);
				entry.setDate(new Date());
//				user.getJournal().addEntry(entry);
			}
		});
		// Asserts that the journal is empty
		assertTrue(user.getJournal().getEntries().size() == 0);
		
		// Asserts that the button contains a listener, calls button click
		assertTrue(button.callOnClick());
		
		// Asserts that the click triggered for a new Entry to be created
		assertTrue(user.getJournal().getEntries().size() == 1);
	}
	
	@SmallTest
	public void testRemoveEntry() {
		final User user = new User();
		Entry entry = new Entry();
		entry.setId(1);
		entry.setDate(new Date());
//		user.getJournal().addEntry(entry);
		
		// Asserts that the entry was added
		assertTrue(user.getJournal().getEntries().size() == 1);
		
		// A row layout is being created
		LinearLayout layout = new LinearLayout(activity);
		TextView id = new TextView(activity);
		id.setText("1");
		TextView isLocked = new TextView(activity);
		isLocked.setText("false");
		LinearLayout buttonLayout = new LinearLayout(activity);
		Button button = new Button(activity);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				activity.removeEntry(v);
			}
		});
		buttonLayout.addView(button);
		layout.addView(id);
		layout.addView(isLocked);
		layout.addView(buttonLayout);
		
		// Asserts that the button contains a listener
		assertTrue(button.callOnClick());
		
		// Asserts that the entry was removed
		assertTrue(user.getJournal().getEntries().size() == 0);
	}
	
	@SmallTest
	public void testLockUnlock() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Date dateCreated = new Date();
				Entry entry = new Entry();
				entry.setId(1);
				entry.setDate(dateCreated);
				LinearLayout row = activity.createEntryRow(entry);
				EditText journal = (EditText)row.getChildAt(3);
				LinearLayout buttonLayout = (LinearLayout)row.getChildAt(4);
				Button lockUnlockButton = (Button)buttonLayout.getChildAt(0);
				
				// Asserts that there is a listener attached to the button
				assertTrue(lockUnlockButton.callOnClick());
				
				// Asserts that the journal entry has been locked
				assertFalse(journal.isEnabled());
				
				// Calls the method in the listener again
				assertTrue(lockUnlockButton.callOnClick());
				
				// Asserts that the journal entry has been unlocked
				assertTrue(journal.isEnabled());
			}
		});
		getInstrumentation().waitForIdleSync();
	}
}

