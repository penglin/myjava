package date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DateUtil {
	/**
	 * 一个月的开始日到指定日期date的间隔天数
	 * @param date
	 * @param month
	 * @return
	 */
	private int getDays(String date,String month){
		return getTotalDaysBetween(month+"-01",date,"yyyy-MM-dd");
	}
	
	public static void main(String[] args) {
		List<String> dates = new ArrayList<String>();
		dates.add("2010-05-06");
		dates.add("2010-03-06");
		dates.add("2010-06-06");
		dates.add("2010-06-08");
		dates.add("2010-06-09");
		dates.add("2010-06-10");
		String[] max_min_date = new DateUtil().getMaxAndMinDayInList("2010-05",dates);
		System.out.println(Arrays.toString(max_min_date));
		Collections.sort(dates);
		System.out.println(dates);
		int days = getTotalDaysBetween("2010-05-01","2010-05-06","yyyy-MM-dd");
		System.out.println(days);
//		1298908766679 2011年02月28日
//		1295236992925 2011年01月17日
		Date d = new Date(1295236992925L);
		System.out.println(DateUtil.formateDate(d));
		
		long l = 1295454929780L;
	}
	
	private String[] getMaxAndMinDayInList(String month,List<String> dates){
		String[] max_min_date = new String[2];
		String max_date = null;
		String min_date = null;
		for(String date : dates){
			if(date.startsWith(month)){
				if(max_date==null&&min_date==null){
					max_date = date;
					min_date = date;
					continue;
				}
				if(date.compareTo(min_date)<0){
					min_date = date;
				}else{
					max_date = date;
				}
			}
		}
		max_min_date[0] = min_date;
		max_min_date[1] = max_date;
		return max_min_date;
	}
	
	/**
	 * 补充月中间没有的出现的月的数据
	 * @param months
	 * @return
	 */
	private List<String> getMonths(List<String> months){
		Collections.sort(months);
		String startMonth = months.get(0);
		String endMonth = months.get(months.size()-1);
		if(startMonth.equals(endMonth))
			return months;
		
		List<String> newMonths = new ArrayList<String>();
		newMonths.add(startMonth);
		int start_Month = Integer.parseInt(startMonth.substring(5, 7))+1;
		startMonth = startMonth.substring(0, 4)+"-"+(Integer.toString(start_Month).length()==1?"0"+start_Month:Integer.toString(start_Month));
		while(startMonth.compareTo(endMonth)<0){
			newMonths.add(startMonth);
			if(start_Month==12){
				start_Month = 1;
				startMonth = (Integer.parseInt(startMonth.substring(0, 4))+1)+"-01";
			}else{
				start_Month = Integer.parseInt(startMonth.substring(5, 7))+1;
				startMonth = startMonth.substring(0, 4)+"-"+(Integer.toString(start_Month).length()==1?"0"+start_Month:Integer.toString(start_Month));
			}
		}
		newMonths.add(endMonth);
		return newMonths;
	}
	
	/**
	 * 获取两个日期的间隔天数
	 * @param start
	 * @param end
	 * @param dateFormat
	 * @return
	 */
	public static int getTotalDaysBetween(String start, String end,
			String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			long totalDate = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			long timestart = calendar.getTimeInMillis();
			calendar.setTime(endDate);
			long timeend = calendar.getTimeInMillis();
			totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
			return (int) totalDate;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}   
	
	/**
	 * 获取一个月的开始日期和结束日期
	 */
	public int getDaysOfMonth(String monthString,String format){
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date nowDate = simpleDateFormat.parse(monthString);
			Calendar c = Calendar.getInstance();
			c.setTime(nowDate);
			c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			String endDate = DateUtil.formateDate(c.getTime());
			return Integer.parseInt(endDate.substring(8, 10));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	/**
	 * 格式化指定时间对象为指定字符串形式时间
	 * 
	 * @param date 时间对象
	 * @return 字符串形式时间
	 */
	public static String formateDate(Date date)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.dateFormat);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取一个月指定日期之间日期的字符串集合
	 * @param startDate
	 * @param endDate
	 * @param month
	 * @return
	 */
	private List<String> getDaysList(String startDate,String endDate,String month){
		List<String> dates = new ArrayList<String>();
		int start = Integer.parseInt(startDate.substring(8,10));
		int end = Integer.parseInt(endDate.substring(8,10));
		for(int i=start;i<=end;i++){
			String date = null;
			if(i<10)
				date = "0"+Integer.toString(i);
			else
				date = Integer.toString(i);
			dates.add(month+"-"+date);
		}
		return dates;
	}
	private static long ONE_DAY = 1000*60*60*24;
	
	public static int getNumberOfDayBetween(Date beginDate,Date endDate){
		//最后加一是为了包括第一天
		return Math.round((endDate.getTime()-beginDate.getTime())/ONE_DAY)+1;
	}
	
	public static List<String> getDatesBetween(Date beginDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		List<String> dates = new ArrayList<String>();
		//要包括第一天，故把其加入list
		dates.add(sdf.format(beginDate));
		int numberOfDays = getNumberOfDayBetween(beginDate,endDate);
		for(int i=1;i<=numberOfDays-1;i++){
			beginDate = getNextDay(beginDate);
			dates.add(sdf.format(beginDate));
		}
		return dates;
	}
	
	private static Date getNextDay(Date beginDate){		
		return new Date(beginDate.getTime()+ONE_DAY);
	}
	/**
	 * 字符串形式日期格式
	 */
	private final static String dateFormat = "yyyy年MM月dd日";

}
