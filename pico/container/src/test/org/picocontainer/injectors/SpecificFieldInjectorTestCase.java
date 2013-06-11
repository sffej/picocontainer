package org.picocontainer.injectors;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoCompositionException;

public class SpecificFieldInjectorTestCase {


	public static class TestInjection {
		
		public static String something;
		
		public String somethingElse;
		
	}
	
	private Field somethingField = null;
	
	private Field somethingElseField = null;
	
	@Before
	public void setupReflectionFields() throws NoSuchFieldException{
		somethingField = TestInjection.class.getDeclaredField("something");
		 somethingElseField = TestInjection.class.getDeclaredField("somethingElse");
	}

	@Test
	public void testStaticInjection() throws NoSuchFieldException {
		MutablePicoContainer pico = new DefaultPicoContainer().addComponent(String.class,"Testing");
		
		SpecificFieldInjector<TestInjection> adapter = new SpecificFieldInjector<TestInjection>(TestInjection.class, TestInjection.class, somethingField);
		adapter.injectStatics(pico, null);
		
		assertEquals("Testing", TestInjection.something);
	}

	
	@Test
	public void testNonStaticInjection() throws NoSuchFieldException {
		TestInjection.something = null;
		MutablePicoContainer pico = new DefaultPicoContainer().addComponent(String.class,"Testing");
		
		SpecificFieldInjector<TestInjection> adapter = new SpecificFieldInjector<TestInjection>(TestInjection.class, TestInjection.class, somethingElseField);
		TestInjection ti = adapter.getComponentInstance(pico, null);
		assertNotNull(ti);
		assertNull(TestInjection.something);
		assertEquals("Testing", ti.somethingElse);
	}
	
	@Test(expected=PicoCompositionException.class)
	public void testMixingStaticAndNotStaticFieldsResultsInPicoCompositionException() {
		new SpecificFieldInjector<TestInjection>(TestInjection.class, TestInjection.class, somethingField, somethingElseField);
	}
	
	@Test(expected=PicoCompositionException.class)
	public void testCallingInjectStaticsWithNonStaticFieldsThrowsCompositionException() {
		SpecificFieldInjector<TestInjection> adapter = new SpecificFieldInjector<TestInjection>(TestInjection.class, TestInjection.class, somethingElseField);
		adapter.injectStatics(null, null);
	}
	
	
	@Test(expected=PicoCompositionException.class)
	public void testCallingGetComponetInstanceWithStaticFieldsThrowsCompositionException() {
		SpecificFieldInjector<TestInjection> adapter = new SpecificFieldInjector<TestInjection>(TestInjection.class, TestInjection.class, somethingField);
		adapter.getComponentInstance(null, null);
	}	
}
