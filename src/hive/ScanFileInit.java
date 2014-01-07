/**
 * 点击处理初始化对象
 */
package hive;

import java.io.File;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import cn.adsit.common.mission.MissionInitializable;

/**
 * @author Administrator
 * 
 * 监播处理初始化对象
 */
public class ScanFileInit implements MissionInitializable
{
	/**
	 * 日志对象
	 */
	private static Log log = null;

	/**
	 * 任务启动初始化
	 */
	public boolean initialize(Properties properties)
	{
		try
		{
			// 得到对象装载路径
			String loaderPath = URLDecoder.decode(ScanFileInit.class.getClassLoader().getResource("").getPath(), "UTF-8");
			// 加载Log4j配置文件,如果文件不存在，退出
			String log4jConfigurePath = loaderPath + "hive" + File.separator + properties.getProperty("LogProperties", "hive-log4j.properties");
			File file = new File(log4jConfigurePath);
			if (!file.exists())
				throw new Exception("日志输出Log4j配置文件指定位置<" + log4jConfigurePath + ">不存在.");
			PropertyConfigurator.configure(log4jConfigurePath);
			log = LogFactory.getLog(ScanFileInit.class);
			log.info("Log4j装载配置文件及初始化成功.");
		
			// 初始化完成
			log.info("初始化完成");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.info("请求处理初始化失败.");
			return false;
		}
		return true;
	}
}