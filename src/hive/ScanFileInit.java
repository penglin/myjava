/**
 * ���������ʼ������
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
 * �ಥ������ʼ������
 */
public class ScanFileInit implements MissionInitializable
{
	/**
	 * ��־����
	 */
	private static Log log = null;

	/**
	 * ����������ʼ��
	 */
	public boolean initialize(Properties properties)
	{
		try
		{
			// �õ�����װ��·��
			String loaderPath = URLDecoder.decode(ScanFileInit.class.getClassLoader().getResource("").getPath(), "UTF-8");
			// ����Log4j�����ļ�,����ļ������ڣ��˳�
			String log4jConfigurePath = loaderPath + "hive" + File.separator + properties.getProperty("LogProperties", "hive-log4j.properties");
			File file = new File(log4jConfigurePath);
			if (!file.exists())
				throw new Exception("��־���Log4j�����ļ�ָ��λ��<" + log4jConfigurePath + ">������.");
			PropertyConfigurator.configure(log4jConfigurePath);
			log = LogFactory.getLog(ScanFileInit.class);
			log.info("Log4jװ�������ļ�����ʼ���ɹ�.");
		
			// ��ʼ�����
			log.info("��ʼ�����");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.info("��������ʼ��ʧ��.");
			return false;
		}
		return true;
	}
}