
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

import com.jooketechnologies.jooketest.JookeApplication;
import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;
import com.jooketechnologies.jooketest.SharedPreferenceUtils;
/**
	 * A fragment that launches other parts of the demo application.
	 */
	public class SignupFragment extends Fragment {
		
		public JookeApplication jookeApplication;
		
		public Button normalSignupButton;
		public Button facebookSignupButton;
		public Button twitterSignupButton;
		public Context mContext;
		public static final int SONG_SELECT_RESULT = 1000;
		TextView successText;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			jookeApplication = (JookeApplication) getActivity().getApplication();
			mContext = getActivity();
			
			final View rootView = inflater.inflate(
					R.layout.fragment_signup, container, false);
	
			normalSignupButton = (Button)rootView.findViewById(R.id.signup_normal);
			normalSignupButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
					View dialog_layout = ((Activity) mContext).getLayoutInflater()
							.inflate(R.layout.dialog_signup_normal, null);
					alert.setTitle("Signup");
					final EditText email = (EditText) dialog_layout
							.findViewById(R.id.email);
					final EditText password = (EditText) dialog_layout
							.findViewById(R.id.password);
					final EditText fullname = (EditText) dialog_layout
							.findViewById(R.id.fullname);
					alert.setView(dialog_layout);
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String emailString = email.getText().toString();
									String passwordString = password.getText()
											.toString();
									String fullnameString = fullname.getText().toString();
									new signupAsyncTask(emailString.toString(), passwordString.toString(),
											fullnameString.toString())
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

		
		
		public class signupAsyncTask extends AsyncTask<Void, Void, Void> {
			public String email;
			public String password;
			public String fullname;
			public String thirdparty_id;
			
			public signupAsyncTask(String email, String password, String fullname) {
				this.email = email;
				this.password = password;
				this.fullname = fullname;
			}

			protected Void doInBackground(Void... none) {
				
				String userid = ServerUtilities.signUp(email, 0, fullname
						.toString(), null, password, null);
				if(userid!=null){
					SharedPreferenceUtils.storeJookeUserId(mContext, userid);
				}
				else{
					Log.e("shuold not happen","should not happen");
				}
				return null;
			}
			protected void onPostExecute(String success) {
				if(success!=null)
					successText.setText(success);
			}
		}
		
}