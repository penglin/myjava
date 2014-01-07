/**
 * ɨ���ļ�����
 */
package hive;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator
 * 
 * ɨ���ļ�����
 */
public class ScanFile
{
	// ��־����
	private Log log = LogFactory.getLog(this.getClass());

	// Ŀ���ļ�����·��
	private String targetDirectory = null;

	// Ŀ���ļ�ǰ׺
	private String targetFilePrefix = null;

	// Ŀ���ļ���׺
	private String targetFileSuffix = null;

	// Ŀ���ļ��洢ת�����
	private int targetTransmitInterval = 5000;

	// ɨ����������
	private int scanBatchCapacity = 600;

	/**
	 * ���캯��
	 * 
	 * @param targetDirectory Ŀ���ļ�����·��
	 * @param targetFilePrefix Ŀ���ļ�ǰ׺
	 * @param targetFileSuffix Ŀ���ļ���׺
	 * @param targetTransmitInterval Ŀ���ļ��洢ת�����
	 */
	public ScanFile(String targetDirectory, String targetFilePrefix, String targetFileSuffix, int targetTransmitInterval)
	{
		this.targetDirectory = targetDirectory;
		this.targetFilePrefix = targetFilePrefix;
		this.targetFileSuffix = targetFileSuffix;
		this.targetTransmitInterval = targetTransmitInterval;
		// ���Ŀ���ļ�����Ŀ¼�����ڣ�����Ŀ¼
		new File(this.targetDirectory).mkdirs();
	}

	/**
	 * ����ɨ����������
	 */
	public void setScanBatchCapacity(int scanBatchCapacity)
	{
		this.scanBatchCapacity = scanBatchCapacity;
	}

	/**
	 * ɨ���ļ�
	 * 
	 * @return ɨ�赽��Ŀ���ļ�����
	 */
	public LinkedList<File> scan() throws Exception
	{
		LinkedList<File> targets = new LinkedList<File>();
		// ɨ��Ŀ���ļ�·��
		File[] directories = new File(this.targetDirectory).listFiles();
		if (directories == null || directories.length == 0)
			return targets;
		// �Դ洢���ļ�������
		LinkedList<File> directoriesList = new LinkedList<File>();
		Collections.addAll(directoriesList, directories);
		Collections.sort(directoriesList);
		// �����洢���ļ���
		Iterator<File> directoryIterator = directoriesList.iterator();
		while (directoryIterator.hasNext())
		{
			// �õ�һ������Ŀ¼
			File directory = directoryIterator.next();
			// ������ļ�,�澯��ɾ��
			if (directory.isFile())
			{
				this.log.warn("ɨ��Ŀ���ļ�,һ��Ŀ¼���´��ڷǷ��ļ�[" + directory.getName() + "],�ļ���ɾ��.");
				directory.delete();
				continue;
			}
			// ������ļ��У�����������
			if (!directory.isDirectory())
				continue;
			// ������װ�ط����ļ�
			File[] files = directory.listFiles();
			// ɾ�����ļ���
			if (files == null || files.length == 0)
			{
				if (directoryIterator.hasNext())
					directory.delete();
				continue;
			}
			// װ�ط���Դ�����ļ�
			for (int i = 0; i < files.length; i++)
			{
				File targetFile = files[i];
				// ������ļ�������������
				if (targetFile == null || !targetFile.isFile())
					continue;
				// ����ļ����������뵱ǰʱ��û��һ���ʱ���ݲ�����
				if (System.currentTimeMillis() - targetFile.lastModified() <= this.targetTransmitInterval)
					continue;
				// ��֤�ļ������Ƿ����
				String fileName = targetFile.getName();
				if (!fileName.startsWith(this.targetFilePrefix) || !fileName.endsWith(this.targetFileSuffix))
					continue;
				// �������������Ŀ���ļ�������
				targets.add(targetFile);
				// �������ɨ��Ŀ���ļ��Ѿ��ﵽ������������ͣɨ��
				if (targets.size() >= this.scanBatchCapacity)
					break;
			}
		}
		return targets;
	}
}