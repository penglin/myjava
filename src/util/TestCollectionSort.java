package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.SqlBridge;

public class TestCollectionSort {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 连接数据库
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.238:1433/tracking_analysis_core;tds=8.0;lastupdatecount=true", "sa", "Adsit!@#$");

		SqlBridge sb = new SqlBridge();
		sb.setConnection(conn);
		String queryString = "select distinct a.Analysis_Task_Id,a.Analysis_Task_Title,a.Analysis_Task_Type,a.Analysis_Task_Mode,a.Task_Mode_Peroid,"
			+ "a.Task_Mode_Time,a.Campaign_Id,a.Campaign_Begin_Date,a.Campaign_End_Date,a.Analysis_Task_Level,a.Analysis_Task_State,"
			+ "a.Task_Publish_Time,a.Analysis_Task_Memo,"
			+ "b.Analysis_Task_Report_Id,b.Task_Report_Spotplan,b.Task_Report_By_Day,"
			+ "c.Analysis_Report_Id,c.Report_Name,c.Report_Sql_Path,c.Report_Memo,c.Generate_Report_Sql,c.Report_Merge_Key_Properties,c.Report_Merge_Value_Properties "
			+ "from Analysis_Task a,Analysis_Task_Report b, Analysis_Report c "
			+ "where a.Analysis_Task_State=1 and a.Analysis_Task_Id=b.Analysis_Task_Id and b.Analysis_Report_Id=c.Analysis_Report_Id ";
		
		List<Object[]> list = sb.executeQuery(queryString);
		//对任务列表进行排序
		Collections.sort(list, new Comparator<Object[]>(){
			public int compare(Object[] objs1, Object[] objs2) {
				int level1 = Integer.parseInt(objs1[9].toString());
				int level2 = Integer.parseInt(objs2[9].toString());
				//优先级比较
				if(level1<level2){
					return 1;
				}
				
				if(level1==level2){
					//报表类型比较
					int taskMode1 = Integer.parseInt(objs1[3].toString());
					int taskMode2 = Integer.parseInt(objs2[3].toString());
					if(taskMode1>taskMode2){
						return 1;
					}
					if(taskMode1==taskMode2){
						return 0;
					}
					if(taskMode1<taskMode2){
						return -1;
					}
				}
				
				return -1;
			}});
		
		System.out.println(list.size());
		sb.closeConnection();
	}

}
