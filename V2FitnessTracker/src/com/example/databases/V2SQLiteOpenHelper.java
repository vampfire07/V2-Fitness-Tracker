package com.example.databases;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class V2SQLiteOpenHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_REGISTERED = "registered";
	public static final String COLUMN_AGE = "age";
	public static final String COLUMN_HEIGHTFEET = "heightFeet";
	public static final String COLUMN_HEIGHTINCHES = "heightInches";
	public static final String COLUMN_WEIGHT = "weight";
	public static final String COLUMN_GOALWEIGHT = "goalWeight";
	public static final String COLUMN_EXERCISES = "exercises";
	public static final String COLUMN_FOODS = "foods";
	public static final String COLUMN_JOURNAL = "journal";
	
	public static final String TABLE_EXERCISES = "exercises";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_SETS = "sets";
	public static final String COLUMN_REPS = "reps";

	public static final String TABLE_FOODS = "foods";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_CALORIES = "calories";
	
	public static final String TABLE_ENTRIES = "entries";
	public static final String COLUMN_CONTENT = "calories";
	public static final String COLUMN_CREATED = "created";
	
	private static final String DATABASE_NAME = "v2fitnesstracker.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation SQL statements
	private static final String DATABASE_CREATE_USERS = "create table " +
			TABLE_USERS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_USERNAME + " text not null, " +
			COLUMN_PASSWORD + " text not null, " +
			COLUMN_REGISTERED + " date not null, " +
			COLUMN_AGE + " number, " +
			COLUMN_HEIGHTFEET + " number, " +
			COLUMN_HEIGHTINCHES + " number, " +
			COLUMN_WEIGHT + " number, " +
			COLUMN_GOALWEIGHT + " number, " + 
			COLUMN_EXERCISES + " text not null, " +
			COLUMN_FOODS + " text not null, " +
			COLUMN_JOURNAL + " text not null);";
	
	private static final String DATABASE_CREATE_EXERCISES = "create table " +
			TABLE_EXERCISES + "(" + COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_NAME + " text not null, " +
			COLUMN_TYPE + " text not null, " +
			COLUMN_SETS + " number, " +
			COLUMN_REPS + " number);";
	
	private static final String DATABASE_CREATE_FOODS = "create table " +
			TABLE_FOODS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_NAME + " text not null, " +
			COLUMN_AMOUNT + " text not null, " +
			COLUMN_CALORIES + " number);";
	
	private static final String DATABASE_CREATE_ENTRIES = "create table " +
			TABLE_ENTRIES + "(" + COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_CONTENT + ", " +
			COLUMN_CREATED + " date not null);";
	
	private static final String DROP_ALL_TABLES = "DROP TABLE IF EXISTS " + TABLE_ENTRIES + "\r\n" +
	"DROP TABLE IF EXISTS " + TABLE_FOODS + "\r\n" + "DROP TABLE IF EXISTS " + TABLE_EXERCISES + "\r\n" +
			"DROP TABLE IF EXISTS " + TABLE_USERS;
			

	public V2SQLiteOpenHelper(Context context) {
		super(context, /* new ContextWrapper().getFilesDir() getDataPath() + "/" + */DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_USERS + "\r\n" + DATABASE_CREATE_EXERCISES
				+ "\r\n" + DATABASE_CREATE_FOODS + "\r\n" + DATABASE_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(V2SQLiteOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data.");
		db.execSQL(DROP_ALL_TABLES);
		onCreate(db);
		
	}
	
}
