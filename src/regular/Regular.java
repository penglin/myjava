package regular;

public class Regular {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// [\d*,\d*]*
		System.out.println("".matches("[\\d*,\\d*]+"));

		String t = "cc0vlano5vsakm4x76cl7yl91igpaoy67f503db83bqzisoshu-fv4028810938c1894a0138db8a42ef053b1346222159lfg528vlg4ph34mmdg51c34028810935d24a620135d27b2ede00794028810935d24a620135d2932a93009c122.146.41.96Ì¨ï¿½ï¿½Ê¡Ì¨ï¿½ï¿½ï¿½ï¿½134622213944outck1ee1mz23x6nr84eisoshu*qzisoshu-fv13462221391132012å¹?8æœ?9æ—?41end";
		int len = t.split(new String(new char[] { 1 })).length;
		System.out.println(len);

		
		String simpleDate = "20120920";
		String simpleDate2 = "20120923";
		System.out.println(simpleDate.compareTo("20120921jflkdsjfljasdlfkjasdlk"));
		System.out.println(simpleDate2.compareTo("20120922dfasdfadfds"));
	}

}
