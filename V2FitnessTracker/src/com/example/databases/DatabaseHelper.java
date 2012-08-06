package com.example.databases;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.entities.Entry;
import com.example.entities.Exercise;
import com.example.entities.Food;
import com.example.entities.Journal;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "v2fitnesstracker.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<Entry, Integer> entryDao;
	private Dao<Exercise, Integer> exerciseDao;
	private Dao<Food, Integer> foodDao;
	private Dao<Journal, Integer> journalDao;
	private Dao<User, Integer> userDao;
	private RuntimeExceptionDao<User, Integer> runtimeUserDao;
	private RuntimeExceptionDao<Exercise, Integer> runtimeExerciseDao;
	private RuntimeExceptionDao<Food, Integer> runtimeFoodDao;
	private RuntimeExceptionDao<Journal, Integer> runtimeJournalDao;
	private RuntimeExceptionDao<Entry, Integer> runtimeEntryDao;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Exercise.class);
			TableUtils.createTable(connectionSource, Food.class);
			TableUtils.createTable(connectionSource, Journal.class);
			TableUtils.createTable(connectionSource, Entry.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}
	
	public Dao<User, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}
	
	public Dao<Exercise, Integer> getExerciseDao() throws SQLException {
		if (exerciseDao == null) {
			exerciseDao = getDao(Exercise.class);
		}
		return exerciseDao;
	}
	
	public Dao<Food, Integer> getFoodDao() throws SQLException {
		if (foodDao == null) {
			foodDao = getDao(Food.class);
		}
		return foodDao;
	}
	
	public Dao<Journal, Integer> getJournalDao() throws SQLException {
		if (journalDao == null) {
			journalDao = getDao(Journal.class);
		}
		return journalDao;
	}
	
	public Dao<Entry, Integer> getEntryDao() throws SQLException {
		if (entryDao == null) {
			entryDao = getDao(Entry.class);
		}
		return entryDao;
	}
	
	public RuntimeExceptionDao<User, Integer> getRuntimeUserDao() {
		if (runtimeUserDao == null) {
			runtimeUserDao = getRuntimeExceptionDao(User.class);
		}
		return runtimeUserDao;
	}
	
	public RuntimeExceptionDao<Exercise, Integer> getRuntimeExerciseDao() {
		if (runtimeExerciseDao == null) {
			runtimeExerciseDao = getRuntimeExceptionDao(Exercise.class);
		}
		return runtimeExerciseDao;
	}
	
	public RuntimeExceptionDao<Food, Integer> getRuntimeFoodDao() {
		if (runtimeFoodDao == null) {
			runtimeFoodDao = getRuntimeExceptionDao(Food.class);
		}
		return runtimeFoodDao;
	}
	
	public RuntimeExceptionDao<Journal, Integer> getRuntimeJournalDao() {
		if (runtimeJournalDao == null) {
			runtimeJournalDao = getRuntimeExceptionDao(Journal.class);
		}
		return runtimeJournalDao;
	}
	
	public RuntimeExceptionDao<Entry, Integer> getRuntimeEntryDao() {
		if (runtimeEntryDao == null) {
			runtimeEntryDao = getRuntimeExceptionDao(Entry.class);
		}
		return runtimeEntryDao;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Entry.class, true);
			TableUtils.dropTable(connectionSource, Journal.class, true);
			TableUtils.dropTable(connectionSource, Food.class, true);
			TableUtils.dropTable(connectionSource, Exercise.class, true);
			TableUtils.dropTable(connectionSource, User.class, true);
			
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		runtimeUserDao = null;
		runtimeExerciseDao = null;
		runtimeFoodDao = null;
		runtimeJournalDao = null;
		runtimeEntryDao = null;
	}

}
