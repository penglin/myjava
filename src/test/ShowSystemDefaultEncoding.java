package test;

public class ShowSystemDefaultEncoding {

	public static void main(String[] args) {
		System.setProperty("file.encoding", "ISO-8859-1");
		String encoding = System.getProperty("file.encoding");

		System.out.println(encoding);

	}
}

//	ÎÄÕÂ³ö´¦£º·ÉÅµÍø(www.firnow.com):http://dev.firnow.com/course/4_webprogram/jsp/jsp_js/20071118/86754.html
