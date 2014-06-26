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

// Extra credit: add a birthday field using DatePicker, can save and load
// You can find sample code here:
// http://developer.android.com/resources/tutorials/views/hello-datepicker.html

public class getHostIpFragment extends Fragment {

	String hostIdString;
	String eventIdString;
	Button getHost;
	Context mContext;
	TextView hostIpTextView;
	
	public class getHostIpAsyncTask extends AsyncTask<Void, Void, String> {
		public String hostId;
		public String eventId;
		
		public getHostIpAsyncTask(String hostId, String eventId) {
			this.hostId = hostId;
			this.eventId = eventId;
		}

		protected String doInBackground(Void... none) {
			return ServerUtilities.
			getHostIp(hostId,eventId);
		}
		protected void onPostExecute(String hostip) {
			hostIpTextView.setText(hostip);
			Log.e("setTextView","setTextView:"+hostip);
	    }
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		final View rootView = inflater.inflate(R.layout.fragment_gethost,
				container, false);
		hostIpTextView = (TextView)rootView.findViewById(R.id.hostip);
		getHost = (Button)rootView.findViewById(R.id.gethost);
		getHost.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				View dialog_layout = ((Activity) mContext).getLayoutInflater()
						.inflate(R.layout.dialog_gethost, null);
				alert.setTitle("Input eventid");
			
				final EditText hostid = (EditText) dialog_layout
						.findViewById(R.id.hostid);
				final EditText eventid = (EditText) dialog_layout
						.findViewById(R.id.eventid);
				
				alert.setView(dialog_layout);
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								hostIdString = hostid.getText().toString();
								eventIdString = eventid.getText().toString();
								Log.e("afaf","aflfndfa");
								new getHostIpAsyncTask(hostIdString,eventIdString)
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