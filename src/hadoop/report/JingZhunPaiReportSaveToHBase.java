package hadoop.report;

import hadoop.report.pojo.AdPosFrequencyGather;
import hadoop.report.pojo.AdPosZoneSegmentGather;
import hadoop.report.pojo.common.Gather;
import hbase.util.HBaseUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JingZhunPaiReportSaveToHBase extends Configured implements Tool {
	private static final Log log = LogFactory.getLog(JingZhunPaiReportSaveToHBase.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("v0.1");
			log.info("JingZhunPaiReport start");
			int res = ToolRunner.run(new Configuration(), new JingZhunPaiReportSaveToHBase(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(JingZhunPaiReportSaveToHBase.class);
		job.setJobName("Save_In_Reduce");
		
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setMapperClass(ReportMap.class);
		job.setCombinerClass(ReportCombiner.class);
		job.setReducerClass(ReportReduce.class);
		job.setNumReduceTasks(18);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileSystem fs = FileSystem.newInstance(getConf());
		Path path = new Path("/penglin/reportStatistics2");
		if(fs.exists(path)){
			fs.delete(path, true);
		}
//		Path[] paths = new Path[]{new Path("/penglin/reportSource2")};
		Path[] paths = new Path[]{new Path("/penglin/report2/201203211229"),
				new Path("/penglin/report2/201203211314"),
				new Path("/penglin/report2/201203211358"),
				new Path("/penglin/report2/201203211359"),
				new Path("/penglin/report2/201203211443"),
				new Path("/penglin/report2/201203211444")};
		FileInputFormat.setInputPaths(job, paths);
		FileOutputFormat.setOutputPath(job, path);
		
		boolean success = job.waitForCompletion(true);
		
		return success ? 0 : 1;
	}
	
	public static String getVideoPlayMapKeyUseJSON(JSONObject jsonObject, String[] keys) {
		StringBuffer sb = new StringBuffer();
		for(String key : keys){
			sb.append(jsonObject.getString(key));
			sb.append("$");
		}
		return sb.toString();
	}

	public static class ReportMap extends Mapper<LongWritable, Text, Text, Text> {
		private Text outputKey = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			if (value == null || value.toString().isEmpty())
				return;

			String jsonString = value.toString();
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			String userId = jsonObject.getString("userId");
			outputKey.set(userId);
			context.write(outputKey, value);
		}
	}
	
	/**
	 * Combine 
	 * @author 彭霖
	 *
	 */
	public static class ReportCombiner extends Reducer<Text, Text, Text, Text> {
		private Text outputText = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			StringBuffer sb = new StringBuffer();
			for(Text value : values){
				sb.append(value);
				sb.append("\n");
			}
			sb.deleteCharAt(sb.lastIndexOf("\n"));
			outputText.set(sb.toString());
			context.write(key, outputText);
		}
	}

	public static class ReportReduce extends Reducer<Text, Text, NullWritable, NullWritable> {
		private static Map<String, Gather> adPosZoneSegmentMap = null;
		private static Map<String, Gather> adPosZoneFrequencyMap = null;
		private final static String[] adPosZoneSegmentKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay","videoDemandHour"};
		private final static String[] adPosZoneFrequencyKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay"};
		
		private static Configuration conf;
		
		private HTable adPosZoneSegmentTable = null;
		private final static String adPosZoenSegmentTableName = "Ad_Pos_Zone_Segment_Gather";
		
		private HTable adPosZoneFrequencyTable = null;
		private final static String adPosZoenFrequencyTableName = "Ad_Pos_Zone_Frequency_Gather";
		
		private JSONObject jsonObject = null;
		
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
			reportCacheClean(context);
			
			adPosZoneSegmentMap = null;
			adPosZoneFrequencyMap = null;
			
			context.write(NullWritable.get(), NullWritable.get());
		}
		
		/**
		 * 输出各个统计报表的数据
		 * @param context
		 * @throws IOException
		 * @throws InterruptedException
		 */
		private void reportCacheClean(Context context) throws IOException, InterruptedException{
			reduceOutput(adPosZoneSegmentMap, "AdPosZoneSegment", adPosZoneSegmentTable, context);
			adPosZoneSegmentMap.clear();
			
			reduceOutput(adPosZoneFrequencyMap, "AdPosZoneFrequency", adPosZoneFrequencyTable, context);
			adPosZoneFrequencyMap.clear();
		}
		
		/**
		 * 
		 * @param map
		 * @param outPutKey
		 * @param context
		 * @throws IOException
		 * @throws InterruptedException
		 */
		private static long currTime = System.currentTimeMillis(); 
		private void reduceOutput(Map<String,Gather> map, String outPutKey, HTable htable, Context context) throws IOException, InterruptedException {
			if(map==null || map.isEmpty())
				return ;
			Set<String> entrySet = map.keySet();
			Iterator<String> it = entrySet.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Gather gather = map.get(key);
			
				Put put = new Put();
				
				Increment increment = new Increment(Bytes.toBytes(key.toString()));
				gather.initIncrement(increment,gather.getFamily(),gather,gather.getPointers());
				
				long tmpTime = System.currentTimeMillis();
				htable.increment(increment);
				if(System.currentTimeMillis() - currTime >5*1000){
					log.info("write HBase cost time:"+(System.currentTimeMillis() - tmpTime));
					currTime = System.currentTimeMillis();
				}
			}
			map.clear();
		}
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
			adPosZoneSegmentMap = new HashMap<String, Gather>();
			adPosZoneFrequencyMap = new HashMap<String, Gather>();
			
			try {
				conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
			} catch (Exception e) {
			}
			
			adPosZoneSegmentTable = new HTable(conf,adPosZoenSegmentTableName);
			adPosZoneSegmentTable.setAutoFlush(false);
			adPosZoneSegmentTable.setWriteBufferSize(5 * 1024 * 1024);
			
			adPosZoneFrequencyTable = new HTable(conf,adPosZoenFrequencyTableName);
			adPosZoneFrequencyTable.setAutoFlush(false);
			adPosZoneFrequencyTable.setWriteBufferSize(5 * 1024 * 1024);
		}

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			try {
				for(Text value : values){
					//地域时段报表统计
					adPosZoneSegmentReport(key, value, context);
					
					//计算每个用户的在指定维度出现的次数
					adPosZoneFrequencyVisitorCount(value);
				}
				
				//频次报表统计
				adPosZoneFrequencyReport(frequencyCount, adPosZoneFrequencyMap);
				
				//防止执行Reduce时出现异常而重新执行造成的数据不准确，每一次Reduce就输出一次
				reportCacheClean(context);
			} catch (Exception e) {
				log.error("执行Reduce时出现异常", e);
				throw new InterruptedException(e.getMessage());
			}
		}
		
		private void adPosZoneSegmentReport(Text key, Text value, Context context) throws Exception {
			if (value == null || value.toString().isEmpty())
				return ;
			jsonObject = JSONObject.fromObject(value.toString());
			String mapKey = getVideoPlayMapKeyUseJSON(jsonObject, adPosZoneSegmentKeys);
			AdPosZoneSegmentGather gather = (AdPosZoneSegmentGather)adPosZoneSegmentMap.get(mapKey);
			if (gather == null) {
				gather = new AdPosZoneSegmentGather();
				setReportGatherUseJSON(gather, jsonObject);
				adPosZoneSegmentMap.put(mapKey, gather);
			} else {
				gather.setDemandCount(gather.getDemandCount() + 1L);
			}
		}

		private void setReportGatherUseJSON(AdPosZoneSegmentGather gather, JSONObject jsonObject) throws Exception{
			gather.setCampaignId(jsonObject.getString("orderId"));
			gather.setCampaignTrackId(jsonObject.getString("advertiserVideoId"));
			gather.setWorkstationId(jsonObject.getString("workstationId"));
			gather.setAdPosId(jsonObject.getString("adPosId"));
			gather.setZoneProvince(jsonObject.getString("zoneProvince"));
			gather.setZoneCity(jsonObject.getString("zoneName"));
			gather.setGatherDate(jsonObject.getString("videoDemandDay"));
			gather.setGatherHour(jsonObject.getString("videoDemandHour"));

			gather.setDemandCount(1L);
			gather.setDemandUniqueCount(1L);
		}
		
		private static Map<String,Long> frequencyCount = new HashMap<String, Long>();
		private void adPosZoneFrequencyVisitorCount(Text value) throws Exception{
			if (value == null || value.toString().isEmpty())
				return ;
			
			jsonObject = JSONObject.fromObject(value.toString());
			String mapKey = getVideoPlayMapKeyUseJSON(jsonObject, adPosZoneFrequencyKeys);
			Long count = frequencyCount.get(mapKey);
			if(count==null){
				frequencyCount.put(mapKey, 1L);
			}else{
				frequencyCount.put(mapKey, count + 1L);
			}
		}
		
		private void adPosZoneFrequencyReport(Map<String, Long> frequencyCount, Map<String, Gather> adPosZoneFrequencyMap){
			adPosZoneFrequencyMap.clear();
			
			Set<String> mapKeys = frequencyCount.keySet();
			for(String mapKey : mapKeys){
				Long count = frequencyCount.get(mapKey);
				AdPosFrequencyGather gather = (AdPosFrequencyGather)(adPosZoneFrequencyMap.get(mapKey));
				if(gather==null){
					int i = 0;
					String[] keys = mapKey.split("\\$");
					gather = new AdPosFrequencyGather();
					gather.setCampaignId(keys[i++]);
					gather.setCampaignTrackId(keys[i++]);
					gather.setWorkstationId(keys[i++]);
					gather.setAdPosId(keys[i++]);
					gather.setZoneProvince(keys[i++]);
					gather.setZoneCity(keys[i++]);
					gather.setGatherDate(keys[i++]);
				}
				
				gather.frequency(count.intValue());
				adPosZoneFrequencyMap.put(mapKey, gather);
			}
			//清除上个Reduce时的缓存数据
			frequencyCount.clear();
			
		}
	}
	
	public static void setValueByPropertyName(Object obj,String propertyName,Class[] classes,Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method methode = obj.getClass().getDeclaredMethod(propertyName, classes);
		methode.invoke(obj, args);
	}
	
}
