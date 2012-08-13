package com.example.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Exercise implements Serializable {
	
	private static final long serialVersionUID = 1619436988107211583L;
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String type;
	
	@DatabaseField
	private int sets;
	
	@DatabaseField
	private int reps;
	
	@DatabaseField(foreign = true)
	private User user;
	
	public Exercise() {
		// No-arg constructor used by ORMLite
	}
	
	public Exercise(String name, String type, int sets, int reps) {
		this.name = name;
		this.type = type;
		this.sets = sets;
		this.reps = reps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
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
