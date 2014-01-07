package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class GenSQLMerge {
	public static String url = "jdbc:jtds:sqlserver://ip:1433/dbname;tds=8.0;lastupdatecount=true";
	public static String pwd = "Adsit!@#$";
	public static String user = "sa";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url.replace("ip", "192.168.1.238").replace("dbname", "tracking_analysis_core"), user, pwd);
		Connection conn_table = DriverManager.getConnection(url.replace("ip", "192.168.1.100").replace("dbname", "tracking_analysis_report"), user, pwd);
		
		String sql = "select * from dbo.Analysis_Report ";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		while(rs.next()){
			String reportName = rs.getString(2);
			System.out.println("-------------------------"+reportName+"-------------------------");
			String tableKey = rs.getString(5);
			String tableValue = rs.getString(6);
			StringBuffer sb = new StringBuffer();
			
			StringBuffer sbUpdate = new StringBuffer();
			
			for(String key:tableKey.split(",")){
				sb.append(" to_table.");
				sb.append(key);
				sb.append("=from_table.");
				sb.append(key);
				sb.append(" and");
			}
			sb.delete(sb.lastIndexOf("and")-1, sb.toString().length());
			
			for(String value:tableValue.split(",")){
				sbUpdate.append(" to_table.");
				sbUpdate.append(value);
				sbUpdate.append("=to_table.");
				sbUpdate.append(value);
				sbUpdate.append("+from_table.");
				sbUpdate.append(value);
				sbUpdate.append(",");
			}
			sbUpdate.delete(sbUpdate.lastIndexOf(",")-1, sbUpdate.toString().length());
			
			Statement st = conn_table.createStatement();
			ResultSet rs_table = st.executeQuery("select name from syscolumns where id=object_id('"+reportName+"')");
			StringBuffer list = new StringBuffer();
			while(rs_table.next()){
				list.append("from_table.");
				list.append(rs_table.getString(1));
				list.append(",");
			}
			if(list.toString().isEmpty())
				continue;
			list.delete(list.lastIndexOf(",")-1, list.toString().length());
			
			String merge = " MERGE tracking_analysis_report.dbo."+reportName+" as to_table \n" +
					"USING \n(" +
					"select * from DB_LINK_TO_76.tracking_analysis_local.dbo."+reportName+
					"\n)as from_table \n" +
					"ON " +sb.toString()+
					"\n WHEN MATCHED THEN\n" +
					"\nupdate set " +sbUpdate.toString()+
					"\nWHEN NOT MATCHED THEN \n" +
					"INSERT VALUES(\n" +
					 list.toString()+
					"\n)";
			st.close();
			rs_table.close();
			
			System.out.println(merge);
		}
		rs.close();
		conn.close();
		conn_table.close();
	}

}
