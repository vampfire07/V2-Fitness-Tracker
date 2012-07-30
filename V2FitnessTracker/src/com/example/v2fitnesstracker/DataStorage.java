package com.example.v2fitnesstracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

import android.os.Environment;

public class DataStorage {

	public static String state;
	public static OutputStream os;
	public static InputStream in;
	
	public static void storeUser() throws FileNotFoundException, IOException {
		state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)) {
			// Media readable and writeable
			String fileName = User.getUsername() + ".v2";
			
			File path = Environment.getExternalStorageDirectory();
			File file = new File(path, fileName);
			path.mkdirs();
			
			os = new FileOutputStream(file);
			User.writeObject(new ObjectOutputStream(os));
		}
	}
	
	public static void retrieveUser(String username) throws StreamCorruptedException, IOException, ClassNotFoundException {
		String fileName = username + ".v2";
		
		File path = Environment.getExternalStorageDirectory();
		File file = new File(path, fileName);
		
		in = new FileInputStream(file);
		User.readObject(new ObjectInputStream(in));
	}
}
