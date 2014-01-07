package test;

import java.util.ArrayList;
import java.util.List;

import json.JsonUtil;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedTest {
	public static MemCachedClient memCachedClient;
	
	public static void main(String[] args) {
		String[] serverlist = { "127.0.0.1:11211"};
		memCachedClientInstance(serverlist);
		UserBrandCampaign userBrandCampaign = new UserBrandCampaign("lin","123456789lkjhgf","45641ds3fsd4sd",4738,3223);
		UserBrandCampaign userBrandCampaign1 = new UserBrandCampaign("lin1","1234fdlkjhgf","456dfs13fsd4sd",47328,5223);
		UserBrandCampaign userBrandCampaign2 = new UserBrandCampaign("lin2","1234567fds89lkjhgf","fdsfsd13fsd4sd",323738,42323);
		List<UserBrandCampaign> list = new ArrayList<UserBrandCampaign>();
		list.add(userBrandCampaign);
		list.add(userBrandCampaign1);
		list.add(userBrandCampaign2);
		
		UserBrand userBrand = new UserBrand(System.currentTimeMillis(),list,"penglin");
		
//		memCachedClient.add("penglin", userBrand);
		String json = JsonUtil.beanToJson(userBrand);
		memCachedClient.set("penglin", null);
//		memCachedClient.flushAll();  //使memcache中的所有数据失效
		Object obj = memCachedClient.get("penglin");
		System.out.println(obj);
	}
	
	private static void memCachedClientInstance(String[] servers)
	{
		//API地址
		// tp:// www.geelou.com/javadocs/java_memcached-release_2.0.1/com/danga/MemCached/SockIOPool.html
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
		pool.setMaxBusyTime(5*60*1000);   
		
		pool.initialize();
		
		memCachedClient = new MemCachedClient();
	}
}
