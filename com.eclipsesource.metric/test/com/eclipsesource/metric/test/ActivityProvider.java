package com.eclipsesource.metric.test;

import com.eclipsesource.metric.MetricActivity;
import com.google.inject.Provider;

public class ActivityProvider implements Provider<MetricActivity> {

	@Override
	public MetricActivity get() {
		return new MetricActivity();
	}

}
