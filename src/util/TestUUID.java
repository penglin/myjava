package util;

import java.util.Random;
import java.util.UUID;


public class TestUUID {
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		System.out.println(uuid.toString().length());
		
		Random r = new Random();
		System.out.println(r.nextInt(5));
	}
	
}
