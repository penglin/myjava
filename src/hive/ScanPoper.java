/*
 * adsit
 * 2011-9-23
 */
package hive;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.service.HiveServerException;
import org.apache.thrift.TException;

import cn.adsit.common.mission.MissionQueue;
import cn.adsit.common.mission.Poper;

public class ScanPoper extends Poper
{
	/**
     * 日志对象
     */
    private Log log = LogFactory.getLog(ScanPoper.class);
	// 正在upload的目录
	private MissionQueue ScaningLogQueue;

	// userlog的存放路径
	private File PersistFilePath;

	private HiveThriftOperator hiveOperator;
	
	@Override
	public void init()
	{
		this.ScaningLogQueue = this.missionQueues.get("ScaningLogQueue");
		this.PersistFilePath = new File(this.properties.getProperty("PersistFilePath","d://adsit//temp/hiveFile/").trim());

		this.hiveOperator = new HiveThriftOperator(this.properties.getProperty("HiveOperator.Host", "192.168.1.143"), Integer.parseInt(this.properties.getProperty("HiveOperator.Part", "10000")), this.properties.getProperty("HiveOperator.DBName","penglin_test"));
	}

	@Override
	public void pop()
	{
		try {
			Set<File> logFiles = this.scan();

			for (File userLogDirectory : logFiles)
			{
				// 判断文件有效性
				if (!userLogDirectory.exists() || !userLogDirectory.canRead())
				{
					continue;
				}

				// 把文件名放入站在解析队列  
				if (!this.ScaningLogQueue.putMission(userLogDirectory.getAbsolutePath(), userLogDirectory.getAbsolutePath()))
				{
					continue;
				}

				this.uploadHive(userLogDirectory);

				this.deleteDirectory(userLogDirectory);

			}
		} catch (HiveServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void uploadHive(File userLog) throws Exception
	{
		// 目录有效性验证
		if (userLog.listFiles().length == 0)
		{
			return;
		}

		if (FileUtils.listFiles(userLog, new String[] { "log" }, true).size() == 0)
		{
			return;
		}

		// 按照文件夹规则 取得hive需要的分区字段
		String dire = StringUtils.substringAfter(userLog.getAbsolutePath(), this.PersistFilePath.getAbsolutePath());
		String[] split = StringUtils.split(dire, File.separator);
//		String source_id = split[0];
//		String visit_date = split[1];

		// 构造导入语句
		StringBuilder sb = new StringBuilder();
		sb.append("load data local inpath '" + userLog.getAbsolutePath() + "' ");
		sb.append("into table  visitor_id_360 ");
//		sb.append(MessageFormat.format("partition (uuid=''{0}'',visitor_id=''{1}'')", source_id, visit_date));

		this.log.info("hive command : " + sb.toString());
		long currTime = System.currentTimeMillis();
		this.hiveOperator.execute(sb.toString());
		log.info("load 一个文件["+userLog.list().length+"]个,耗时(毫秒)"+(System.currentTimeMillis()-currTime));

	}

	private void deleteDirectory(File userLogDirectory) throws IOException
	{
		FileUtils.deleteDirectory(userLogDirectory);

		// remove 正在扫描
		this.ScaningLogQueue.getMission(userLogDirectory.getAbsolutePath(), true);
	}

	private Set<File> scan()
	{
		Set<File> dires = new HashSet<File>();

		this.listDirectory(dires, this.PersistFilePath);

		return dires;
	}

	private SimpleDateFormat direDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

	// 递归取得需要的文件夹
	private void listDirectory(Collection<File> dires, File directory)
	{
		File[] found = directory.listFiles();

		// 文件夹递归
		if (found != null)
		{
			// 空目录删除
			if (found.length == 0)
			{
				directory.delete();
			}

			for (File file : found)
			{
				if (file.isDirectory())
				{
					// 裁掉配置路径,取得由程序维护的路径结构
					String dire = StringUtils.substringAfter(file.getAbsolutePath(), this.PersistFilePath.getAbsolutePath());

//					String[] split = StringUtils.split(dire, File.separator);
					File[] files = file.listFiles();
					long time = 0L;
					for(File tmp : files){
						time = Math.max(time, tmp.lastModified());
					}

					// 如果文件创建日期离当前时间没有一秒的时间差，暂不处理
					if (System.currentTimeMillis() - time >= 120000){
						dires.add(file);
					}
				}
			}
		}
	}
}

