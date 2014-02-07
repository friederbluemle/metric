package com.eclipsesource.metric.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import roboguice.RoboGuice;
import roboguice.config.DefaultRoboModule;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;
import roboguice.inject.RoboInjector;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.eclipsesource.metric.roboguice.MetricGuiceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;

public class TestGuiceModule extends AbstractModule {

	private final class ViewTypeListener implements TypeListener {

		@Override
		public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
			for (Class<?> c = typeLiteral.getRawType(); c != Object.class; c = c.getSuperclass()) {
				for (Field field : c.getDeclaredFields()) {
					if (field.isAnnotationPresent(InjectView.class)) {
						if (Modifier.isStatic(field.getModifiers())) {
							throw new UnsupportedOperationException(
									"Views may not be statically injected");
						} else if (!View.class.isAssignableFrom(field.getType())) {
							throw new UnsupportedOperationException(
									"You may only use @InjectView on fields descended from type View");
						} else if (Context.class.isAssignableFrom(field.getDeclaringClass())
								&& !Activity.class.isAssignableFrom(field.getDeclaringClass())) {
							throw new UnsupportedOperationException(
									"You may only use @InjectView in Activity contexts");
						} else {
							typeEncounter.register(new TestViewMembersInjector<I>(viewBindings,
									field));
						}
					}
				}
			}
		}
	}

	private HashMap<Class<?>, Object> bindings;
	private SparseArray<Object> viewBindings;

	public TestGuiceModule() {
		bindings = new HashMap<Class<?>, Object>();
		viewBindings = new SparseArray<Object>();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void configure() {
		bind(Activity.class).toProvider(ActivityProvider.class).in(ContextSingleton.class);
		Set<Entry<Class<?>, Object>> entries = bindings.entrySet();
		for (Entry<Class<?>, Object> entry : entries) {
			bind((Class<Object>) entry.getKey()).toInstance(entry.getValue());
		}
		bindListener(Matchers.any(), new ViewTypeListener());
	}

	public void addBinding(Class<?> type, Object object) {
		bindings.put(type, object);
	}

	public void addViewBinding(int id, Object object) {
		viewBindings.put(id, object);
	}

	public static void setUp(Object testObject, TestGuiceModule module) {
		DefaultRoboModule roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
		Module productionModule = Modules.override(roboGuiceModule).with(new MetricGuiceModule());
		Module testModule = Modules.override(productionModule).with(module);
		RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE,
				testModule);
		RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
		injector.injectMembers(testObject);
	}

	public static void tearDown() {
		RoboGuice.util.reset();
		Application app = Robolectric.application;
		DefaultRoboModule defaultModule = RoboGuice.newDefaultRoboModule(app);
		RoboGuice.setBaseApplicationInjector(app, RoboGuice.DEFAULT_STAGE, defaultModule);
	}

}
