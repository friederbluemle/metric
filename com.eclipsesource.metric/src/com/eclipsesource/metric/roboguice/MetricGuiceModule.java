package com.eclipsesource.metric.roboguice;

import com.eclipsesource.metric.ConversionService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MetricGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConversionService.class).toProvider(ConversionServiceProvider.class).in(
				Singleton.class);
	}

}
