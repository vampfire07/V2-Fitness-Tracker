package com.example.v2fitnesstracker;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ExerciseAdapter extends BaseAdapter {

	private ArrayList<String> type = new ArrayList<String>();
	
	public int getCount() {
		return this.type.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		final ViewHolder holder;
//	    if (convertView == null) 
//	    {
//	        convertView = mInflater.inflate(R.layout.contactcustomlist, null);
//	        holder = new ViewHolder();
//	        holder.Name = (TextView) convertView.findViewById(R.id.tvNameCustomContact);
//	        holder.Number= (TextView) convertView.findViewById(R.id.tvNumberCustomContact);
//	        holder.Group= (TextView) convertView.findViewById(R.id.tvGroupCustomContact);
//	        convertView.setTag(holder);
//	    } 
//	    else 
//	    {
//	        holder = (ViewHolder) convertView.getTag();
//	    }
		return null;
	}

}
