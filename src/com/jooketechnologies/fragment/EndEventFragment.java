package com.jooketechnologies.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;


public class EndEventFragment extends Fragment {

	
	Button endevent;
	boolean isHeartbeating=false;
	Context mContext;
	TextView successText;
	
	public class endEventAsyncTask extends AsyncTask<Void, Void, String> {
		public String user_id;
		public String event_id;
		
		public endEventAsyncTask(String user_id, String event_id) {
			this.user_id = user_id;
			this.event_id = event_id;
		}

		protected String doInBackground(Void... none) {

			return ServerUtilities.leaveEvent(user_id,event_id);
		}

		protected void onPostExecute(String success){
			if(success!=null)
				successText.setText(success);
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		final View rootView = inflater.inflate(R.layout.fragment_endevent,
				container, false);
		successText = (TextView) rootView.findViewById(R.id.success);
		endevent = (Button)rootView.findViewById(R.id.endevent);
		endevent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				View dialog_layout = ((Activity) mContext).getLayoutInflater()
						.inflate(R.layout.dialog_gethost, null);
				alert.setTitle("Input eventid");
			
				final EditText userid = (EditText) dialog_layout
						.findViewById(R.id.hostid);
				final EditText eventid = (EditText) dialog_layout
						.findViewById(R.id.eventid);
				
				alert.setView(dialog_layout);
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String userIdString = userid.getText().toString();
								String eventIdString = eventid.getText().toString();
								new endEventAsyncTask(userIdString,eventIdString)
								.execute();
								
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
		});
		return rootView;
	}


}