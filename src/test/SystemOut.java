package test;

import java.util.UUID;

public class SystemOut {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String queryString = "select distinct a.Analysis_Task_Id,a.Analysis_Task_Title,a.Analysis_Task_Type,a.Analysis_Task_Mode,a.Task_Mode_Peroid,"
			+ "a.Task_Mode_Time,a.Campaign_Id,a.Campaign_Begin_Date,a.Campaign_End_Date,a.Analysis_Task_Level,a.Analysis_Task_State,"
			+ "a.Task_Publish_Time,a.Analysis_Task_Memo,"
			+ "b.Analysis_Task_Report_Id,b.Task_Report_Spotplan,b.Task_Report_By_Day,"
			+ "c.Analysis_Report_Id,c.Report_Name,c.Report_Sql_Path,c.Report_Memo,c.Generate_Report_Sql,c.Report_Merge_Key_Properties,c.Report_Merge_Value_Properties "
			+ "from Analysis_Task a,Analysis_Task_Report b, Analysis_Report c "
			+ "where a.Analysis_Task_State=1 and a.Analysis_Task_Id=b.Analysis_Task_Id and b.Analysis_Report_Id=c.Analysis_Report_Id "
			+ "order by a.Analysis_Task_Level desc";
		System.out.println(queryString);
		
		
		System.out.println("fdsjfsdjlk#fldsjf".split("#").length);
		
		String tt = "dddd"+new String(new char[]{1});
		System.out.println(tt);
		
		String userId = "t:";
		//”√ªßid  t:
		if(userId.contains(":")&&userId.split(":").length==1){
			userId = userId+UUID.randomUUID().toString();
		}
		System.out.println(userId);
	}

}
