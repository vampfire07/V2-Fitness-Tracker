package com.example.databases;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;

import com.example.entities.Entry;
import com.example.entities.Exercise;
import com.example.entities.Food;
import com.example.entities.Journal;
import com.example.entities.User;

public class DataSource {
	
	// Database fields
	private SQLiteDatabase database;
	private V2SQLiteOpenHelper dbHelper;
	private String[] allUsernames = { V2SQLiteOpenHelper.COLUMN_ID, V2SQLiteOpenHelper.COLUMN_USERNAME };
	private String[] allNames = { V2SQLiteOpenHelper.COLUMN_ID, V2SQLiteOpenHelper.COLUMN_NAME };
	private String[] allContents = { V2SQLiteOpenHelper.COLUMN_ID, V2SQLiteOpenHelper.COLUMN_CONTENT };
	
	public DataSource(Context context) {
		dbHelper = new V2SQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
//	public Entry createEntry(String content, Date date) {
//		ContentValues values = new ContentValues();
//		values.put(V2SQLiteOpenHelper.COLUMN_CONTENT, content);
//		values.put(V2SQLiteOpenHelper.COLUMN_CREATED, date.toString());
//		
//		long insertId = database.insert(V2SQLiteOpenHelper.TABLE_ENTRIES, null, values);
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_ENTRIES, allContents, 
//				V2SQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//		cursor.moveToFirst();
//		Entry newEntry = cursorToEntry(cursor, date);
//		cursor.close();
//		return newEntry;
//	}
//	
//	public void deleteEntry(Entry entry) {
//		long id = entry.getId();
//		database.delete(V2SQLiteOpenHelper.TABLE_ENTRIES, V2SQLiteOpenHelper.COLUMN_ID + " = " + id, null);
//	}
//	
////	public SparseArray<Entry> getAllEntries() {
////		SparseArray<Entry> entries = new SparseArray<Entry>();
////		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_ENTRIES, allContents, null, null, null, null, null);
////		cursor.moveToFirst();
////		while(!cursor.isAfterLast()) {
////			Date date = new Date(cursor.getLong(1));
////			entries.add(cursorToEntry(cursor, cursor.getString(2)));
////		}
////	}
//	
//	public Exercise createExercise(String name, String type, int sets, int reps) {
//		ContentValues values = new ContentValues();
//		values.put(V2SQLiteOpenHelper.COLUMN_NAME, name);
//		values.put(V2SQLiteOpenHelper.COLUMN_TYPE, type);
//		values.put(V2SQLiteOpenHelper.COLUMN_SETS, sets);
//		values.put(V2SQLiteOpenHelper.COLUMN_REPS, reps);
//		
//		long insertId = database.insert(V2SQLiteOpenHelper.TABLE_EXERCISES, null, values);
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_EXERCISES, allNames, 
//				V2SQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//		cursor.moveToFirst();
//		Exercise newExercise = cursorToExercise(cursor);
//		cursor.close();
//		return newExercise;
//	}
//	
//	public void deleteExercise(Exercise exercise) {
//		long id = exercise.getId();
//		database.delete(V2SQLiteOpenHelper.TABLE_EXERCISES, V2SQLiteOpenHelper.COLUMN_ID + " = " + id, null);
//	}
//	
//	public Food createFood(String name, String amount, int calories) {
//		ContentValues values = new ContentValues();
//		values.put(V2SQLiteOpenHelper.COLUMN_NAME, name);
//		values.put(V2SQLiteOpenHelper.COLUMN_AMOUNT, amount);
//		values.put(V2SQLiteOpenHelper.COLUMN_CALORIES, calories);
//		
//		long insertId = database.insert(V2SQLiteOpenHelper.TABLE_FOODS, null, values);
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_FOODS, allNames, 
//				V2SQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//		cursor.moveToFirst();
//		Food newFood = cursorToFood(cursor);
//		cursor.close();
//		return newFood;
//	}
//	
//	public void deleteFood(Food food) {
//		long id = food.getId();
//		database.delete(V2SQLiteOpenHelper.TABLE_FOODS, V2SQLiteOpenHelper.COLUMN_ID + " = " + id, null);
//	}
//	
//	public void createUser(String username, String password, Date registered, int age, int heightFeet, 
//			int heightInches, int weight, int goalWeight, SparseArray<Exercise> exercises,
//			SparseArray<Food> foods, Journal journal) throws IOException, ClassNotFoundException {
//		ContentValues values = new ContentValues();
//		values.put(V2SQLiteOpenHelper.COLUMN_USERNAME, username);
//		values.put(V2SQLiteOpenHelper.COLUMN_PASSWORD, password);
//		values.put(V2SQLiteOpenHelper.COLUMN_REGISTERED, toString(registered));
//		values.put(V2SQLiteOpenHelper.COLUMN_AGE, age);
//		values.put(V2SQLiteOpenHelper.COLUMN_HEIGHTFEET, heightFeet);
//		values.put(V2SQLiteOpenHelper.COLUMN_HEIGHTINCHES, heightInches);
//		values.put(V2SQLiteOpenHelper.COLUMN_WEIGHT, weight);
//		values.put(V2SQLiteOpenHelper.COLUMN_GOALWEIGHT, goalWeight);
//		values.put(V2SQLiteOpenHelper.COLUMN_EXERCISES, toString((Serializable)exercises));
//		values.put(V2SQLiteOpenHelper.COLUMN_FOODS, toString((Serializable)foods));
//		values.put(V2SQLiteOpenHelper.COLUMN_JOURNAL, toString(journal));
//		
//		long insertId = database.insert(V2SQLiteOpenHelper.TABLE_USERS, null, values);
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_USERS, allNames, 
//				V2SQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//		cursor.moveToFirst();
//		setUserValues(cursor);
//		cursor.close();
//	}
//	
//	public void deleteUser(String username) {
//		database.delete(V2SQLiteOpenHelper.TABLE_USERS, V2SQLiteOpenHelper.COLUMN_USERNAME + " = " + username, null);
//	}
//	
//	public List<String> getAllUsernames() {
//		List<String> usernames = new ArrayList<String>();
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_USERS, allUsernames, null, null, null, null, null);
//		cursor.moveToFirst();
//		while(!cursor.isAfterLast()) {
//			usernames.add(cursor.getString(1));
//		}
//		return usernames;
//	}
//	
//	public void setUserValuesByUsername(String username) throws IOException, ClassNotFoundException {
//		Cursor cursor = database.query(V2SQLiteOpenHelper.TABLE_USERS, allUsernames, null, null, null, null, null);
//		if(cursor.getString(1).equalsIgnoreCase(username)) {
//			setUserValues(cursor);
//			return;
//		}
//		Log.w(DataSource.class.getName(), "Did not find a matching username");
//	}
//	
//	@SuppressWarnings("unchecked")
//	private void setUserValues(Cursor cursor) throws IOException, ClassNotFoundException {
//		try {
//			User.setUsername(cursor.getString(1));
//			User.setPassword(cursor.getString(2));
//			
//			Date parsed = new Date();
//			parsed = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(cursor.getString(3));
//			
//			User.setRegistered(parsed);
//			User.setAge(cursor.getInt(4));
//			User.setHeight_feet(cursor.getInt(5));
//			User.setHeight_inches(cursor.getInt(6));
//			User.setWeight(cursor.getInt((7)));
//			User.setGoal_weight(cursor.getInt(8));
//			
//			User.setExercises((SparseArray<Exercise>)fromString(cursor.getString(9)));
//			User.setFoods((SparseArray<Food>)fromString(cursor.getString(10)));
//			User.setJournal((Journal)fromString(cursor.getString(11)));
//			
//		}
//		catch(NumberFormatException e) {
//			Log.w(DataSource.class.getName(), "Cannot safely cast long to integer for food id.");
//		} catch (ParseException e) {
//			Log.w(DataSource.class.getName(), "Cannot safely parse string to date.");
//		}
//	}
//	
//	private Food cursorToFood(Cursor cursor) {
//		try {
//			return new Food((int)cursor.getLong(0), cursor.getString(1), cursor.getString(2),
//					cursor.getInt(3));
//		}
//		catch(NumberFormatException e) {
//			Log.w(DataSource.class.getName(), "Cannot safely cast long to integer for food id.");
//		}
//		return null;
//	}
//	
//	private Exercise cursorToExercise(Cursor cursor) {
//		try {
//			return new Exercise((int)cursor.getLong(0), cursor.getString(1), cursor.getString(2),
//					cursor.getInt(3), cursor.getInt(4));
//		}
//		catch(NumberFormatException e) {
//			Log.w(DataSource.class.getName(), "Cannot safely cast long to integer for exercise id.");
//		}
//		return null;
//	}
//	
//	private Entry cursorToEntry(Cursor cursor, Date date) {
//		try {
//			return new Entry((int)cursor.getLong(0), cursor.getString(1), date);
//		}
//		catch(NumberFormatException e) {
//			Log.w(DataSource.class.getName(), "Cannot safely cast long to integer for entry id.");
//		}
//		return null;
//	}
//	
//	/** Read the object from Base64 string. */
//    private static Object fromString(String s) throws IOException, ClassNotFoundException {
//        byte [] data = Base64.decode(s, Base64.DEFAULT);
//        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
//        Object o  = ois.readObject();
//        ois.close();
//        return o;
//    }
//
//    /** Write the object to a Base64 string. */
//    private static String toString(Object o) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(o);
//        oos.close();
//        return new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
//    }
}
