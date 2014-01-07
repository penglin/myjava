package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private String url = "jdbc:sqlserver://ADSIT-60:1433;DatabaseName=precise_analysis_report_test";
//	private String url = "jdbc:sqlserver://192.168.1.100:1433;DatabaseName=adman";
	private String pwd = "Adsit!@#$";
	private String user = "sa";
	private Connection conn;
	public static int PAGESIZE = 1000;
	public static int UPDATENUM = 50;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public DBConnection() {
//		initParams();
		getConnction();
	}

	public DBConnection(String url,String user,String pwd) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		getConnction();
		// TODO Auto-generated constructor stub
	}
	
	private void initParams(){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("db.properties"));
			this.url = prop.getProperty("url");
			this.pwd = prop.getProperty("pwd");
			this.user = prop.getProperty("user");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Statement getStatement(){
		try {
//			getConnction();
			return conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Connection getConnction(){
		try {
//			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 执行sql返回的结果集
	 * @param sql
	 * @return
	 */
	public ResultSet getList(String sql){
		ResultSet rs = null;
		try {
			java.sql.Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 获取tableName中以PAGESIZE大小分页时,总共的页数
	 * @param tableName 表名
	 * @return 分页的页数
	 */
	public int getPageNumber(String sql){
		int pageNumber = 0;
		Statement st = null;
		ResultSet rs =null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			int totalNumber = rs.getInt(1);
			pageNumber = totalNumber%DBConnection.PAGESIZE==0?totalNumber/DBConnection.PAGESIZE:totalNumber/DBConnection.PAGESIZE+1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return pageNumber;
	}
	
	public static void main(String[] args) throws SQLException {
		int i = 0;
		DBConnection db = new DBConnection();
		System.out.println(db.getConnction());
		/*while(true){
			System.out.println("获取到连接："+i++);
			Connection conn = db.getConnction();
			System.out.println(conn);
		}*/
//		String beginDate = "2012年08月06日";
//		String endDate = "2012年08月06日";
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select ");
//		sql.append(" gather_date,");
//		sql.append("sum(demand_count),");
//		sql.append("sum(demand_unique_count),");
//		sql.append(" case when gather_date='2012年05月23日' then ");
//		sql.append(" (select sum(demand_unique_count)");
//		sql.append(" from campaign_track_date_gather");
//		sql.append(" where campaign_id='NLILFGU70024'");
//		sql.append(" and report_type=2");
//		sql.append(" and gather_date='2012年05月23日')");
//		sql.append(" else");
//		sql.append(" ((select sum(demand_unique_count) ");
//		sql.append(" from campaign_track_gather");
//		sql.append(" where gather_end_date=gather_date");
//		sql.append(" and campaign_id='NLILFGU70024'");
//		sql.append(" and report_type=5)");
//		sql.append(" -");
//		sql.append(" (select sum(demand_unique_count) ");
//		sql.append(" from campaign_track_gather");
//		sql.append(" where gather_end_date=datename(\"yy\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
//		sql.append(" +'年'+datename(\"mm\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
//		sql.append(" +'月'+");
//		sql.append(" substring('00',0,3-len(datename(\"dd\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))))");
//		sql.append(" +datename(\"dd\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
//		sql.append(" +'日'");
//		sql.append(" and campaign_id='NLILFGU70024'");
//		sql.append(" and report_type=5))");
//		sql.append(" end");
//		sql.append(" from campaign_track_date_gather");
//		sql.append(" where campaign_id='NLILFGU70024'");
//		sql.append(" and report_type=2");
//		sql.append(" and gather_date between  '" + beginDate + "' and '" + endDate + "'");
//		sql.append(" group by gather_date");
//		sql.append(" order by gather_date");
//		ResultSet rs = db.getList(sql.toString());
//		while(rs.next()){
//			System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getString(3));
//			System.out.println(rs.getString(4));
//		}
		
		
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public void close(){
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
