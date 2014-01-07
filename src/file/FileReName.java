package file;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReName {
	public static void main(String[] args) throws IOException {
		// File file = new
		// File("tracking_192.168.1.145_[depositSimpleDate]_4028816732e320290132e686ab7828c4.log");
		// file.renameTo(new
		// File("pannnel_tracking_[depositSimpleDate]_4028816732e320290132e686ab7828c4.log"));
		System.out.println(System.currentTimeMillis());

		String fileName = URLEncoder.encode("\"Buy 3 get 1 Free\" Promotion", "GBk");
		;
		File testFile = new File(fileName);
		testFile.createNewFile();

		String campaignName = "Buy 3 get 1 Free \\ /Promotion";
		System.out.println(campaignName);
		Pattern p = Pattern.compile("[:<>?*\"|\\\\/]+");
		Matcher m = p.matcher(campaignName);
		boolean b = m.find();
		System.out.println(b);
	}
}
