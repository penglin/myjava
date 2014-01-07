package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sql {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		Date beginDate = sdf.parse("2011��03��01��");
		Date endDate = sdf.parse("2011��03��14��");
		List<String> dates = getDatesBetween(beginDate,endDate);
//		System.out.println(dates);
		Map<String,String> tt = new HashMap<String, String>();
		//402881862dbec394012dc22b31af0074
		for(String date : dates){
			Date d = sdf.parse(date);
			Date nextDate = getNextDay(d);
//			String sql = "update visit_log_20110228 set visit_date='"+date+"',visit_date_time='"+date+"'+substring(visit_date_time,12,9) where time_stamp>="+d.getTime()+" and time_stamp<"+nextDate.getTime()+"\n GO";
			String sql = "update visit_log_20110228 set visit_date='"+date+"',visit_date_time='"+date+"'+substring(visit_date_time,12,9) where time_stamp>="+d.getTime()+" and time_stamp<"+nextDate.getTime()+"\n GO";
//			System.out.println(sql);
			
			/*String sql = "insert into ttSITE  select '"+date+"'," +
						" (select COUNT(*) from  visit_log_20110228 where  Campaign_Track_Id = 'NLUCN4TG000C' and visit_date between '2011��01��25��' and '"+date+"')," +
						"sum(case when t.f = 1 then 1 else 0 end) as Ƶ��1" +
						",sum(case when t.f = 2 then 1 else 0 end) as Ƶ��2" +
						",sum(case when t.f >= 3 then 1 else 0 end) as Ƶ��3" +
						" from(" +
						"select count(visitor_id) as f from visit_log_20110228 " +
						"where  Campaign_Track_Id = 'NLUCN4TG000C' and visit_date between '2011��01��25��' and '"+date+"' " +
						"	group by visitor_id	)as t \n GO";*/
			System.out.println(sql);
			
			
			
			/*String[] arr = new String[]{"2011��02��11�� 00:00:00","2011��02��11�� 23:59:59","2011��02��17�� 00:00:00","2011��02��17�� 23:59:59","2011��02��28�� 00:00:00","2011��02��28�� 23:59:59"};
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
			for(String tmp : arr){
				System.out.println(tmp+":"+sdf2.parse(tmp).getTime());
			}*/
			
		}
		
/*	delete from talbe where 
	(
		time_stamp not between 1297353600000 and 1297439999000 
		or time_stamp not between 1297872000000 and 1297958399000 
		or time_stamp not between 1298822400000 and 1298908799000
	) 
	and now_obj_point_id='402881862dbec394012dc22b31af0074'*/
	}
	
	private static long ONE_DAY = 1000*60*60*24;
	
	public static int getNumberOfDayBetween(Date beginDate,Date endDate){
		//����һ��Ϊ�˰�����һ��
		return Math.round((endDate.getTime()-beginDate.getTime())/ONE_DAY)+1;
	}
	
	public static List<String> getDatesBetween(Date beginDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		List<String> dates = new ArrayList<String>();
		//Ҫ������һ�죬�ʰ������list
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
}