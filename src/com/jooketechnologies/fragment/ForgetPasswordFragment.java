package com.jooketechnologies.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ForgetPasswordFragment extends Fragment {

	Button forgetPassword;
	Context mContext;
	String emailString;
	TextView successText;

	public class forgetPasswordAsyncTask extends AsyncTask<Void, Void, String> {
		public String email;

		public forgetPasswordAsyncTask(String email) {
			this.email = email;
		}

		protected String doInBackground(Void... none) {

			return ServerUtilities.forgetPassword(email);
		}
		protected void onProgressUpdate(String success) {
			if(success!=null)
				successText.setText(success);
	    }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		final View rootView = inflater.inflate(R.layout.fragment_forget,
				container, false);
		
		forgetPassword = (Button) rootView.findViewById(R.id.forget);
		successText = (TextView) rootView.findViewById(R.id.success);
		
		forgetPassword.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				View dialog_layout = ((Activity) mContext).getLayoutInflater().inflate(
						R.layout.dialog_forget, null);
				alert.setTitle("Forget Password");

				final EditText email = (EditText) dialog_layout
						.findViewById(R.id.email);

				alert.setView(dialog_layout);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						emailString = email.getText().toString();
						
						new forgetPasswordAsyncTask(emailString).execute();

					}
				});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
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