package com.example.v2fitnesstracker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bluetooth.AcceptThread;
import com.example.bluetooth.ConnectionThread;
import com.example.databases.DatabaseHelper;
import com.example.entities.Exercise;
import com.example.entities.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class HomeActivity extends OrmLiteBaseActivity<DatabaseHelper> implements V2Activity {
	
	private static final int DEVICE_ADDRESS_INDEX = 1;
	private BluetoothDevice remoteDevice;
	
	public static User user;
	
	// Database Access Objects (DAOs)
	private static RuntimeExceptionDao<User, Integer> dao;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = getHelper().getRuntimeUserDao();
        int id = getIntent().getIntExtra("user_id", -1);
        
        for(User u : dao.queryForAll()) {
        	if(u.getId() == id) user = u;
        }
        setContentView(R.layout.activity_home);
        initializeInformation();
        setNavigationButtons();
    }
    
    /*
     * Sets the device up to listen for connections.
     * Also enables Bluetooth and makes the device discoverable. 
     */
    public void listen(View view) {
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if(mBluetoothAdapter == null) {
			showAlertMessage("Your device does not support Bluetooth", true);
			return;
		}
		
		if(!mBluetoothAdapter.isEnabled()) {
			/* Allow for the device to be discovered by Bluetooth-enabled devices for 300 seconds.
			 * Also enables Bluetooth.
			 */
			enableDiscoverable();
		}
		AcceptThread acceptThread = new AcceptThread(mBluetoothAdapter);
		acceptThread.run();
    }
    
    public static void saveReceivedUser(User user) {
    	for(User u : dao.queryForAll()) {
    		if(u.getUsername().equalsIgnoreCase(user.getUsername())) {
    			dao.update(user);
    			return;
    		}
    	}
    	dao.create(user);
    }
    
    /*
     * Sets the device up to search for Bluetooth-enabled remote devices.
     * Also enables Bluetooth and makes the device discoverable. 
     */
    public void bluetoothShare(View view) {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null) {
			showAlertMessage("Your device does not support Bluetooth", true);
			return;
		}
		
		if(!mBluetoothAdapter.isEnabled()) {
			/* Allow for the device to be discovered by Bluetooth-enabled devices for 300 seconds.
			 * Also enables Bluetooth.
			 */
			enableDiscoverable();
		}
		
		mBluetoothAdapter.startDiscovery();
		
		final ArrayAdapter<CharSequence> deviceList = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
		// Store into a Set all the devices that have been paired to this device, if any
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there is any paired device
		if(pairedDevices.size() > 0) {
			for(BluetoothDevice device : pairedDevices) {
				deviceList.add(device.getName() + "\n" + device.getAddress());
			}
		}
		
		/* Create a BroadcastReceiver that listens for an action
		 * Action is called ACTION_FOUND 
		*/
		final BroadcastReceiver mReceiver = createDeviceFoundReceiver(deviceList);
		
		// Register the BroadcastReceiver to an Intent filter
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		Set<BluetoothDevice> allDevices = pairedDevices;
		setVisualContents(mBluetoothAdapter, deviceList, allDevices);
	}

	private void enableDiscoverable() {
		int discoverableDuration = 300;
		Intent discoverableIntent = new
				Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, discoverableDuration);
		startActivity(discoverableIntent);
	}

	private BroadcastReceiver createDeviceFoundReceiver(
			final ArrayAdapter<CharSequence> deviceList) {
		final BroadcastReceiver mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				// If the discovery process founds a device, add that device to array adapter
				if(BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					deviceList.add(device.getName() + "\n" + device.getAddress());
				}
			}
		};
		return mReceiver;
	}

	private void setVisualContents(BluetoothAdapter mBluetoothAdapter,
			final ArrayAdapter<CharSequence> deviceList,
			Set<BluetoothDevice> allDevices) {
		Spinner deviceListSpinner = new Spinner(this);
    	deviceList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	deviceListSpinner.setAdapter(deviceList);
    	
    	Dialog dialog = new Dialog(this);
    	dialog.setTitle("Available devices");
    	
    	LinearLayout overallLayout = createLinearLayout(LinearLayout.VERTICAL);
    	
    	View[] views = new View[] { deviceListSpinner, createSpinnerButtons(dialog, deviceListSpinner, allDevices, mBluetoothAdapter) };
    	addViewsToLayout(views, overallLayout);
    	dialog.setContentView(overallLayout);
    	dialog.show();
	}
    
    private void attemptConnect(BluetoothAdapter mBluetoothAdapter) {
    	if(remoteDevice != null) {
    		// Attempt to connect to the remote device
    		ConnectionThread connect = new ConnectionThread(remoteDevice, mBluetoothAdapter);
    		connect.run();
    	}
    }
    
    private LinearLayout createSpinnerButtons(Dialog dialog, Spinner spinner, 
    		Set<BluetoothDevice> allDevices, BluetoothAdapter adapter) {
    	LinearLayout layout = createLinearLayout(LinearLayout.HORIZONTAL);
    	Button okButton = createOKButton(dialog, spinner, allDevices, adapter);
    	Button cancelButton = createCancelButton(dialog);
    	View[] views = new View[] { okButton, cancelButton };
    	addViewsToLayout(views, layout);
    	return layout;
    }
    
    private Button createOKButton(final Dialog dialog, final Spinner spinner, 
    		final Set<BluetoothDevice> allDevices, final BluetoothAdapter adapter) {
		Button okButton = new Button(this);
    	okButton.setText("OK");
    	okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String[] textSplit = getSpinnerSelection(spinner).split("\n");
				if(textSplit != null) {
					String deviceAddress = textSplit[DEVICE_ADDRESS_INDEX];
					for(BluetoothDevice device : allDevices) {
						if(device.getAddress().equals(deviceAddress)) {
							remoteDevice = device;
							attemptConnect(adapter);
							break;
						}
					}
				}
				dialog.dismiss();
			}
		});
		return okButton;
	}
    
    private Button createCancelButton(final Dialog dialog) {
		Button cancelButton = new Button(this);
    	cancelButton.setText("Cancel");
    	cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return cancelButton;
	}
    
    private String getSpinnerSelection(Spinner spinner) {
    	return spinner.getSelectedItem().toString();
    }
    
    /*
     * Displays the username and password to the appropriate text fields.
     */
    public void initializeInformation() {
    	((EditText)findViewById(R.id.home_username)).setText(user.getUsername());
    	((EditText)findViewById(R.id.home_password)).setText(user.getPassword());
    	((EditText)findViewById(R.id.home_age)).setText(user.getAge() + "");
    	((EditText)findViewById(R.id.home_heightFeet)).setText(user.getHeightFeet() + "");
    	((EditText)findViewById(R.id.home_heightInches)).setText(user.getHeightInches() + "");
    	((EditText)findViewById(R.id.home_weight)).setText(user.getWeight() + "");
    	((EditText)findViewById(R.id.home_goalWeight)).setText(user.getGoalWeight() + "");
    }
    
    private AlertDialog.Builder createInputDialog(String title, String message) {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle(title);
    	alert.setMessage(message);
    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	
    	return alert;
    }
    
    public void changeUsername(View view) {
    	AlertDialog.Builder alert = createInputDialog("Change Username", "Type in the new username: ");
    	final EditText input = createInputField();
    	alert.setView(input);
    	
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@SuppressWarnings("null")
			public void onClick(DialogInterface dialog, int which) {
				String newUsername = input.getText().toString();
				if(newUsername != null || newUsername.length() != 0) {
					if(newUsername.length() <= 15) {
						user.setUsername(newUsername);
						dao.update(user);
						((EditText)findViewById(R.id.home_username)).setText(newUsername);
					}
					else showAlertMessage("Username must contain only up to 15 characters.", true);
				}
				else showAlertMessage("Username must not be empty.", true);
			}
		});
    	alert.show();
    }

	private EditText createInputField() {
		final EditText input = new EditText(this);
    	input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return input;
	}
    
    public void changePassword(View view) {
    	AlertDialog.Builder alert = createInputDialog("Change Password", "Type in the new password: ");
    	final EditText input = createInputField();
    	alert.setView(input);
    	
    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@SuppressWarnings("null")
			public void onClick(DialogInterface dialog, int which) {
				String newPassword = input.getText().toString();
				if(newPassword != null || newPassword.length() != 0) {
					if(newPassword.length() <= 15) {
						user.setPassword(newPassword);
						dao.update(user);
						((EditText)findViewById(R.id.home_password)).setText(newPassword);
					}
					else showAlertMessage("Password must contain only up to 15 characters.", true);
				}
				else showAlertMessage("Password must not be empty.", true);
			}
		});
    	alert.show();
    }
    
    // Updates the user information (age, weight, goal weight, height)
    public void update(View view) {
    	try {
	    	user.setAge(Integer.parseInt(((EditText)findViewById(R.id.home_age)).getText().toString()));
	    	user.setWeight(Integer.parseInt(((EditText)findViewById(R.id.home_weight)).getText().toString()));
	    	user.setGoalWeight(Integer.parseInt(((EditText)findViewById(R.id.home_goalWeight)).getText().toString()));
	    	user.setHeightFeet(Integer.parseInt(((EditText)findViewById(R.id.home_heightFeet)).getText().toString()));
	    	user.setHeightInches(Integer.parseInt(((EditText)findViewById(R.id.home_heightInches)).getText().toString()));
	    	dao.update(user);
	    	showAlertMessage("Information has been updated", true);
    	}
    	catch(NumberFormatException e) {
    		showAlertMessage("Make sure the information are filled in correctly.", true);
    	}
    }
    
    /*
     *  Calculates the user's BMI based on weight and height 
     *  then displays the result in a text field.
     */
    public void calculateBMI(View view) {
    	double index = calculateBMIIndex();
    	((EditText)findViewById(R.id.home_BMIindex)).setText(index + "");
    	((EditText)findViewById(R.id.home_BMIclassification)).setText(calculateBMIClassification(index));
    }
    
    // Calculates BMI Index and returns the result in one decimal place.
    public double calculateBMIIndex() {
    	double index = 0;
    	int heightInInches = (user.getHeightFeet() * 12) + user.getHeightInches();
    	if(heightInInches != 0) {
    		double squared = Math.pow(heightInInches, 2);
    		double preResult = user.getWeight() / squared;
    		double BMI_formula = preResult * 703;
    		index = Double.parseDouble(new DecimalFormat("#0.0").format(BMI_formula));
    	}
    	return index;
    }
    
    /*
     * Categorizes BMI Classification based on the BMI Index 
     * and returns the result as a String.
     */
    public String calculateBMIClassification(double index) {
    	String classification = "";
    	if(index < 18.5) classification = "Underweight";
    	else if(index >= 18.5 && index < 25) classification = "Normal";
    	else if(index >= 25 && index < 30) classification = "Overweight";
    	else if(index >= 30) classification = "Obese";
    	return classification;
    }
    
    // Adds the View elements inside the array into the layout
    public void addViewsToLayout(View[] views, ViewGroup layout) {
    	if(views != null && layout != null) {
    		for(View v : views) layout.addView(v);
    	}
    }

    // Returns a LinearLayout with the orientation passed in as parameter 
    public LinearLayout createLinearLayout(int orientation) {
    	LinearLayout view = new LinearLayout(this);
    	view.setOrientation(orientation);
    	return view;
    }
    
    // Shows an AlertDialog with the message passed in the parameter 
    public void showAlertMessage(String message, boolean cancelable) {
    	AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(cancelable);
		ad.setMessage(message);
		ad.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    public void setNavigationButtons() {
    	Button home = (Button)findViewById(R.id.navigation_home);
    	Button exercise = (Button)findViewById(R.id.navigation_exercise);
    	Button nutrition = (Button)findViewById(R.id.navigation_nutrition);
    	Button journal = (Button)findViewById(R.id.navigation_journal);
    	Button logout = (Button)findViewById(R.id.navigation_logout);
    	new NavigationFactory(this).setNavigationOnClick(home, exercise, nutrition, journal, logout);
    }
}
