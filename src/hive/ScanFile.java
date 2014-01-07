/**
 * 扫描文件对象
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
 * 扫描文件对象
 */
public class ScanFile
{
	// 日志对象
	private Log log = LogFactory.getLog(this.getClass());

	// 目标文件保存路径
	private String targetDirectory = null;

	// 目标文件前缀
	private String targetFilePrefix = null;

	// 目标文件后缀
	private String targetFileSuffix = null;

	// 目标文件存储转发间隔
	private int targetTransmitInterval = 5000;

	// 扫描批次容量
	private int scanBatchCapacity = 600;

	/**
	 * 构造函数
	 * 
	 * @param targetDirectory 目标文件保存路径
	 * @param targetFilePrefix 目标文件前缀
	 * @param targetFileSuffix 目标文件后缀
	 * @param targetTransmitInterval 目标文件存储转发间隔
	 */
	public ScanFile(String targetDirectory, String targetFilePrefix, String targetFileSuffix, int targetTransmitInterval)
	{
		this.targetDirectory = targetDirectory;
		this.targetFilePrefix = targetFilePrefix;
		this.targetFileSuffix = targetFileSuffix;
		this.targetTransmitInterval = targetTransmitInterval;
		// 如果目标文件保存目录不存在，创建目录
		new File(this.targetDirectory).mkdirs();
	}

	/**
	 * 设置扫描批次容量
	 */
	public void setScanBatchCapacity(int scanBatchCapacity)
	{
		this.scanBatchCapacity = scanBatchCapacity;
	}

	/**
	 * 扫描文件
	 * 
	 * @return 扫描到的目标文件集合
	 */
	public LinkedList<File> scan() throws Exception
	{
		LinkedList<File> targets = new LinkedList<File>();
		// 扫描目标文件路径
		File[] directories = new File(this.targetDirectory).listFiles();
		if (directories == null || directories.length == 0)
			return targets;
		// 对存储子文件夹排序
		LinkedList<File> directoriesList = new LinkedList<File>();
		Collections.addAll(directoriesList, directories);
		Collections.sort(directoriesList);
		// 遍历存储子文件夹
		Iterator<File> directoryIterator = directoriesList.iterator();
		while (directoryIterator.hasNext())
		{
			// 得到一个请求目录
			File directory = directoryIterator.next();
			// 如果是文件,告警并删除
			if (directory.isFile())
			{
				this.log.warn("扫描目标文件,一级目录底下存在非法文件[" + directory.getName() + "],文件被删除.");
				directory.delete();
				continue;
			}
			// 如果非文件夹，跳过不处理
			if (!directory.isDirectory())
				continue;
			// 遍历并装载分析文件
			File[] files = directory.listFiles();
			// 删除空文件夹
			if (files == null || files.length == 0)
			{
				if (directoryIterator.hasNext())
					directory.delete();
				continue;
			}
			// 装载分析源请求文件
			for (int i = 0; i < files.length; i++)
			{
				File targetFile = files[i];
				// 如果非文件，跳过不处理
				if (targetFile == null || !targetFile.isFile())
					continue;
				// 如果文件创建日期离当前时间没有一秒的时间差，暂不处理
				if (System.currentTimeMillis() - targetFile.lastModified() <= this.targetTransmitInterval)
					continue;
				// 验证文件名称是否符合
				String fileName = targetFile.getName();
				if (!fileName.startsWith(this.targetFilePrefix) || !fileName.endsWith(this.targetFileSuffix))
					continue;
				// 保存符合条件的目标文件到集合
				targets.add(targetFile);
				// 如果本次扫描目标文件已经达到批次容量，暂停扫描
				if (targets.size() >= this.scanBatchCapacity)
					break;
			}
		}
		return targets;
	}
}
