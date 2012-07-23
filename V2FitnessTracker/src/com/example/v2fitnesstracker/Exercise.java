package com.example.v2fitnesstracker;

public class Exercise {
	
	private String name;
	private String type;
	private int sets;
	private int reps;
	
	public Exercise(String name, String type, int sets, int reps) {
		this.setName(name);
		this.setType(type);
		this.setSets(sets);
		this.setReps(reps);
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
}
