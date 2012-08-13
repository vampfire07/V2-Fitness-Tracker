package com.example.entities;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Entry implements Serializable {

	private static final long serialVersionUID = -5884147702617735623L;
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(defaultValue = "")
	private String content;
	
	@DatabaseField(canBeNull = false)
	private Date date;
	
	@DatabaseField(foreign = true)
	private Journal journal;
	
	public Entry() {
		// No-arg constructor used by ORMLite
	}
	
	public Entry(String content, Date date) {
		this.content = content;
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return content;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}
}
