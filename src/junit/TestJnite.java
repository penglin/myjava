package junit;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

public class TestJnite extends TestCase {
	public void testEmptyCollection() {
		Collection collection = new ArrayList();
		assertTrue(collection.isEmpty());
	}
	
	public void testAdd(){
		System.out.println("add your monther");
	}
}
