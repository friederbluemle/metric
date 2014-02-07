package com.eclipsesource.metric.roboguice;

import com.eclipsesource.metric.ConversionService;
import com.google.inject.Provider;

public class ConversionServiceProvider implements Provider<ConversionService> {

	private static final float FOOT_IN_METER = 0.3048f;

	@Override
	public ConversionService get() {
		return new ConversionService(0.3048f);
	}

}
