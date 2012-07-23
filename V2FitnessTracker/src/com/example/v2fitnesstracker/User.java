package com.example.v2fitnesstracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

	private static String username;
	private static String password;
	private static Date registered;
	
	private static int age;
	private static int weight;
	private static int goal_weight;
	private static int height_feet;
	private static int height_inches;
	
	private static List<Exercise> exercises;
	private static List<Food> foods;
	
	public static void clearData() {
		username = "";
		password = "";
		registered = null;
		age = 0;
		weight = 0;
		goal_weight = 0;
		height_feet = 0;
		height_inches = 0;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String username) {
		User.username = username;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String password) {
		User.password = password;
	}
	
	public static Date getRegistered() {
		return registered;
	}
	
	public static void setRegistered(Date registered) {
		User.registered = registered;
	}

	public static int getWeight() {
		return weight;
	}

	public static void setWeight(int weight) {
		User.weight = weight;
	}

	public static int getGoal_weight() {
		return goal_weight;
	}

	public static void setGoal_weight(int goal_weight) {
		User.goal_weight = goal_weight;
	}

	public static int getHeight_feet() {
		return height_feet;
	}

	public static void setHeight_feet(int height_feet) {
		User.height_feet = height_feet;
	}

	public static int getHeight_inches() {
		return height_inches;
	}

	public static void setHeight_inches(int height_inches) {
		User.height_inches = height_inches;
	}

	public static int getAge() {
		return age;
	}

	public static void setAge(int age) {
		User.age = age;
	}

	public static List<Exercise> getExercises() {
		if(exercises == null) {
			exercises = new ArrayList<Exercise>();
		}
		return exercises;
	}

	public static void setExercises(List<Exercise> exercises) {
		User.exercises = exercises;
	}

	public static List<Food> getFoods() {
		return foods;
	}

	public static void setFoods(List<Food> foods) {
		User.foods = foods;
	}
}
