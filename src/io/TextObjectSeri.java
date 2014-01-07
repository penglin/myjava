package io;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

public class TextObjectSeri {
	@Test
	public void write() throws IOException {
		/*ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(new A("lin"));
		oo.writeObject(new B("penglin","23"));

		FileOutputStream out = new FileOutputStream("test");
		out.write(bo.toByteArray());
		out.close();*/
		
		FileOutputStream fos = new FileOutputStream("test");
        ObjectOutputStream oo = new ObjectOutputStream(fos);
        oo.writeObject(new A("lin"));
		oo.writeObject(new B("penglin","23"));
//        oo.writeObject(1);
		oo.close();
		
		System.out.println(new File("test").length());
	}

	@Test
	public void read() throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream("test");
		
		ObjectInputStream oi = new ObjectInputStream(in);
		Object obj = null;
		try {
			while((obj=oi.readObject())!=null){
				if(obj instanceof B){
					B b = (B) obj;
					System.out.println(b.getB());
				}else{
					A a = (A) obj;
					System.out.println(a.getA());
				}
			}
		} catch (EOFException e) {
			e.printStackTrace();
		}

		oi.close();
		System.out.println("end");
	}
}

class A implements Serializable {
	private String a;

	public A(String a) {
		this.a = a;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}

class B extends A{
	private String b ;
	public B(String a) {
		super(a);
	}
	
	public B(String a, String b) {
		super(a);
		this.b = b;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}