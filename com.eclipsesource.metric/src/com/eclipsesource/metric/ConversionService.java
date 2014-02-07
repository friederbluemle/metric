package com.eclipsesource.metric;

public class ConversionService {

	private float footInMeter;

	public ConversionService(float footInMeter) {
		this.footInMeter = footInMeter;
	}

	public float convertFeetToMeter(float feet) {
		return feet * footInMeter;
	}
}
