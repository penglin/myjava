package date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyDate {
	public static void main(String[] args) {
		Date date = new Date(1387402523653L);
		System.out.println(date);
		//Tue Apr 19 20:21:15 CST 2011
		String datess = "yyyyƒÍMM‘¬dd»’ HH:mm:ss";
		System.out.println(datess.substring(0,11)+"[[[");
		
		long time = 1372407139039L;
		System.out.println(new Date(time));
		
		long time2 = 1354738351051L;
		System.out.println(new Date(time2));
		
		String s = "Nov 28, 2013 4:44:09 PM";
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		try {
			System.out.println(sdf.format(new Date()));
			
			Date dddd = sdf.parse(s);
			System.out.println(dddd.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
