package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Described£º¶ÑÒç³ö²âÊÔ
 * @VM args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails
 */
public class HeapOutOfMemory {

	public static void main(String[] args) {
		List<TestCase> cases = new ArrayList<TestCase>();
		while (true) {
			cases.add(new TestCase());
		}
	}
}

class TestCase {

}