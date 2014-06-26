package com.jooketechnologies.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;
import com.jooketechnologies.jooketest.Utils;

// Extra credit: add a birthday field using DatePicker, can save and load
// You can find sample code here:
// http://developer.android.com/resources/tutorials/views/hello-datepicker.html

public class HeartBeatFragment extends Fragment {

	
	Button startHeartBeat;
	EditText userIdEditText;
	String userIdString;
	Context mContext;
	boolean isHeartbeating=false;
	String useridString;
	String eventIdString;
	final private long mHeartbeatRate = 5*1000;
	private Handler mHeartbeatHandler = new Handler();
	private Runnable mHeatbeatTask = new Runnable(){
		public void run(){
			heartbeatSchedule();
			new heartbeatAsyncTask(useridString, eventIdString, Utils.getIPAddress(true))
			.execute();
			Log.e("heartbeat","heartbeat");
		}
	};
	
	public class heartbeatAsyncTask extends AsyncTask<Void, Void, Void> {
		public String userid;
		public String eventid;
		public String userip;
		
		public heartbeatAsyncTask(String userid, String eventid, String userip) {
			this.eventid = eventid;
			this.userid = userid;
			this.userip = userip;
		}

		protected Void doInBackground(Void... none) {
			Log.e("Utils.getIPAddress(true)",Utils.getIPAddress(true));
			ServerUtilities.
					heartBeat(userid, userip, eventid);
			return null;
		}
	}
	

	private synchronized void heartbeatSchedule() {
		mHeartbeatHandler.removeCallbacks(mHeatbeatTask);
		
		mHeartbeatHandler.postDelayed(mHeatbeatTask, mHeartbeatRate);

	}
	private synchronized void removeHeartbeatSchedule() {
		mHeartbeatHandler.removeCallbacks(mHeatbeatTask);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		final View rootView = inflater.inflate(R.layout.fragment_heartbeat,
				container, false);
		startHeartBeat = (Button)rootView.findViewById(R.id.heartbeat);
		
		startHeartBeat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				isHeartbeating = !isHeartbeating;
				if(isHeartbeating){
					startHeartBeat.setText("Stop Heartbeat");
					AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
					View dialog_layout = ((Activity) mContext).getLayoutInflater()
							.inflate(R.layout.dialog_heartbeat, null);
					alert.setTitle("Login");
				
					final EditText userid = (EditText) dialog_layout
							.findViewById(R.id.userid);
					final EditText eventid = (EditText) dialog_layout
							.findViewById(R.id.eventid);
					
					alert.setView(dialog_layout);
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									useridString = userid.getText().toString();
									eventIdString = eventid.getText()
											.toString();
									heartbeatSchedule();
									
								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Canceled.
									
								}
							});

					alert.show();
					
				}
				else{
					startHeartBeat.setText("Start Heartbeat");
					removeHeartbeatSchedule();
				}
			}
		});
		return rootView;
	}


}