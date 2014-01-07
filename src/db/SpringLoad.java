package db;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringLoad {
	/**
	 * Spring应用上下文对象
	 */
	public static ApplicationContext applicationContext = null;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 得到Spring配置文件列表
//		PropertyConfigurator.configure("log4j.properties");
		List<String> springConfigFiles = new ArrayList<String>();
		String loaderPath = URLDecoder.decode(SpringLoad.class.getClassLoader().getResource("").getPath(), "UTF-8");
		// 定位Spring配置文件路径
		loaderPath = loaderPath.substring(0, loaderPath.lastIndexOf("bin"));
		loaderPath = loaderPath + "spring";
		File[] files = new File(loaderPath).listFiles();
		for (int i = 0; i < files.length; i++)
		{
			String fileName = files[i].getName();
			if (fileName.startsWith("applicationContext") && fileName.endsWith(".xml"))
				springConfigFiles.add(files[i].getPath());
		}
		applicationContext = new FileSystemXmlApplicationContext(springConfigFiles.toArray(new String[] {}));
		TestService service = (TestService) applicationContext.getBean("siteReportService");
		/*String beginDate = "2012年08月06日";
		String endDate = "2012年08月06日";
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" gather_date,");
		sql.append("sum(demand_count) as demand_count,");
		sql.append("sum(demand_unique_count) as demand_unique_count");
		sql.append(" ,(case when gather_date='2012年05月23日' then ");
		sql.append(" (select sum(demand_unique_count)");
		sql.append(" from campaign_track_date_gather");
		sql.append(" where campaign_id='NLILFGU70024'");
		sql.append(" and report_type=2");
		sql.append(" and gather_date='2012年05月23日')");
		sql.append(" else");
		sql.append(" ((select sum(demand_unique_count) ");
		sql.append(" from campaign_track_gather");
		sql.append(" where gather_end_date=gather_date");
		sql.append(" and campaign_id='NLILFGU70024'");
		sql.append(" and report_type=5)");
		sql.append(" -");
		sql.append(" (select sum(demand_unique_count) ");
		sql.append(" from campaign_track_gather");
		sql.append(" where gather_end_date=datename(\"yy\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
		sql.append(" +'年'+datename(\"mm\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
		sql.append(" +'月'+");
		sql.append(" substring('00',0,3-len(datename(\"dd\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))))");
		sql.append(" +datename(\"dd\",dateadd(\"dd\",-1,cast(replace(replace(replace(gather_date,'年','-'),'月','-'),'日',' 00:00:00') as datetime)))");
		sql.append(" +'日'");
		sql.append(" and campaign_id='NLILFGU70024'");
		sql.append(" and report_type=5))");
		sql.append(" end) as xinz");
		sql.append(" from campaign_track_date_gather");
		sql.append(" where campaign_id='NLILFGU70024'");
		sql.append(" and report_type=2");
		sql.append(" and gather_date between  '" + beginDate + "' and '" + endDate + "'");
		sql.append(" group by gather_date");
		sql.append(" order by gather_date");
		List<Object[]> list = service.query(sql.toString());
		System.out.println(list);*/
		
		try {
			String sql2 = "update adman_core.dbo.ad set Ad_Class_Id='-' where Ad_Id='0a6b2420229140aabcd19aeec8d8fd41'";
			service.update("insert into test values('1','222452')");
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
			e.printStackTrace();
		}
		System.out.println("更新成功");
	}

}
