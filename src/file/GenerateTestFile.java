package file;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import cn.adsit.common.util.DateUtil;
import cn.adsit.common.util.FileUtil;
public class GenerateTestFile extends Thread{
	private static Random random = new Random();
	
	
	private static String demandPrefix = "hive";
	private static String demandSuffix = "log";
	private static String demandDirectory = "/test/penglin/hiveTestFile/";
	
	private static int demandCount = 0;
	
	private static List<Thread> threadList = new ArrayList<Thread>();
	
	private static Object obj = new Object();
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<100;i++){
			Thread t = new GenerateTestFile();
			threadList.add(t);
			t.start();
		}
		validate();
	}

	
	private static void validate(){
		boolean isHasAlive = true;
		while (isHasAlive)
		{
			isHasAlive = false;
			for (int i = 0; i < threadList.size() && !isHasAlive; i++)
			{
				Thread popThread = threadList.get(i);
				isHasAlive |= !(popThread.getState() == Thread.State.TERMINATED);
			}
			System.out.println("count:"+demandCount);
			System.out.println("-------------------------------");
			try
			{
				Thread.sleep(10 * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	@Override
	public void run() {
		try {
			List<String> contentList = new ArrayList<String>();
			String subDirectory = DateUtil.getCurrSimpleTime().substring(0, 12);
			String directory = demandDirectory+subDirectory;
			String fileName = directory + "/"+ demandPrefix+"_"+genUUID()+"."+demandSuffix;
			for(int i=0;i<100;i++){
				for(int j=0;j<1000;j++){
					String uuid = genUUID();
					String visitorId = "visitorId_"+getRandom(random, 10000000);
					contentList.add(uuid+","+visitorId+"\n");
					synchronized (obj) {
						demandCount++;
					}
				}
				
				genFile(contentList, directory, fileName);
				contentList.clear();
				
//				File file = new File(fileName);
//				long length = file.length();
//				if(length>1000000){
//					subDirectory = DateUtil.getCurrSimpleTime().substring(0, 12);
//					directory = demandDirectory+subDirectory;
//					fileName = directory + "/"+ demandPrefix+"_"+genUUID()+"."+demandSuffix;
//				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String genUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private static void genFile(List<String> contentList, String directory,String fileName) throws Exception{
		FileUtil.makeDirectory(directory);
		FileWriter writer = new FileWriter(fileName,true);
		for(String content:contentList){
//			writer.write(content);
			writer.append(content);
		}
		writer.flush();
		writer.close();
	}
	
	private static int getRandom(Random random,int radis){
		int r = random.nextInt(radis);
		while(r<0){
			r = random.nextInt(radis);
		}
		return r;
	}
}
