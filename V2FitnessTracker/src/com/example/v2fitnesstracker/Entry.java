package com.example.v2fitnesstracker;

import java.util.Date;

public class Entry {

	private long id;
	private String content;
	private Date date;
	
	public Entry(long id, String content, Date date) {
		this.id = id;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
