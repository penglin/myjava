package db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	private ConnectionFactory() {
	}

	private static ComboPooledDataSource ds = null;

	static {
		try {
			// Logger log = Logger.getLogger("com.mchange"); // ��־
			// log.setLevel(Level.WARNING);
			ds = new ComboPooledDataSource();
			// ����JDBC��Driver��
			ds.setDriverClass("net.sourceforge.jtds.jdbc.Driver"); // ������
																	// Config
																	// ����������ļ���ȡ
			// ����JDBC��URL
			ds.setJdbcUrl("jdbc:jtds:sqlserver://192.168.1.238:1433/tracking_analysis_core;tds=8.0;lastupdatecount=true");
			// �������ݿ�ĵ�¼�û���
			ds.setUser("sa");
			// �������ݿ�ĵ�¼�û�����
			ds.setPassword("Adsit!@#$");
//			ds.setPassword("adsit");
			// �������ӳص����������
			ds.setMaxPoolSize(5);
			// �������ӳص���С������
			ds.setMinPoolSize(2);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage()+"--------");
		}
		return con;
	}
	// C3P0 end
	
	public static synchronized void closeFactory(){
		ds.close();
	}
	
	public static void main(String[] args) {
		Connection con = getConnection();
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from Analysis_Task");
			ResultSetMetaData  rsmd = rs.getMetaData();
			Object[] row = null;
			while (rs.next())
			{
				 row = new Object[rsmd.getColumnCount()];
				LinkedHashMap<String,Object> map  = new LinkedHashMap<String,Object>(rsmd.getColumnCount());
				for (int i = 0; i < row.length; i++)
				{
					String columnName = rsmd.getColumnName(i+1);
					row[i] = rs.getObject(i + 1);
					map.put(columnName, row[i]);
				}
//				size=size+map.size();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage()+"--------");
			System.out.println(e+"--------");
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(con);
	}
	
}