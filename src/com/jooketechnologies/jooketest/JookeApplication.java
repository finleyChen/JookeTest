package com.jooketechnologies.jooketest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.location.Location;

import com.jooketechnologies.user.MySelf;


public class JookeApplication extends Application {

	ArrayList<HashMap<String, String>> arraylist;
	public boolean isInEvent;

	public MySelf mMe;

	public static JookeApplication jookeApplication = null;
	public Location currentLocation;
	public String currentZipCode;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	

	public void setIsInEvent(boolean isInEvent) {
		this.isInEvent = isInEvent;
	}

	public boolean isInEvent() {
		return isInEvent;
	}
}