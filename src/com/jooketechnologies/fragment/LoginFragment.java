package com.jooketechnologies.fragment;

import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jooketechnologies.jooketest.Constants;
import com.jooketechnologies.jooketest.JookeApplication;
import com.jooketechnologies.jooketest.R;
import com.jooketechnologies.jooketest.ServerUtilities;
import com.jooketechnologies.user.MySelf;

/**
 * A fragment that launches other parts of the demo application.
 */
public class LoginFragment extends Fragment {

	public JookeApplication jookeApplication;
	public MySelf mMe;
	private Context mContext;
	public Button normalLoginButton;
	private TextView successText;
	private static SharedPreferences mSharedPreferences;

	public static final int SONG_SELECT_RESULT = 1000;



	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		jookeApplication = (JookeApplication) getActivity().getApplication();
		mSharedPreferences = getActivity().getApplicationContext()
				.getSharedPreferences("MyPref", 0);
		mMe = jookeApplication.mMe;
		final View rootView = inflater.inflate(R.layout.fragment_login,
				container, false);
		successText = (TextView) rootView.findViewById(R.id.success);
		normalLoginButton = (Button) rootView.findViewById(R.id.login_normal);
		normalLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				View dialog_layout = ((Activity) mContext).getLayoutInflater()
						.inflate(R.layout.dialog_login_normal, null);
				alert.setTitle("Login");
				final EditText email = (EditText) dialog_layout
						.findViewById(R.id.email);
				final EditText password = (EditText) dialog_layout
						.findViewById(R.id.password);
				alert.setView(dialog_layout);
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String emailString = email.getText().toString();
								String passwordString = password.getText()
										.toString();
								Log.e("email password", emailString + ","
										+ passwordString);
								new loginAsyncTask(emailString, passwordString,
										Constants.SIGNUP_TYPE_NORMAL, null)
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

	
	public class loginAsyncTask extends AsyncTask<Void, Void, String> {
		public String email;
		public String password;
		public int signin_type;
		public String thirdparty_id;

		public loginAsyncTask(String email, String password, int signin_type,
				String thirdparty_id) {
			this.email = email;
			this.password = password;
			this.signin_type = signin_type;
			this.thirdparty_id = thirdparty_id;
		}

		protected String doInBackground(Void... none) {

			return ServerUtilities.logIn(email, password, signin_type,
					thirdparty_id);
		}

		protected void onProgressUpdate(String success) {
			if(success!=null)
				successText.setText(success);
	    }

	}
}
