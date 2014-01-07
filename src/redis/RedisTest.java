package redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import redis.clients.jedis.Jedis;


public class RedisTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RedisTest.class);

	public static void main(String[] args) {
//		keys attemp*
		Jedis jedis = new Jedis("192.168.1.185", 6386);
		System.out.println(jedis.dbSize());
		long t = System.currentTimeMillis();
		Set<String> keys = jedis.keys("*");
		List<String> delKeys = new ArrayList<String>();
		for(String key : keys){
			System.out.println(key);
			if(key.equals("Precise_RealTime_Process_Info")){
				continue;
			}
			System.out.println(jedis.ttl(key));
//			jedis.expire(key, 3600);
		}
		
//		jedis.set("Test_Redis_Expirt", "jjjjjj");
//		jedis.expire("Test_Redis_Expirt", 60);
//		System.out.println(jedis.ttl("Test_Redis_Expirt"));
//		jedis.set("Test_Redis_Expirt", "jjjjjj");
//		System.out.println(jedis.ttl("Test_Redis_Expirt"));
		
//		for(int i=0;i<100000;i++){
//			jedis.hincrBy("penglin", "lfdsfdsfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsfdsfsdfsfdfsdfsfdsfsfdsfdsfsdfsdfsdfsdfsdfsdin"+i, i);
//		}
//		System.out.println(System.currentTimeMillis() - t);
		/*String[] redisPorts = new String[]{"6381","6382","6383","6384","6385","6386"}; 
		for(final String port : redisPorts){
			for(int i=0;i<20;i++){
				new Thread(new Runnable() {
					Jedis jedis = new Jedis("192.168.1.200", Integer.parseInt(port));
					Random r = new Random();
					@Override
					public void run() {
						long t = System.currentTimeMillis();
						for(int i=0;i<100000;i++){
//						jedis.hset("penglin", "lfdsfdsfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsfdsfsdfsfdfsdfsfdsfsfdsfdsfsdfsdfsdfsdfsdfsdin"+r.nextInt(100000), i+"");
//						jedis.hincrBy("penglin", "lfdsfdsfsdfsdfsdfsdfsdf/sdfsdfsdfsdfsdfsdfsfdsfsdfsfdfsdfsfdsfsfdsfdsfsdfsdfsdfsdfsdfsdin"+r.nextInt(100000), i);
							jedis.set("peglin"+r.nextInt(100000), i+"");
//						jedis.incrBy("peglin"+r.nextInt(100000), i);
						}
						System.out.println(System.currentTimeMillis() - t);
					}
				}).start();
			}
		}*/
	}
	
	
	
	@Test
	public void redisTest(){
		Jedis jedis = new Jedis("192.168.0.164", 6379);
		jedis.hset("penglin", "lin", "1");
		
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("lin2", "2");
		map.put("lin3", "3");
		map.put("lin4", "4");
		
		jedis.hmset("penglin", map);
		
		Map<String,String> redisMap = jedis.hgetAll("penglin");
		System.out.println(redisMap.size());
	}
}

class RedisMapTest{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RedisMapTest.class);
	
}