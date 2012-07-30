package com.example.v2fitnesstracker;

public class Food {
	
	private long id;
	private String name;
	private String amount;
	private int calories;
	
	public Food(long id, String name, String amount, int calories) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.calories = calories;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
