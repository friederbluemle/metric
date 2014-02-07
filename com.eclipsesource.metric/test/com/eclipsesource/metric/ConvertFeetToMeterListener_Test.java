package com.eclipsesource.metric;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eclipsesource.metric.test.TestGuiceModule;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ConvertFeetToMeterListener_Test {

	private @Inject ConvertFeetToMeterListener listener;
	private @Mock ConversionService conversionService;
	private @Mock TextView resultText;
	private EditText feetEditText;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Activity anyActivity = mock(Activity.class);
		feetEditText = new EditText(anyActivity);
		TestGuiceModule module = new TestGuiceModule();
		module.addBinding(ConversionService.class, conversionService);
		module.addViewBinding(R.id.feet_edit_text, feetEditText);
		module.addViewBinding(R.id.result_text, resultText);
		TestGuiceModule.setUp(this, module);
	}

	@After
	public void tearDown() {
		TestGuiceModule.tearDown();
	}

	@Test
	public void onClickShouldConvertFeedAndSetResult() {
		feetEditText.setText("3");
		when(conversionService.convertFeetToMeter(3f)).thenReturn(0.9144f);

		listener.onClick(mock(View.class));

		verify(resultText).setText("3.0 feet is 0.9144 meter");
	}

}
