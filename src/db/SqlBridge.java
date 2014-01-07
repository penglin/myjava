/**
 * ���ݿ������װ����
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator
 * 
 * ���ݿ������װ���� ��װ�������ݿ����,�ṩ���������ݿ�����ӿ�
 */
public class SqlBridge
{
	// ��־����
	Log log = LogFactory.getLog(this.getClass());

	/**
	 * ���ݿ�����
	 */
	private Connection conn = null;

	/**
	 * ���ݿ��������
	 */
	private Statement stmt = null;

	/**
	 * ���ݿ����Ԥ����
	 */
	private PreparedStatement preparedStmt = null;

	/**
	 * ���ݿ��ѯ�����
	 */
	private ResultSet rs = null;

	/**
	 * ���ݿ��ѯ�����������
	 */
	private ResultSetMetaData rsmd = null;

	/**
	 * ���ݿ�����Ź��캯��
	 */
	public SqlBridge()
	{
	}

	/**
	 * �������ݿ�����
	 * 
	 * @throws Exception �����쳣
	 */
	public void setConnection(Connection conn) throws Exception
	{
		// �ر�ԭ������
		this.closeConnection();
		// ��������
		this.conn = conn;
		this.conn.setAutoCommit(true);
	}

	/**
	 * �ر�����
	 * 
	 * @throws Exception �����쳣
	 */
	public void closeConnection() throws Exception
	{
		// ����������
		if (this.conn == null)
			return;
		this.clearResult();
		// �ر�ԭ������
		if (this.conn != null && !this.conn.isClosed())
			this.conn.close();
		this.conn = null;
	}

	/**
	 * ��ʼ��������
	 * 
	 * @throws Exception �����쳣
	 */
	public void transact() throws Exception
	{
		this.conn.setAutoCommit(false);
	}

	/**
	 * �ύ����
	 * 
	 * @throws Exception �����쳣
	 */
	public void commit() throws Exception
	{
		this.conn.commit();
		this.conn.setAutoCommit(true);
	}

	/**
	 * �ع�����
	 * 
	 * @throws Exception �����쳣
	 */
	public void rollback() throws Exception
	{
		this.conn.rollback();
		this.conn.setAutoCommit(true);
	}

	/**
	 * ��������
	 * 
	 * @throws Exception �����쳣
	 */
	private void clearResult() throws Exception
	{
		if (this.rs != null)
			this.rs.close();
		this.rs = null;
		if (this.stmt != null)
			this.stmt.close();
		this.stmt = null;
		if (this.preparedStmt != null)
			this.preparedStmt.close();
		this.preparedStmt = null;
		this.rsmd = null;
	}

	/**
	 * �õ��ֶ�ֵ
	 * 
	 * @param columnIndex �ֶ�����
	 * @return �ֶ�ֵ
	 * @throws Exception �����쳣
	 */
	private Object getFieldValue(int columnIndex) throws Exception
	{
		// ��������Ϊ��,�����쳣
		if (this.rs == null || this.rsmd == null)
			throw new SQLException("ResultSet is null.");
		// �����ֶ��������ͺͽ��˵�����Ż��ֶε�ֵ
		switch (this.rsmd.getColumnType(columnIndex))
		{
		// ������������ȡֵ
		case Types.BIGINT: {
			return new Long(this.rs.getLong(columnIndex));
		}
		case Types.BINARY: {
			return new Byte(this.rs.getByte(columnIndex));
		}
		case Types.BIT: {
			return new Boolean(this.rs.getBoolean(columnIndex));
		}
		case Types.CHAR: {
			return this.rs.getString(columnIndex);
		}
		case Types.DATE: {
			return new Date(this.rs.getDate(columnIndex).getTime());
		}
		case Types.TIME: {
			return new Date(this.rs.getTime(columnIndex).getTime());
		}
		case Types.DECIMAL: {
			return this.rs.getBigDecimal(columnIndex);
		}
		case Types.DOUBLE: {
			return new Double(this.rs.getDouble(columnIndex));
		}
		case Types.FLOAT: {
			return new Float(this.rs.getFloat(columnIndex));
		}
		case Types.INTEGER: {
			return new Integer(this.rs.getInt(columnIndex));
		}
		case Types.LONGVARBINARY: {
			return this.rs.getBinaryStream(columnIndex);
		}
		case Types.LONGVARCHAR: {
			return this.rs.getString(columnIndex);
		}
		case Types.NULL: {
			return null;
		}
		case Types.NUMERIC: {
			return this.rs.getBigDecimal(columnIndex);
		}
		case Types.REAL: {
			return new Float(this.rs.getFloat(columnIndex));
		}
		case Types.SMALLINT: {
			return new Short(this.rs.getShort(columnIndex));
		}
		case Types.TIMESTAMP: {
			return this.rs.getTimestamp(columnIndex);
		}
		case Types.TINYINT: {
			return new Byte(this.rs.getByte(columnIndex));
		}
		case Types.VARBINARY: {
			return this.rs.getBytes(columnIndex);
		}
		case Types.VARCHAR: {
			return this.rs.getString(columnIndex);
		}
		default: {
			return this.rs.getObject(columnIndex);
		}
		}
	}

	/**
	 * ִ��SQL��ѯ
	 * 
	 * @param querySql ��ѯSQL
	 * @return ��ѯ�������
	 * @throws Exception �����쳣
	 */
	public List<Object[]> executeQuery(String querySql) throws Exception
	{
		// �жϵ�ǰ�����Ƿ���Ч
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// ���֮ǰ��ѯ�������
		this.clearResult();
		// �����Զ��ύ
		this.conn.setAutoCommit(true);
		// ִ�в�ѯ
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// ������ѯ������ϱ��浽�б�
		ArrayList<Object[]> objects = new ArrayList<Object[]>();
		while (this.rs.next())
		{
			Object[] row = new Object[this.rsmd.getColumnCount()];
			for (int i = 0; i < row.length; i++)
			{
				row[i] = this.getFieldValue(i + 1);
			}
			objects.add(row);
		}
		// ���֮ǰ��ѯ�������
		this.clearResult();
		// ���ؽ������
		return objects;
	}
	/**
	 * ִ��SQL��ѯ
	 * 
	 * @param querySql ��ѯSQL
	 * @return ��ѯ�������
	 * @throws Exception �����쳣
	 */
	public int executeMapQuery(List<LinkedHashMap<String,Object>> objects,String querySql) throws Exception
	{
		// �жϵ�ǰ�����Ƿ���Ч
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// �����Զ��ύ
		this.conn.setAutoCommit(true);
		// ���֮ǰ��ѯ�������
		this.clearResult();
		// ִ�в�ѯ
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// ������ѯ������ϱ��浽�б�
//		ArrayList<LinkedHashMap<String,Object>> objects = new ArrayList<LinkedHashMap<String,Object>>();
		int size=0;
		while (this.rs.next())
		{
			Object[] row = new Object[this.rsmd.getColumnCount()];
			LinkedHashMap<String,Object> map  = new LinkedHashMap<String,Object>(this.rsmd.getColumnCount());
			for (int i = 0; i < row.length; i++)
			{
				String columnName = this.rsmd.getColumnName(i+1);
				row[i] = this.getFieldValue(i + 1);
				map.put(columnName, row[i]);
			}
			objects.add(map);
			map = null;
			row = null;
//			size=size+map.size();
			size++;
		}
		// ���֮ǰ��ѯ�������
		this.clearResult();
//		log.info("map������="+size);
		// ���ؽ������
		return size;
	}


	/**
	 * ִ��SQL��ѯ
	 * 
	 * @param querySql ��ѯSQL
	 * @return ��ѯ�������
	 * @throws Exception �����쳣
	 */
	public List<LinkedHashMap<String,Object>> executeMapQuery(String querySql) throws Exception
	{
		// �жϵ�ǰ�����Ƿ���Ч
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// �����Զ��ύ
		this.conn.setAutoCommit(true);
		// ִ�в�ѯ
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// ������ѯ������ϱ��浽�б�
		ArrayList<LinkedHashMap<String,Object>> objects = new ArrayList<LinkedHashMap<String,Object>>();
//		int size=0;
		while (this.rs.next())
		{
			Object[] row = new Object[this.rsmd.getColumnCount()];
			LinkedHashMap<String,Object> map  = new LinkedHashMap<String,Object>(this.rsmd.getColumnCount());
			for (int i = 0; i < row.length; i++)
			{
				String columnName = this.rsmd.getColumnName(i+1);
				row[i] = this.getFieldValue(i + 1);
				map.put(columnName, row[i]);
			}
			objects.add(map);
//			size=size+map.size();
		}
		// ���֮ǰ��ѯ�������
		this.clearResult();
//		log.info("map������="+size);
		// ���ؽ������
		return objects;
	}
	
	/**
	 * ִ�и���sql
	 * 
	 * @param sql ����sql
	 * @return ִ�и�����Ӱ���¼����
	 */
	public int executeUpdate(String sql) throws Exception
	{
		// �жϵ�ǰ�����Ƿ���Ч
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// �����Զ��ύ
		this.conn.setAutoCommit(true);
		// ���֮ǰ��ѯ�������
		this.clearResult();
		// ִ�и���SQL
		int result = 0;
		try
		{
			this.stmt = this.conn.createStatement();
			result = this.stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			this.log.error("SQL[" + sql + "]ִ���쳣.", e);
			/*try
			{
				this.conn.rollback();
			}
			catch (Exception ex)
			{
				this.log.error("SQL[" + sql + "]�ع��쳣.", ex);
			}*/
			throw e;
		}
		finally
		{
			// ���SQLִ�кۼ�
			this.clearResult();
		}
		return result;
	}

	/**
	 * ִ����������sql
	 * 
	 * @param sqls sql����
	 * @return ִ�и�����Ӱ���¼����
	 */
	public int[] executeUpdate(List<String> sqls) throws Exception
	{
		// ��֤������Ч��
		if (sqls == null || sqls.size() == 0)
			return null;
		// �жϵ�ǰ�����Ƿ���Ч
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// ���֮ǰ��ѯ�������
		this.clearResult();
		int[] result = null;
		try
		{
			// �����Զ��ύΪ��
			this.conn.setAutoCommit(false);
			this.stmt = this.conn.createStatement();
			for (int i = 0; i < sqls.size(); i++)
			{
				String sql = sqls.get(i);
				stmt.addBatch(sql);
			}
			result = stmt.executeBatch();
			this.conn.commit();
			this.conn.setAutoCommit(true);
		}
		catch (Exception e)
		{
			for (int i = 0; i < sqls.size(); i++)
			{
				this.log.error("����SQLִ���쳣.", e);
			}
			try
			{
				this.conn.rollback();
			}
			catch (Exception ex)
			{
				this.log.error("����SQLִ�лع��쳣.", ex);
			}
			throw e;
		}
		finally
		{
			// ���SQLִ�кۼ�
			this.clearResult();
		}
		return result;
	}
}