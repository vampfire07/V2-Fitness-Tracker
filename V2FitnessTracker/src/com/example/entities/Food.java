package com.example.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Food implements Serializable {

	private static final long serialVersionUID = 3201594887230070317L;

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField
	private String amount;
	
	@DatabaseField
	private int calories;
	
	@DatabaseField(foreign = true)
	private User user;
	
	public Food() {
		// No-arg constructor used by ORMLite
	}
	
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
