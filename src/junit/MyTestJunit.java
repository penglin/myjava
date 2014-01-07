package junit;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MyTestJunit {
	public static Test suite() {
		return new TestSuite(TestJnite.class);
	}
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}
}	
