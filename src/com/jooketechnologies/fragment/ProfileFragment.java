package com.jooketechnologies.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ProfileFragment extends Fragment {

	EditText instagramEditText;
	EditText facebookEditText;
	EditText twitterEditText;
	EditText profileImgEditText;
	EditText idEditText;
	EditText userIdText;
	EditText fullnameEditText;
	Button saveButton;
	TextView successText;
	
	public class saveProfileAsyncTask extends AsyncTask<Void, Void, String> {
		public String user_id;
		public String full_name;
		public String instagram_link;
		public String facebook_link;
		public String twitter_link;
		public String profile_img;
		
		public saveProfileAsyncTask(String user_id, String full_name, String instagram_link,
				String facebook_link, String twitter_link, String profile_img) {
			this.user_id = user_id;
			this.full_name = full_name;
			this.instagram_link =instagram_link;
			this.facebook_link =facebook_link;
			this.twitter_link =twitter_link;
			this.profile_img =twitter_link;
		}

		protected String doInBackground(Void... none) {

			return String.valueOf(ServerUtilities.saveProfile( user_id,  full_name,
					 instagram_link,  facebook_link,  twitter_link,
					 profile_img));
		}

		protected void onPostExecute(String success) {
			if(success!=null)
				successText.setText(success);
		}

	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_profile,
				container, false);
		instagramEditText = (EditText)rootView.findViewById(R.id.instagram_link);
		facebookEditText = (EditText)rootView.findViewById(R.id.facebook_link);
		twitterEditText = (EditText)rootView.findViewById(R.id.twitter_link);
		fullnameEditText = (EditText)rootView.findViewById(R.id.fullname);
		idEditText = (EditText)rootView.findViewById(R.id.id);
		
		saveButton = (Button)rootView.findViewById(R.id.save);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new saveProfileAsyncTask(idEditText.getText().toString(),fullnameEditText.getText().toString(),instagramEditText.getText().toString(),facebookEditText.getText().toString(),
						twitterEditText.getText().toString(),profileImgEditText.getText().toString()).execute();
			}
		});
		profileImgEditText = (EditText)rootView.findViewById(R.id.profile_img);
		successText = (TextView) rootView.findViewById(R.id.success);
		
		return rootView;
	}


}