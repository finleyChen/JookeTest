package com.jooketechnologies.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jooketechnologies.jooketest.Constants;
import com.jooketechnologies.jooketest.ImageLoader;
import com.jooketechnologies.jooketest.R;

public class DiscoverAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public DiscoverAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		final NewsHolder holder;
		if (vi == null) {
			vi = inflater.inflate(R.layout.discover_list_item, null);

			holder = new NewsHolder();

			holder.eventname = (TextView) vi.findViewById(R.id.event);
			holder.hostname = (TextView) vi.findViewById(R.id.host);
			holder.hostimage = (ImageView) vi.findViewById(R.id.host_image);

			vi.setTag(holder);
		} else {
			holder = (NewsHolder) vi.getTag();
		}

		HashMap<String, String> event = new HashMap<String, String>();
		event = data.get(position);

		// Setting all values in listview
		holder.eventname.setText(event.get(Constants.KEY_DISCOVER_EVENT_NAME));
		holder.hostname.setText(event.get(Constants.KEY_DISCOVER_HOST_NAME));
		holder.event_id = event.get(Constants.KEY_EVENT_ID);
		holder.event_mode = Boolean
				.valueOf(event.get(Constants.KEY_EVENT_MODE));
		holder.allow_addsongs = Boolean.valueOf(event
				.get(Constants.KEY_ALLOW_ADDSONGS));
		holder.host_id = event.get(Constants.KEY_HOST_ID);

		imageLoader.DisplayImage(
				event.get(Constants.KEY_HOME_SUBJECT1_PROFILE_IMG),
				holder.hostimage, true);

		return vi;

	}

	static class NewsHolder {

		TextView eventname;
		TextView hostname;
		ImageView hostimage;
		Button event_info;
		Button join_event;
		String event_id;
		boolean allow_addsongs;
		boolean event_mode;
		String host_id;
	}
}