package com.robustaweb.library.gwt;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.robustaweb.library.rest.representation.Representation;

public class GwtRepresentationTest extends GWTTestCase {

	static Representation[] representations;
	static String xml;
	static String json;
	static int asInt = 0;
	static float asFloat = 0f;

	private void create() {

		xml = "<resource>" + "<id>12</id>" + "<age>24</age>"
				+ "<firstName>John</firstName>" + "<lastName>Doe</lastName>"
				+ "<userName>johndoe</userName>" + "<numbers>1</numbers>"
				+ "<numbers>2</numbers>" + "<numbers>3</numbers>"
				+ "<numbers>4</numbers>" + "</resource>";

		json = "{resource : "
				+ "id : 12, age : 24, firstName : 'John', lastName : 'Doe', userName : 'johndoe', "
				+ " numbers : [1, 2, 3, 4]," + "}";

		// only JDOM now
		representations = new Representation[] { new GwtRepresentation(xml) };
	}

	@Override
	public String getModuleName() {
		return "com.robustaweb.library.Robusta";
	}

	@Test
	public void testXXX() {
		create();
		System.out.println("in method");
		assertTrue(true);
	}

	@Test
	public void testToString() {
		create();
		try {
			for (Representation rp : representations) {
				assertTrue (rp.toString().contains("<numbers>4"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGet() {
		create();
		try {
			for (Representation rp : representations) {
				System.out.println("before :" + xml);
				String res = rp.get("userName");
				System.out.println("after :" + xml);
				String expected = "johndoe";
				String errorMessage = "getting " + res + " for representation "
						+ rp.getClass().getName() + " instead of " + expected;
				assertEquals(errorMessage, expected, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testSet() {
		create();
		try {
			for (Representation rp : representations) {
				String expected = "Timbler";
				rp.set("motherName", expected);
				String res = rp.get("motherName");
				String errorMessage = "getting " + res + " for representation "
						+ rp.getClass().getName() + " instead of " + expected;
				assertEquals(errorMessage, expected, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetDocument() {
		create();
		try {
			for (Representation rp : representations) {
				String errorMessage = "getDocument() is null for representation "
						+ rp.getClass().getName();
				assertNotNull(errorMessage, rp.getDocument());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetNumber() {
		create();
		try {
			for (Representation rp : representations) {
				Long expectedId = 12L;
				Long resId = rp.getNumber("id");
				String errorMessage = "getting " + resId
						+ " for representation " + rp.getClass().getName()
						+ " instead of " + expectedId;
				assertEquals(errorMessage, expectedId, resId);

				Representation clone = rp.copy();
				clone.set("float", "12.2");
				float expected = 12.2f;
				float result = clone.getNumber("float", asFloat);
				errorMessage = "getting " + result + " for representation "
						+ clone.getClass().getName() + " instead of "
						+ expected;
				assertTrue(errorMessage, expected == result);// no epsilon test
																// here
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetOptionalValue() {
		create();
		try {
			for (Representation rp : representations) {
				String expected = "johndoe";
				String res = rp.getOptionalValue("userName");
				String errorMessage = "getting " + res + " for representation "
						+ rp.getClass().getName() + " instead of " + expected;
				assertEquals(errorMessage, expected, res);


				res = rp.getOptionalValue("usernameThatIsNotATag");
				errorMessage = "getting " + res + " for representation "
						+ rp.getClass().getName() + " instead of " + null;
				assertNull(errorMessage, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetValues() {
		create();
		try {
			for (Representation rp : representations) {
				List<String> res = rp.getValues("numbers");
				List<String> expected = Arrays.asList(new String[] { "1", "2",
						"3", "4" });
				String errorMessage = "getting " + res + " for representation "
						+ rp.getClass().getName() + " instead of " + expected;
				assertTrue(errorMessage, res.equals(expected));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetNumbers_String() {
		create();
		try {
			for (Representation rp : representations) {

				List<Long> resAsLong = rp.getNumbers("numbers");
				List<Integer> resAsInts = rp.getNumbers("numbers", asInt);
				List<Float> resAsFloats = rp.getNumbers("numbers", asFloat);
				List<Integer> expectedAsInts = Arrays.asList(new Integer[] { 1,
						2, 3, 4 });
				List<Long> expectedAsLong = Arrays.asList(new Long[] { 1L, 2L,
						3L, 4L });
				List<Float> expectedAsFloat = Arrays.asList(new Float[] { 1f,
						2f, 3f, 4f });

				String errorMessage = "For Integers : getting " + resAsInts
						+ " for representation " + rp.getClass().getName()
						+ " instead of " + expectedAsInts;
				assertTrue(errorMessage, resAsInts.equals(expectedAsInts));

				errorMessage = "For Longs : getting " + resAsLong
						+ " for representation " + rp.getClass().getName()
						+ " instead of " + expectedAsLong;
				assertTrue(errorMessage, resAsLong.equals(expectedAsLong));

				errorMessage = "For Floats : getting " + resAsFloats
						+ " for representation " + rp.getClass().getName()
						+ " instead of " + expectedAsFloat;
				assertTrue(errorMessage, resAsFloats.equals(expectedAsFloat));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
