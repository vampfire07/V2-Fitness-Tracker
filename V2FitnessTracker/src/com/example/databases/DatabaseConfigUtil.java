package com.example.databases;

import com.example.entities.Entry;
import com.example.entities.Exercise;
import com.example.entities.Food;
import com.example.entities.Journal;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil{

	private static final Class<?>[] classes = new Class[] { Entry.class, Exercise.class, 
		Food.class, Journal.class, User.class };
	
	  public static void main(String[] args) throws Exception {
	    writeConfigFile("ormlite_config.txt", classes);
	  }
}
