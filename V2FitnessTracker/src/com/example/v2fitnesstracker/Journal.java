package com.example.v2fitnesstracker;

import java.util.ArrayList;
import java.util.List;

public class Journal {

	public int entryId = 1;
	
	private List<Entry> entries;
	
	public Journal() {
		entries = new ArrayList<Entry>();
	}
	
	public Entry findEntryById(long id) {
		for(Entry e : entries) {
			if(e.getId() == id) return e;
		}
		return null;
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	public void removeEntry(Entry entry) {
		for(Entry e : entries) {
			if(e.getId() == entry.getId()) entries.remove(e);
		}
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
}
