package com.example.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.UUID;

import com.example.entities.User;
import com.example.v2fitnesstracker.HomeActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class AcceptThread extends Thread {

	private final BluetoothServerSocket serverSocket;
	private InputStream in;
	private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	public AcceptThread(BluetoothAdapter adapter) {
		BluetoothServerSocket temp = null;
		try {
			temp = adapter.listenUsingRfcommWithServiceRecord("SERVICE_RECORD", MY_UUID);
		} catch(IOException e) {
			Log.w("ACCEPT THREAD", "Something went wrong while trying to listen for connections.");
		}
		serverSocket = temp;
	}
	
	public void run() {
		BluetoothSocket socket = null;
		while(true) {
			try{
				socket = serverSocket.accept();
				in = socket.getInputStream();
				
				ObjectInputStream ois = new ObjectInputStream(in);
				HomeActivity.saveReceivedUser((User)ois.readObject());
			} catch(IOException e) {
				break;
			} catch (ClassNotFoundException e) {
				Log.w("ACCEPT THREAD", "Something went wrong while trying to cast Object into User.");
			}
			if(socket != null) {
				cancel();
			}
		}
	}
	
	public void cancel() {
		try {
			serverSocket.close();
		} catch(IOException e) {
			Log.w("ACCEPT THREAD", "Something went wrong while trying to close the server socket.");
		}
	}
}
