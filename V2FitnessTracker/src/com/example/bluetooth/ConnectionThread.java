package com.example.bluetooth;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.entities.User;
import com.example.v2fitnesstracker.HomeActivity;

public class ConnectionThread extends Thread {
	
	private final BluetoothSocket socket;
	private final BluetoothDevice device;
	private final BluetoothAdapter adapter;
	private OutputStream out;
	private ObjectOutputStream oos;
	private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	private User user;

	public ConnectionThread(BluetoothDevice bluetoothDevice, BluetoothAdapter adapter) {
		user = HomeActivity.user;
		this.adapter = adapter;
		BluetoothSocket temp = null;
		device = bluetoothDevice;
		
		try {
			temp = device.createRfcommSocketToServiceRecord(MY_UUID);
			Log.d("CONNECTIONTHREAD SUCCESS", "Successfully established an RFCOMM Socket.");
		} catch(IOException e) {
			Log.w("CONNECTIONTHREAD ERROR", "Something went wrong while trying to establish " +
					"a connection.");
		}
		
		socket = temp;
		
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			Log.w("CONNECTIONTHREAD ERROR", "Something went wrong while trying to look for the output stream.");
		}
	}
	
	public void run() {
		// If the BluetoothAdapter is still discovering devices, stop the process to prevent slowing down.
		if(adapter.isDiscovering()) adapter.cancelDiscovery();
		
//		byte[] buffer = new byte[1024];
//		int bytes;
		
		try {
			/* Connect the device through the socket. 
			 * This will block until it succeeds or throws an exception. 
			 */
			socket.connect();
			Log.d("CONNECTIONTHREAD SUCCESS", "Successfully connected to the remote device " + device.getName() + ".");
			
			oos = new ObjectOutputStream(out);
			oos.writeObject(user);
			
			oos.close();
			out.close();
		} 
		catch(IOException connectException) {
			Log.w("CONNECTIONTHREAD ERROR", "Something went wrong while trying to connect to socket/write to the stream.");
			try {
				socket.close();
			} catch(IOException closeException) {
				Log.w("CONNECTIONTHREAD ERROR", "Something went wrong while trying to close either the socket/stream connection.");
				return;
			}
		}
	}
	
//	private Document buildXML() throws ParserConfigurationException {
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();
//		Document d = db.newDocument();
//
//		/*
//		 * XML structure
//		<user>
//			<username>user.getUsername()</username>
//			<password>user.getPassword()</password>
//			<age>user.getAge()</age>
//			<heightFeet>user.getHeightFeet()</heightFeet>
//			<heightInches>user.getHeightInches()</heightInches>
//			<weight>user.getWeight()</weight>
//			<goalWeight>user.getGoalWeight()</goalWeight>
//			
//			<exercises>
//				<exercise>
//					<id></id>
//					<name></name>
//					<type></type>
//					<sets></sets>
//					<reps></reps>
//				</exercise>
//			</exercises>
//			
//			<foods>
//				<food>
//					<id></id>
//					<name></name>
//					<amount></amount>
//					<calories></calories>
//				</food>
//			</foods>
//			
//			<journal>
//				<id></id>
//				<entries>
//					<entry>
//						<id></id>
//						<content></content>
//						<date></date>
//					</entry>
//				</entries>
//			</journal>
//		</user>
//		*/
//		
//		Element user = d.createElement("user");
//		
//		Element username = d.createElement("username");
//		username.appendChild(d.createTextNode(this.user.getUsername()));
//		
//		Element password = d.createElement("password");
//		password.appendChild(d.createTextNode(this.user.getPassword()));
//		
//		Element age = d.createElement("age");
//		age.appendChild(d.createTextNode(this.user.getAge() + ""));
//		
//		Element heightFeet = d.createElement("heightFeet");
//		heightFeet.appendChild(d.createTextNode(this.user.getHeightFeet() + ""));
//		
//		Element heightInches = d.createElement("heightInches");
//		heightInches.appendChild(d.createTextNode(this.user.getHeightInches() + ""));
//		
//		Element weight = d.createElement("weight");
//		weight.appendChild(d.createTextNode(this.user.getWeight() + ""));
//		
//		Element goalWeight = d.createElement("goalWeight");
//		goalWeight.appendChild(d.createTextNode(this.user.getGoalWeight() + ""));
//		
//		user.appendChild(username);
//		user.appendChild(password);
//		user.appendChild(age);
//		user.appendChild(heightFeet);
//		user.appendChild(heightInches);
//		user.appendChild(weight);
//		user.appendChild(goalWeight);
//		
//		Element exercises = d.createElement("exercises");
//		for(Exercise e : this.user.getExerciseSet()) {
//			Element exercise = d.createElement("exercise");
//			
//			Element exerciseId = d.createElement("id");
//			exerciseId.appendChild(d.createTextNode(e.getId() + ""));
//			
//			Element exerciseName = d.createElement("name");
//			exerciseName.appendChild(d.createTextNode(e.getName()));
//			
//			Element exerciseType = d.createElement("type");
//			exerciseType.appendChild(d.createTextNode(e.getType()));
//			
//			Element exerciseSets = d.createElement("sets");
//			exerciseSets.appendChild(d.createTextNode(e.getSets() + ""));
//			
//			Element exerciseReps = d.createElement("reps");
//			exerciseReps.appendChild(d.createTextNode(e.getReps() + ""));
//			
//			exercise.appendChild(exerciseId);
//			exercise.appendChild(exerciseName);
//			exercise.appendChild(exerciseType);
//			exercise.appendChild(exerciseSets);
//			exercise.appendChild(exerciseReps);
//			exercises.appendChild(exercise);
//		}
//		
//		Element foods = d.createElement("foods");
//		for(Food f : this.user.getFoodSet()) {
//			Element food = d.createElement("food");
//			
//			Element foodId = d.createElement("id");
//			foodId.appendChild(d.createTextNode(f.getId() + ""));
//			
//			Element foodName = d.createElement("name");
//			foodName.appendChild(d.createTextNode(f.getName()));
//			
//			Element foodAmount = d.createElement("amount");
//			foodAmount.appendChild(d.createTextNode(f.getAmount()));
//			
//			Element foodCalories = d.createElement("calories");
//			foodCalories.appendChild(d.createTextNode(f.getCalories() + ""));
//			
//			food.appendChild(foodId);
//			food.appendChild(foodName);
//			food.appendChild(foodAmount);
//			food.appendChild(foodCalories);
//			foods.appendChild(food);
//		}
//		
//		Element journal = d.createElement("journal");
//		Element journalId = d.createElement("id");
//		journalId.appendChild(d.createTextNode(this.user.getJournal().getId() + ""));
//		
//		Element entries = d.createElement("entries");
//		for(Entry e : this.user.getJournal().getEntrySet()) {
//			Element entry = d.createElement("entry");
//			
//			Element entryId = d.createElement("id");
//			entryId.appendChild(d.createTextNode(e.getId() + ""));
//			
//			Element entryContent = d.createElement("content");
//			entryContent.appendChild(d.createTextNode(e.getContent()));
//			
//			Element entryDate = d.createElement("date");
//			entryDate.appendChild(d.createTextNode(e.getDate().toString()));
//			
//			entry.appendChild(entryId);
//			entry.appendChild(entryContent);
//			entry.appendChild(entryDate);
//			entries.appendChild(entry);
//		}
//		
//		journal.appendChild(journalId);
//		journal.appendChild(entries);
//		
//		user.appendChild(exercises);
//		user.appendChild(foods);
//		user.appendChild(journal);
//		d.appendChild(user);
//		
//		return d;
//	}
	
	public void cancel() {
		try {
			socket.close();
		} catch(IOException e) {
			Log.w("CONNECTIONTHREAD ERROR", "Something went wrong while trying to close the socket connection.");
		}
	}
	
}
