package com.example.entities;

import java.io.Serializable;
import java.util.Iterator;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Journal implements Serializable {

	private static final long serialVersionUID = -2716930949931989112L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@ForeignCollectionField
	private ForeignCollection<Entry> entries;
	
	@DatabaseField(foreign = true)
	private User user;
	
	public Journal() {
		
	}
	
	public Entry findEntryById(int id) {
		Iterator<Entry> i = entries.iterator();
		for(Entry e : entries) {
			while(i.hasNext()) {
				if(e.getId() == id) return e;
			}
		}
		return null;
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	// TO-DO: Change this to use temp
	public void removeEntry(Entry entry) {
		entries.remove(entry.getId());
	}
	
	public ForeignCollection<Entry> getEntries() {
		return entries;
	}
	
	public void setEntries(ForeignCollection<Entry> entries) {
		this.entries = entries;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
