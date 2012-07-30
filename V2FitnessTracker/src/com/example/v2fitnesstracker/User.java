package com.example.v2fitnesstracker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	
	public static long exerciseId = 1;
	public static long foodId = 1;

	private static String username;
	private static String password;
	private static Date registered;
	
	private static int age;
	private static int weight;
	private static int goal_weight;
	private static int height_feet;
	private static int height_inches;
	
	private static List<Exercise> exercises = new ArrayList<Exercise>();
	private static List<Food> foods = new ArrayList<Food>();
	private static Journal journal = new Journal();
	
	public static void writeObject(ObjectOutputStream out) throws IOException {
		out.writeLong(exerciseId);
		out.writeLong(foodId);
		out.writeObject(username);
		out.writeObject(password);
		out.writeObject(registered);
		out.writeInt(age);
		out.writeInt(weight);
		out.writeInt(goal_weight);
		out.writeInt(height_feet);
		out.writeInt(height_inches);
		out.writeObject(exercises);
		out.writeObject(foods);
		out.writeObject(journal);
		
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
	    exerciseId = in.readLong();
	    foodId = in.readLong();
	    username = (String)in.readObject();
	    password = (String)in.readObject();
	    registered = (Date)in.readObject();
	    age = in.readInt();
	    weight = in.readInt();
	    height_feet = in.readInt();
	    height_inches = in.readInt();
	    exercises = (ArrayList<Exercise>)in.readObject();
	    foods = (ArrayList<Food>)in.readObject();
	    journal = (Journal)in.readObject();

	    in.close();
	}

	
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
	
	public static Food findFoodById(long id) {
		for(Food f : foods) {
			if(f.getId() == id) return f;
		}
		return null;
	}
	
	public static void removeFood(Food food) {
		List<Food> temp = foods;
		temp.remove(findFoodById(food.getId()));
		User.setFoods(temp);
	}
	
	public static void addFood(Food food) {
		List<Food> temp = foods;
		temp.add(food);
		User.setFoods(temp);
	}
	
	public static Exercise findExerciseById(long id) {
		for(Exercise e : exercises) {
			if(e.getId() == id) return e;
		}
		return null;
	}
	
	public static void removeExercise(Exercise exercise) {
		List<Exercise> temp = exercises;
		temp.remove(findExerciseById(exercise.getId()));
		User.setExercises(temp);
	}
	
	public static void addExercise(Exercise exercise) {
		List<Exercise> temp = exercises;
		temp.add(exercise);
		User.setExercises(temp);
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

	public static Journal getJournal() {
		return journal;
	}

	public static void setJournal(Journal journal) {
		User.journal = journal;
	}
}
