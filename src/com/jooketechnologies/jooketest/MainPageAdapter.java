package com.jooketechnologies.jooketest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jooketechnologies.fragment.CreateEventFragment;
import com.jooketechnologies.fragment.DiscoverFragment;
import com.jooketechnologies.fragment.EndEventFragment;
import com.jooketechnologies.fragment.ForgetPasswordFragment;
import com.jooketechnologies.fragment.HeartBeatFragment;
import com.jooketechnologies.fragment.LoginFragment;
import com.jooketechnologies.fragment.ProfileFragment;
import com.jooketechnologies.fragment.SignupFragment;
import com.jooketechnologies.fragment.getHostIpFragment;
import com.jooketechnologies.fragment.getPeopleInfoFragment;

public class MainPageAdapter extends FragmentPagerAdapter {

	private int mCount = 10;

	public MainPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new SignupFragment();
		case 1:
			return new LoginFragment();
		case 2:
			return new ProfileFragment();
		case 3:
			return new HeartBeatFragment();
		case 4:
			return new getHostIpFragment();
		case 5:
			return new getPeopleInfoFragment();
		case 6:
			return new ForgetPasswordFragment();
		case 7:
			return new DiscoverFragment();
		case 8: 
			return new CreateEventFragment();
		case 9:
			return new EndEventFragment();

	
		
		}
		return null;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

}