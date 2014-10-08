package com.subinkrishna.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.subinkrishna.android.ui.widget.ParallaxHeadListView;

public class MainActivity extends Activity {

	private final static String[] values = { "Red", "Blue", "Green", "Black", "Cyan", 
		"White", "Orange", "Pink", "Grey", "Yellow", "Purple", "Violet", "Indigo", 
		"& many more..." };
	
	private ParallaxHeadListView mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Init the view instances
		mList = (ParallaxHeadListView) findViewById(R.id.list);
		
		// Prepare the view and event handlers
		prepareView();
	}
	
	private void prepareView() {
		// Prepare the list view 
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), 
				R.layout.item_list_item, values);
		final View headerView = getLayoutInflater().inflate(R.layout.item_parallax_header, null);
		mList.setParallaxHeader(headerView);
		mList.setAdapter(adapter);
	}
}
