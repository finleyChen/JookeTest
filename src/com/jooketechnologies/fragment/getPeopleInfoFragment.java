package com.jooketechnologies.fragment;

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
import com.jooketechnologies.user.User;

// Extra credit: add a birthday field using DatePicker, can save and load
// You can find sample code here:
// http://developer.android.com/resources/tutorials/views/hello-datepicker.html

public class getPeopleInfoFragment extends Fragment {

	Context mContext;
	Button getpeople;
	TextView fullNameTextView;
	TextView facebookTextView;
	TextView twitterTextView;
	TextView instagramTextView;
	TextView userProfileTextView;
	
	public class getPeopleIpAsyncTask extends AsyncTask<Void, Void, User> {
		public String userId;
		
		public getPeopleIpAsyncTask(String userId) {
			this.userId = userId;
		}
		protected User doInBackground(Void... none) {
			return ServerUtilities.
					getProfile(userId);
		}
		protected void onPostExecute(User user) {
			Log.e("onPostExecute","onPostExecute");
			if (user.userName==null){
				fullNameTextView.setText("null");
			}else{
				fullNameTextView.setText(user.userName);
			}
			if (user.facebookUrl==null){
				facebookTextView.setText("null");
			}else{
				facebookTextView.setText(user.facebookUrl);
			}
			if (user.instagramUrl==null){
				instagramTextView.setText("null");
			}else{
				instagramTextView.setText(user.instagramUrl);
			}
			if (user.twitterUrl==null){
				twitterTextView.setText("null");
			}else{
				twitterTextView.setText(user.twitterUrl);
			}
			if (user.userProfileImgUrl==null){
				userProfileTextView.setText("null");
			}else{
				userProfileTextView.setText(user.userProfileImgUrl);
			}
	    }
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		
		final View rootView = inflater.inflate(R.layout.fragment_people,
				container, false);
		fullNameTextView = (TextView) rootView.findViewById(R.id.fullname);
		facebookTextView = (TextView) rootView.findViewById(R.id.facebook_link);
		twitterTextView = (TextView) rootView.findViewById(R.id.twitter_link);
		instagramTextView = (TextView) rootView.findViewById(R.id.instagram_link);
		userProfileTextView = (TextView) rootView.findViewById(R.id.profile_img);
		getpeople = (Button)rootView.findViewById(R.id.getpeople);
		getpeople.setOnClickListener(new View.OnClickListener() {

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
				  new getPeopleIpAsyncTask(userIdString)
					.execute();
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
		
		return rootView;
	}


}