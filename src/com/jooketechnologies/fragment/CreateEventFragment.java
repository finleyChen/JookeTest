
package com.jooketechnologies.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jooketechnologies.jooketest.JookeApplication;
import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;
import com.jooketechnologies.jooketest.Utils;
import com.jooketechnologies.user.MySelf;

	public class CreateEventFragment extends Fragment {
		
		private Context mContext;
		public JookeApplication jookeApplication;
		public MySelf mMe;
		
		private Button mStartEventButton;
		private EditText mEventNameEditText;
		private EditText mHostIdEditText;
		private TextView mEventIDTextView;
		public ToggleButton mAllowAddSongsToggleButton;
		public ToggleButton mAllowVotingToggleButton;
		
		private String mEventNameString; 
		private String mEventModeString; 
		private String mEventZipCodeString;
		private String mEventTimeString;
		private String mHostIdString;
		private String mHostIpString;
		private String mLatString;
		private String mLonString;
		private String mAllowAddSongsString;
		public boolean isAllowAddSongs=true;
		public boolean isAllowVoting=true;
		
		public static final int SONG_SELECT_RESULT = 1000;

		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			mContext = getActivity();
			jookeApplication = (JookeApplication) getActivity().getApplication();
			mMe = jookeApplication.mMe;
			final View rootView = inflater.inflate(
					R.layout.fragment_createevent, container, false);
			
			mEventNameEditText = (EditText) rootView
					.findViewById(R.id.event_name_text_field);
			mHostIdEditText = (EditText) rootView.findViewById(R.id.host_id_text_field);
			mEventIDTextView = (TextView) rootView.findViewById(R.id.event_id);
			mAllowAddSongsToggleButton = (ToggleButton)rootView.findViewById(R.id.allow_addsongs);
			mAllowVotingToggleButton = (ToggleButton)rootView.findViewById(R.id.allow_voting);
			
		
			mStartEventButton = (Button) rootView
					.findViewById(R.id.start_event_button);
			
			
			
			
		

			mAllowAddSongsToggleButton.setOnClickListener(new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	isAllowAddSongs = !isAllowAddSongs;
			    }
			});
			mAllowVotingToggleButton.setOnClickListener(new OnClickListener()
			{
			    @Override
			    public void onClick(View v)
			    {
			    	isAllowVoting = !isAllowVoting;
			    }
			});
			
			mStartEventButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					
					if(mEventNameEditText.getText().toString()==null || mEventNameEditText.getText().toString().equals("")){
						new AlertDialog.Builder(mContext)
					    .setTitle("Missing info:")
					    .setMessage("Please name your event to continue.")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            dialog.cancel();
					        }
					     })
					    .setIcon(android.R.drawable.ic_dialog_alert)
					     .show();
					}
					else{
						
						new createEventTask(mHostIdEditText.getText().toString()).execute();
						SharedPreferences settings = mContext.getSharedPreferences("EventSettings", Context.MODE_PRIVATE);
					    SharedPreferences.Editor prefEditor = settings.edit();
					    prefEditor.putString("EventName", mEventNameEditText.getText().toString());
					    prefEditor.commit(); 
					}
					
				}
			});
		

			return rootView;
		}
		
		class createEventTask extends AsyncTask<Void, Void, String> {
			public String hostid;
			
			public createEventTask(String hostid) {
				this.hostid = hostid;
			}
			protected String doInBackground(Void... none) {
				try {
					
					
					mEventNameString      = mEventNameEditText.getText().toString();
					mEventModeString      = String.valueOf(isAllowVoting);
					mAllowAddSongsString  = String.valueOf(isAllowAddSongs);
					
					mEventZipCodeString   = jookeApplication.currentZipCode;
					mEventTimeString      = String.valueOf(System.currentTimeMillis());
					mHostIdString         = hostid;
					mHostIpString         = Utils.getIPAddress(true);
					mLatString            = String.valueOf(jookeApplication.currentLocation.getLatitude());
					mLonString            = String.valueOf(jookeApplication.currentLocation.getLongitude());
					
					
					return ServerUtilities.createEvent(mEventNameString, mEventModeString, mAllowAddSongsString, 
							mEventZipCodeString, mLatString,mLonString, mEventTimeString, mHostIdString, mHostIpString);
				} catch (Exception e) {
					return null;
				}
			}
			protected void onPostExecute(String event_id){
				if(event_id!=null){
					Log.e("eventid",event_id);
					mEventIDTextView.setText(event_id);
				}
			}

		};

	}

