package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {
	/**
	 * 得到指定日期的那周的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(String date){
		String ret_date=null;
		Calendar c = Calendar.getInstance();
		try {
			
			c.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(date));
			c.setFirstDayOfWeek(Calendar.MONDAY);
			//获得当天是星期几
			long dayOfWeek = c.get(c.DAY_OF_WEEK)-1;
			System.out.println(dayOfWeek);
			//获取到此周一到1970 年 1 月 1 日 00:00:00 GMT 的毫秒数。
			long millisecond   = c.getTime().getTime()-24 * 3600 * 1000L * dayOfWeek;
			ret_date = new SimpleDateFormat("yyyy-mm-dd").format(new Date(millisecond));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret_date;
	}
	
	public static Date getDayByWeek(int year,int week,boolean flag)
	  {
	   Calendar cal=Calendar.getInstance();
	   cal.set(Calendar.YEAR,year);
	   cal.set(Calendar.WEEK_OF_YEAR,week);
	   if(!flag)
	    cal.setTimeInMillis(cal.getTimeInMillis()+6*24*60*60*1000);
	    return cal.getTime();  
	  } 
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getFirstDayOfWeek("2010-04-26"));
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };   
		Calendar calendar = Calendar.getInstance();   
		Date date = new Date();   
		calendar.setTime(date);   
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;   
		if(dayOfWeek <0)dayOfWeek=0;   
		System.out.println(dayNames[dayOfWeek]);   
		
		
		try {
			Date d1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse("2010年05月18日 3:4:3");
			Date d2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse("2010年05月18日 16:20:07");
			System.out.println(d1);
			System.out.println(d2);
			long time = d2.getTime() - d1.getTime();
			System.out.println(time);
			System.out.println(time/(24*60*60*1000));
			Calendar c = Calendar.getInstance();
			c.setTime(d2);
			c.add(Calendar.DATE, 1);
			System.out.println(c.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    	String formatDate = sdf.format(date);
    	Date agoDate = sdf.parse("201002");
    	System.out.println(date.getTime()-agoDate.getTime());
    	System.out.println(CAPAIGNDEPRECATEDTIME);
    	System.out.println(formatDate.substring(0, 4));
		System.out.println(formatDate.substring(4));
		
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date oldDate = sdf1.parse("2010年9月10日 19:55:52");
		System.out.println(new Date());
		System.out.println((new Date().getTime() - oldDate.getTime())/(60*1000));
		
		
//		1298908766679 2011年02月28日
//		1295236992925 2011年01月17日
		Date d = new Date(1295236992925L);
		System.out.println(sdf1.format(d));
	}
	private static final long CAPAIGNDEPRECATEDTIME = 6*30*24*60*60*1000L;
}
