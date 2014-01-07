/**
 * 数据库操作封装对象
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
 * 数据库操作封装对象， 封装公共数据库操作,提供公共的数据库操作接口
 */
public class SqlBridge
{
	// 日志对象
	Log log = LogFactory.getLog(this.getClass());

	/**
	 * 数据库联接
	 */
	private Connection conn = null;

	/**
	 * 数据库操作声明
	 */
	private Statement stmt = null;

	/**
	 * 数据库操作预声明
	 */
	private PreparedStatement preparedStmt = null;

	/**
	 * 数据库查询结果集
	 */
	private ResultSet rs = null;

	/**
	 * 数据库查询结果数据描述
	 */
	private ResultSetMetaData rsmd = null;

	/**
	 * 数据库操作桥构造函数
	 */
	public SqlBridge()
	{
	}

	/**
	 * 设置数据库连接
	 * 
	 * @throws Exception 处理异常
	 */
	public void setConnection(Connection conn) throws Exception
	{
		// 关闭原有连接
		this.closeConnection();
		// 保存连接
		this.conn = conn;
		this.conn.setAutoCommit(true);
	}

	/**
	 * 关闭连接
	 * 
	 * @throws Exception 处理异常
	 */
	public void closeConnection() throws Exception
	{
		// 清除结果集合
		if (this.conn == null)
			return;
		this.clearResult();
		// 关闭原有连接
		if (this.conn != null && !this.conn.isClosed())
			this.conn.close();
		this.conn = null;
	}

	/**
	 * 开始事物事务
	 * 
	 * @throws Exception 处理异常
	 */
	public void transact() throws Exception
	{
		this.conn.setAutoCommit(false);
	}

	/**
	 * 提交事务
	 * 
	 * @throws Exception 处理异常
	 */
	public void commit() throws Exception
	{
		this.conn.commit();
		this.conn.setAutoCommit(true);
	}

	/**
	 * 回滚事务
	 * 
	 * @throws Exception 处理异常
	 */
	public void rollback() throws Exception
	{
		this.conn.rollback();
		this.conn.setAutoCommit(true);
	}

	/**
	 * 清除结果集
	 * 
	 * @throws Exception 处理异常
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
	 * 得到字段值
	 * 
	 * @param columnIndex 字段索引
	 * @return 字段值
	 * @throws Exception 处理异常
	 */
	private Object getFieldValue(int columnIndex) throws Exception
	{
		// 如果结果集为空,抛送异常
		if (this.rs == null || this.rsmd == null)
			throw new SQLException("ResultSet is null.");
		// 根据字段数据类型和结果说明，放回字段的值
		switch (this.rsmd.getColumnType(columnIndex))
		{
		// 根据数据类型取值
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
	 * 执行SQL查询
	 * 
	 * @param querySql 查询SQL
	 * @return 查询结果集合
	 * @throws Exception 处理异常
	 */
	public List<Object[]> executeQuery(String querySql) throws Exception
	{
		// 判断当前连接是否有效
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// 清除之前查询结果集合
		this.clearResult();
		// 设置自动提交
		this.conn.setAutoCommit(true);
		// 执行查询
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// 遍历查询结果集合保存到列表
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
		// 清除之前查询结果集合
		this.clearResult();
		// 返回结果集合
		return objects;
	}
	/**
	 * 执行SQL查询
	 * 
	 * @param querySql 查询SQL
	 * @return 查询结果集合
	 * @throws Exception 处理异常
	 */
	public int executeMapQuery(List<LinkedHashMap<String,Object>> objects,String querySql) throws Exception
	{
		// 判断当前连接是否有效
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// 设置自动提交
		this.conn.setAutoCommit(true);
		// 清除之前查询结果集合
		this.clearResult();
		// 执行查询
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// 遍历查询结果集合保存到列表
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
		// 清除之前查询结果集合
		this.clearResult();
//		log.info("map中总数="+size);
		// 返回结果集合
		return size;
	}


	/**
	 * 执行SQL查询
	 * 
	 * @param querySql 查询SQL
	 * @return 查询结果集合
	 * @throws Exception 处理异常
	 */
	public List<LinkedHashMap<String,Object>> executeMapQuery(String querySql) throws Exception
	{
		// 判断当前连接是否有效
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// 设置自动提交
		this.conn.setAutoCommit(true);
		// 执行查询
		this.stmt = this.conn.createStatement();
		this.rs = this.stmt.executeQuery(querySql);
		
		this.rsmd = this.rs.getMetaData();
		// 遍历查询结果集合保存到列表
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
		// 清除之前查询结果集合
		this.clearResult();
//		log.info("map中总数="+size);
		// 返回结果集合
		return objects;
	}
	
	/**
	 * 执行更新sql
	 * 
	 * @param sql 更新sql
	 * @return 执行更新受影响记录条数
	 */
	public int executeUpdate(String sql) throws Exception
	{
		// 判断当前连接是否有效
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// 设置自动提交
		this.conn.setAutoCommit(true);
		// 清除之前查询结果集合
		this.clearResult();
		// 执行更新SQL
		int result = 0;
		try
		{
			this.stmt = this.conn.createStatement();
			result = this.stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			this.log.error("SQL[" + sql + "]执行异常.", e);
			/*try
			{
				this.conn.rollback();
			}
			catch (Exception ex)
			{
				this.log.error("SQL[" + sql + "]回滚异常.", ex);
			}*/
			throw e;
		}
		finally
		{
			// 清除SQL执行痕迹
			this.clearResult();
		}
		return result;
	}

	/**
	 * 执行批量更新sql
	 * 
	 * @param sqls sql集合
	 * @return 执行更新受影响记录条数
	 */
	public int[] executeUpdate(List<String> sqls) throws Exception
	{
		// 验证参数有效性
		if (sqls == null || sqls.size() == 0)
			return null;
		// 判断当前连接是否有效
		if (this.conn == null || this.conn.isClosed())
			throw new SQLException("connection is closed.");
		// 清除之前查询结果集合
		this.clearResult();
		int[] result = null;
		try
		{
			// 设置自动提交为否
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
				this.log.error("批量SQL执行异常.", e);
			}
			try
			{
				this.conn.rollback();
			}
			catch (Exception ex)
			{
				this.log.error("批量SQL执行回滚异常.", ex);
			}
			throw e;
		}
		finally
		{
			// 清除SQL执行痕迹
			this.clearResult();
		}
		return result;
	}
}