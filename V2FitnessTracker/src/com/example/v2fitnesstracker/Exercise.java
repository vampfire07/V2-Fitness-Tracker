package com.example.v2fitnesstracker;

public class Exercise {
	
	private long id;
	private String name;
	private String type;
	private int sets;
	private int reps;
	
	public Exercise(long id, String name, String type, int sets, int reps) {
		this.id = id;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
