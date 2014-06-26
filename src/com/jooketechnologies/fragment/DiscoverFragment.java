
package com.jooketechnologies.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jooketechnologies.event.Event;
import com.jooketechnologies.jooketest.Constants;
import com.jooketechnologies.jooketest.JookeApplication;
import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;

public class DiscoverFragment extends ListFragment {

	ArrayList<HashMap<String, String>> feedsList;
	ListView list;
	DiscoverAdapter adapter;
	Button discoverButton;
	Context mContext;
	Context mContextThemeWrapper;
	JookeApplication jookeApplication;
	
	public void populateList(ArrayList<Event> eventList) {
		feedsList = new ArrayList<HashMap<String, String>>();
		
		for (Event event:eventList){
			HashMap<String, String> map = new HashMap<String, String>();

			// adding each child node to HashMap key => value
			map.put(Constants.KEY_DISCOVER_EVENT_NAME, event.eventName);
			map.put(Constants.KEY_DISCOVER_HOST_NAME, event.hostName);
			map.put(Constants.KEY_HOME_SUBJECT1_PROFILE_IMG, event.hostProfileImgUrl);
			map.put(Constants.KEY_ALLOW_ADDSONGS, event.allowAddSongs);
			map.put(Constants.KEY_EVENT_MODE, event.eventMode);
			map.put(Constants.KEY_HOST_ID, event.hostId);
			map.put(Constants.KEY_EVENT_ID, event.eventId);
			
			// adding HashList to ArrayList
			feedsList.add(map);
		}

		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e("onCreate","onCreate");
		
		jookeApplication=(JookeApplication)getActivity().getApplication();
	    
		mContext = getActivity();
		super.onCreate(savedInstanceState);
		
	
		
		setHasOptionsMenu(true);
		setMenuVisibility(true); 
		
	}
	class discoverNearbyEventTask extends AsyncTask<Void, Void, ArrayList<Event>> {
		String userid;
		public discoverNearbyEventTask(String userid){
			this.userid = userid;
		}
		protected ArrayList<Event> doInBackground(Void... none) {
			try {
				if(jookeApplication.currentLocation==null){
					Log.e("null","location is null");
				}
				return ServerUtilities.discoverNearbyEvents(String.valueOf(jookeApplication.currentLocation.getLatitude()),
						String.valueOf(jookeApplication.currentLocation.getLongitude()), jookeApplication.currentZipCode, userid);

			} catch (Exception e) {
				return null;
			}
		}
		protected void onPostExecute(ArrayList<Event> eventList){
			if(eventList!=null){
				//update the view here.
				//call populate list here.
				Log.e("event list","event list");
				populateList(eventList);
				adapter = new DiscoverAdapter(getActivity(), feedsList);
				setListAdapter(adapter);
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_discover,
				container, false);
		discoverButton = (Button)rootView.findViewById(R.id.discover);
		discoverButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

				alert.setTitle("User id");
				// Set an EditText view to get user input 
				final EditText userid = new EditText(mContext);
				alert.setView(userid);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String userIdString = userid.getText().toString();
				  // Do something with value!
					new discoverNearbyEventTask(userIdString).execute();
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
			}
		});
		list = (ListView) rootView.findViewById(android.R.id.list);
		
		return rootView;
	}

}