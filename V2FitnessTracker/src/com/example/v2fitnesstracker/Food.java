package com.example.v2fitnesstracker;

public class Food {
	
	private String name;
	private String amount;
	private int calories;
	
	public Food(String name, String amount, int calories) {
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
}
