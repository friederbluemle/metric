package com.eclipsesource.metric;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.inject.Inject;

public class MetricActivity extends RoboActivity {

	private @InjectView(R.id.convert_button) ImageButton convertButton;
	private @Inject ConvertFeetToMeterListener listener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		convertButton.setOnClickListener(listener);
	}
}
