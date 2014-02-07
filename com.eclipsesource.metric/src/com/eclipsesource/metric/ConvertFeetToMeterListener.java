package com.eclipsesource.metric;

import roboguice.inject.InjectView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

public class ConvertFeetToMeterListener implements OnClickListener {

	private @Inject ConversionService conversionService;
	private @InjectView(R.id.feet_edit_text) EditText feetEditText;
	private @InjectView(R.id.result_text) TextView resultText;

	@Override
	public void onClick(View v) {
		try {
			float feet = Float.parseFloat(feetEditText.getText().toString());
			float meter = conversionService.convertFeetToMeter(feet);
			resultText.setText(feet + " feet is " + meter + " meter");
		} catch (NumberFormatException e) {
			// handle error
		}
	}
}