package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class JarUtil {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		JarFile jarFile = new JarFile("test.txt");
//		JarInputStream in = new JarInputStream(new FileInputStream("test.txt"));
/*		FileInputStream in = new FileInputStream("test.txt");
		int length = in.available();
		JarOutputStream out = new JarOutputStream(new FileOutputStream("test.jar"));
		byte[] bytes = new byte[length];
		in.read(bytes);
		JarEntry jarEntry = new JarEntry("test.txt");
//		jarEntry.setExtra(bytes);
		out.putNextEntry(jarEntry);
		out.write(bytes);
		out.closeEntry();
		out.close();*/
		
		unJar("test.jar");
	}

	public static void unJar(String file) throws FileNotFoundException, IOException{
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> e = jarFile.entries();
		while(e.hasMoreElements()){
			JarEntry obj = e.nextElement();
			FileOutputStream out = new FileOutputStream("D:\\"+obj.getName());
			JarInputStream in = new JarInputStream(jarFile.getInputStream(obj));
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			out.write(bytes);
			out.flush();
			out.close();
			in.close();
		}
	}
}
