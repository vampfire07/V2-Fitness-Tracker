package com.example.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class User implements Serializable {
	
	private static final long serialVersionUID = 1018593460307835042L;

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false, width = 15, unique = true)
	private String username;
	
	@DatabaseField(canBeNull = false, width = 15)
	private String password;
	
	@DatabaseField(canBeNull = false)
	private Date registered = new Date();
	
	@DatabaseField
	private int age;
	
	@DatabaseField
	private int weight;
	
	@DatabaseField
	private int goalWeight;
	
	@DatabaseField
	private int heightFeet;
	
	@DatabaseField
	private int heightInches;
	
	@ForeignCollectionField
	private ForeignCollection<Exercise> exercises;
	
	@ForeignCollectionField
	private ForeignCollection<Food> foods;
	
	@DatabaseField(foreign = true)
	private Journal journal;
	
	public User() {
		
	}
	
	public void clearData() {
		username = "";
		password = "";
		registered = null;
		age = 0;
		weight = 0;
		goalWeight = 0;
		heightFeet = 0;
		heightInches = 0;
	}
	
	public Food findFoodById(int id) {
		Iterator<Food> i = foods.iterator();
		for(Food f : foods) {
			while(i.hasNext()) {
				if(f.getId() == id) return f;
			}
		}
		return null;
	}
	
	public void removeFood(Food food) {
		foods.remove(food.getId());
	}
	
	public void addFood(Food food) {
		foods.add(food);
	}
	
	public Exercise findExerciseById(int id) {
		Iterator<Exercise> i = exercises.iterator();
		for(Exercise e : exercises) {
			while(i.hasNext()) {
				if(e.getId() == id) return e;
			}
		}
		return null;
	}
	
	public void removeExercise(Exercise exercise) {
		exercises.remove(exercise.getId());
	}
	
	public void addExercise(Exercise exercise) {
		exercises.add(exercise);
	}

	@Override
	public String toString() {
		return username;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getRegistered() {
		return registered;
	}


	public void setRegistered(Date registered) {
		this.registered = registered;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public int getGoalWeight() {
		return goalWeight;
	}


	public void setGoalWeight(int goalWeight) {
		this.goalWeight = goalWeight;
	}


	public int getHeightFeet() {
		return heightFeet;
	}


	public void setHeightFeet(int heightFeet) {
		this.heightFeet = heightFeet;
	}


	public int getHeightInches() {
		return heightInches;
	}


	public void setHeightInches(int heightInches) {
		this.heightInches = heightInches;
	}


	public ForeignCollection<Exercise> getExercises() {
		return exercises;
	}


	public void setExercises(ForeignCollection<Exercise> exercises) {
		this.exercises = exercises;
	}


	public ForeignCollection<Food> getFoods() {
		return foods;
	}


	public void setFoods(ForeignCollection<Food> foods) {
		this.foods = foods;
	}


	public Journal getJournal() {
		return journal;
	}


	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
