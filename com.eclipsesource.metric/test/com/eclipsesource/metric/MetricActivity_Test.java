package com.eclipsesource.metric;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipsesource.metric.test.TestGuiceModule;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowView;

@RunWith(RobolectricTestRunner.class)
public class MetricActivity_Test {

	@Mock ConvertFeetToMeterListener listener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		TestGuiceModule module = new TestGuiceModule();
		module.addBinding(ConvertFeetToMeterListener.class, listener);
		TestGuiceModule.setUp(this, module);
	}

	@After
	public void tearDown() {
		TestGuiceModule.tearDown();
	}

	@Test
	public void onCreateShouldAttachConversionListenerToConvertButton() {
		MetricActivity activity = new MetricActivity();

		activity.onCreate(null);

		ShadowView button = Robolectric.shadowOf(activity.findViewById(R.id.convert_button));
		assertEquals(listener, button.getOnClickListener());
	}

}
