package com.eclipsesource.metric.test;

import java.lang.reflect.Field;

import roboguice.inject.InjectView;
import android.util.SparseArray;

import com.google.inject.MembersInjector;

public class TestViewMembersInjector<T> implements MembersInjector<T> {

	private SparseArray<Object> viewBindings;
	private Field field;

	public TestViewMembersInjector(SparseArray<Object> viewBindings, Field field) {
		this.viewBindings = viewBindings;
		this.field = field;
	}

	@Override
	public void injectMembers(T instance) {
		InjectView injectView = field.getAnnotation(InjectView.class);
		final int id = injectView.value();
		field.setAccessible(true);
		Object view = viewBindings.get(id);
		if (view != null) {
			try {
				field.set(instance, view);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

}