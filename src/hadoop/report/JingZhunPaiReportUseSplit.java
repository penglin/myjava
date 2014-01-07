package hadoop.report;

import file.pojo.MyVideoPlay;
import hadoop.report.pojo.AdPosFrequencyGather;
import hadoop.report.pojo.AdPosZoneSegmentGather;
import hadoop.report.pojo.common.Gather;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JingZhunPaiReportUseSplit extends Configured implements Tool {
	private static final Log log = LogFactory.getLog(JingZhunPaiReportUseSplit.class);
	private static final String SPLIT_STRING = new String(new char[]{1});
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("v0.1");
			log.info("JingZhunPaiReport start");
			int res = ToolRunner.run(new Configuration(), new JingZhunPaiReportUseSplit(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(JingZhunPaiReportUseSplit.class);
		job.setJobName("Use Split");
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setMapperClass(ReportMap.class);
//		job.setCombinerClass(ReportCombiner.class);
		job.setReducerClass(ReportReduce.class);
		job.setNumReduceTasks(43);
		
		job.setInputFormatClass(TextInputFormat.class);
//		job.setOutputFormatClass(TextOutputFormat.class);
		
		getConf().setLong("dfs.block.size", 256000000L);
//		getConf().setBoolean("mapred.compress.map.output", true);
//		getConf().setBoolean("mapred.output.compress", true);
//		getConf().set("mapred.output.compression.type", "BLOCK");
//		getConf().setClass("mapred.output.compression.codec", SnappyCodec.class, CompressionCodec.class);
		
//		FileOutputFormat.setCompressOutput(job, true);
//		FileOutputFormat.setOutputCompressorClass(job,SnappyCodec.class);
		
		
		getConf().setBoolean("mapred.compress.map.output", true);
		getConf().setBoolean("mapred.output.compress", true);
		getConf().set("mapred.output.compression.type", "BLOCK");
		getConf().setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job,GzipCodec.class);
		
		FileSystem fs = FileSystem.newInstance(getConf());
		Path path = new Path("/penglin/reportStatistics");
		if(fs.exists(path)){
			fs.delete(path, true);
		}
//		Path[] paths = new Path[]{new Path("/penglin/SplitTest")};
		Path[] paths = new Path[]{new Path("/penglin/reportSource/201203301829"),
				new Path("/penglin/reportSource/201203301938"),
				new Path("/penglin/reportSource/201203301939"),
				new Path("/penglin/reportSource/201203301940")};
		FileInputFormat.setInputPaths(job, paths);
		FileOutputFormat.setOutputPath(job, path);
		
		MultipleOutputs.addNamedOutput(job, "AdPosZoneSegment", TextOutputFormat.class,
				 NullWritable.class, Text.class);
		
		MultipleOutputs.addNamedOutput(job, "AdPosZoneFrequency", TextOutputFormat.class,
				 NullWritable.class, Text.class);
		
		boolean success = job.waitForCompletion(true);
		
//		boolean success = true;
		if(success){
			success = runAdPosZoneSegmentReportJob();
		}
		
		if(success){
			success = runAdPosZoneFrequencyReportJob();
		}
		
		return success ? 0 : 1;
	}
	
	/**
	 * 执行广告位地域时段报表
	 * @param fs
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	private boolean runAdPosZoneSegmentReportJob() throws IOException, InterruptedException, ClassNotFoundException{
		Job job = initReportJob("Statistics_Zone_Segment_Report", NullWritable.class, Text.class, Text.class,
				Text.class, ReportStatisticsMap.class, ReportCombiner.class, AdPosZoneSegmentReportStatisticsReduce.class, 18, 
				TextInputFormat.class, TextOutputFormat.class);
		Configuration conf = getConf();
		conf.setBoolean("mapred.output.compress", true);
		conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		
		FileSystem fs = FileSystem.newInstance(conf);
		String inputPath = "/penglin/reportStatistics";
		String filterName = "AdPosZoneSegment";
		String outputPath = "/penglin/segment";
		
		job.getConfiguration().set("className", "hadoop.report.pojo.AdPosZoneSegmentGather");
		
		return runReportJob(fs, job, inputPath, filterName, outputPath);
	}
	
	private boolean runAdPosZoneFrequencyReportJob() throws IOException, InterruptedException, ClassNotFoundException{
		Job job = initReportJob("Statistics_Zone_Frequency_Report", NullWritable.class, Text.class, Text.class,
				Text.class, ReportStatisticsMap.class, ReportCombiner.class, AdPosZoneFrequencyReportStatisticsReduce.class, 18, 
				TextInputFormat.class, TextOutputFormat.class);
		Configuration conf = getConf();
		conf.setBoolean("mapred.output.compress", true);
		conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		
		FileSystem fs = FileSystem.newInstance(getConf());
		String inputPath = "/penglin/reportStatistics";
		String filterName = "AdPosZoneFrequency";
		String outputPath = "/penglin/frequency";
		job.getConfiguration().set("className", "hadoop.report.pojo.AdPosFrequencyGather");
		
		return runReportJob(fs, job, inputPath, filterName, outputPath);
	}
	
	private boolean runReportJob(FileSystem fs, Job job, String inputPath, String filterName, String outputPath) throws IOException, InterruptedException, ClassNotFoundException{
		Path outpath = new Path(outputPath);
		if(fs.exists(outpath)){
			fs.delete(outpath, true);
		}
		FileInputFormat.setInputPaths(job, getInputPath(inputPath,filterName));
		FileOutputFormat.setOutputPath(job, outpath);
		
		return job.waitForCompletion(true);
	}

	private Job initReportJob(String jobName, Class<? extends Writable> outputKeyClass, Class<? extends Writable> outputValueClass, 
			Class<? extends Writable> mapOutputKeyClass, Class<? extends Writable> mapOutputValueClass, 
			Class<? extends Mapper> mapperClass, Class<? extends Reducer> combinerClass, Class<? extends Reducer> reducerClass, 
			int reduceNum, Class<? extends InputFormat> input, Class<? extends OutputFormat> output) throws IOException{
		Job statisticJob = new Job(getConf());
		statisticJob.setJarByClass(JingZhunPaiReportUseSplit.class);
		statisticJob.setJobName(jobName);
		
		statisticJob.setOutputKeyClass(outputKeyClass);
		statisticJob.setOutputValueClass(outputValueClass);
		
		statisticJob.setMapOutputKeyClass(mapOutputKeyClass);
		statisticJob.setMapOutputValueClass(mapOutputValueClass);

		statisticJob.setMapperClass(mapperClass);
		if(combinerClass!=null){
			statisticJob.setCombinerClass(combinerClass);
		}
		statisticJob.setReducerClass(reducerClass);
		
		if(reduceNum!=0){
			statisticJob.setNumReduceTasks(reduceNum);
		}
		
		statisticJob.setInputFormatClass(input);
		statisticJob.setOutputFormatClass(output);
		
		return statisticJob;
	}
	
	private Path[] getInputPath(String pathName, final String filterName) throws IOException{
		FileSystem fs = FileSystem.newInstance(getConf());
		Path path = new Path(pathName);
		FileStatus[] fileStatus = fs.listStatus(path, new PathFilter(){
			@Override
			public boolean accept(Path p) {
				return p.getName().contains(filterName);
			}
			
		});
		
		return FileUtil.stat2Paths(fileStatus);
	}
	
/*	public static String getVideoPlayMapKeyUseJSON(JSONObject jsonObject, String[] keys) {
		StringBuffer sb = new StringBuffer();
		for(String key : keys){
			sb.append(jsonObject.getString(key));
			sb.append("$");
		}
		return sb.toString();
	}*/

	public static class ReportMap extends Mapper<LongWritable, Text, Text, Text> {
		private Text outputKey = new Text();
		private long count = 0L;
		private long currTime = System.currentTimeMillis();
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			if (value == null || value.toString().isEmpty())
				return;
			count++;
			
			String jsonString = value.toString();
			String[] splits = jsonString.split(SPLIT_STRING);
			String userId = splits[2];
			
			outputKey.set(userId);
			context.write(outputKey, value);
			if(count==10000){
				log.info("10000 cost time:"+(System.currentTimeMillis() - currTime));
				count = 0L;
				currTime = System.currentTimeMillis();
			}
				
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

	public static class ReportReduce extends Reducer<Text, Text, Text, Text> {
		private static Map<String, AdPosZoneSegmentGather> adPosZoneSegmentMap = null;
		private static Map<String, AdPosFrequencyGather> adPosZoneFrequencyMap = null;
		private final static String[] adPosZoneSegmentKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay","videoDemandHour"};
		private final static String[] adPosZoneFrequencyKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay"};
//		private final static String[] adPosZoneFrequencyKeys = new String[]{"orderId","workstationId","videoDemandDay"};
		private static final String SPLIT_STRING = new String(new char[]{1});;
		
		private MultipleOutputs<Object,Object> mos;
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
			reportCacheClean(context);
			
			adPosZoneSegmentMap = null;
			adPosZoneFrequencyMap = null;
			
			mos.close();
		}
		
		/**
		 * 输出各个统计报表的数据
		 * @param context
		 * @throws IOException
		 * @throws InterruptedException
		 */
		private void reportCacheClean(Context context) throws IOException, InterruptedException{
			reduceOutput(adPosZoneSegmentMap, "AdPosZoneSegment", context);
			adPosZoneSegmentMap.clear();
			
			reduceOutput(adPosZoneFrequencyMap, "AdPosZoneFrequency", context);
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
		private void reduceOutput(Map map, String outPutKey, Context context) throws IOException, InterruptedException {
			if(map==null || map.isEmpty())
				return ;
			Set entrySet = map.keySet();
			Iterator it = entrySet.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				Object key = it.next();
				sb.append(map.get(key));
				sb.append("\n");
			}
			
			sb.deleteCharAt(sb.lastIndexOf("\n"));
			mos.write(outPutKey, NullWritable.get(), sb);
			map.clear();
		}
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
			adPosZoneSegmentMap = new HashMap<String, AdPosZoneSegmentGather>();
			adPosZoneFrequencyMap = new HashMap<String, AdPosFrequencyGather>();
			
			mos = new MultipleOutputs(context);
		}

		private long count = 0L;
		long tmpTime = System.currentTimeMillis();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			try {
				for(Text value : values){
					MyVideoPlay play = new MyVideoPlay(value.toString().split(SPLIT_STRING));
					//地域时段报表统计
					adPosZoneSegmentReport(play);
					
					//计算每个用户的在指定维度出现的次数
					adPosZoneFrequencyVisitorCount(play);
				}
				
				//频次报表统计
				adPosZoneFrequencyReport(frequencyCount, adPosZoneFrequencyMap);
				
				//防止执行Reduce时出现异常而重新执行造成的数据不准确，每一次Reduce就输出一次
				reportCacheClean(context);
				
				count++;
				if(count==1000){
					log.info("1000 cost time:"+(System.currentTimeMillis() - tmpTime));
					count = 0L;
					tmpTime = System.currentTimeMillis();
				}
			} catch (Exception e) {
				log.error("执行Reduce时出现异常", e);
				throw new InterruptedException(e.getMessage());
			}
		}
		
		private void adPosZoneSegmentReport(MyVideoPlay play) throws Exception {
			if (play == null)
				return ;
			
//			String mapKey = getVideoPlayMapKeyUseJSON(jsonObject, adPosZoneSegmentKeys);
//			sb.append(jsonObject.getString(key));
			StringBuffer mapKey = new StringBuffer();
			mapKey.append(play.getOrderId());
			mapKey.append("$");
			mapKey.append(play.getAdvertiserVideoId());
			mapKey.append("$");
			mapKey.append(play.getWorkstationId());
			mapKey.append("$");
			mapKey.append(play.getAdPosId());
			mapKey.append("$");
			mapKey.append(play.getZoneProvince());
			mapKey.append("$");
			mapKey.append(play.getZoneName());
			mapKey.append("$");
			mapKey.append(play.getVideoDemandDay());
			mapKey.append("$");
			mapKey.append(play.getVideoDemandHour());
			mapKey.append("$");
			AdPosZoneSegmentGather gather = adPosZoneSegmentMap.get(mapKey);
			if (gather == null) {
				gather = new AdPosZoneSegmentGather();
				gather.setCampaignId(play.getOrderId());
				gather.setCampaignTrackId(play.getAdvertiserVideoId());
				gather.setWorkstationId(play.getWorkstationId());
				gather.setAdPosId(play.getAdPosId());
				gather.setZoneProvince(play.getZoneProvince());
				gather.setZoneCity(play.getZoneName());
				gather.setGatherDate(play.getVideoDemandDay());
				gather.setGatherHour(play.getVideoDemandHour()+"");

				gather.setDemandCount(1L);
				gather.setDemandUniqueCount(1L);
				
				adPosZoneSegmentMap.put(mapKey.toString(), gather);
			} else {
				gather.setDemandCount(gather.getDemandCount() + 1L);
			}
		}

		/*private void setReportGatherUseJSON(AdPosZoneSegmentGather gather, JSONObject jsonObject) throws Exception{
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
		}*/
		
		private static Map<String,Long> frequencyCount = new HashMap<String, Long>();
		private void adPosZoneFrequencyVisitorCount(MyVideoPlay play) throws Exception{
			if (play == null)
				return ;
			
			StringBuffer mapKey = new StringBuffer();
			mapKey.append(play.getOrderId());
			mapKey.append("$");
			mapKey.append(play.getAdvertiserVideoId());
			mapKey.append("$");
			mapKey.append(play.getWorkstationId());
			mapKey.append("$");
			mapKey.append(play.getAdPosId());
			mapKey.append("$");
			mapKey.append(play.getZoneProvince());
			mapKey.append("$");
			mapKey.append(play.getZoneName());
			mapKey.append("$");
			mapKey.append(play.getVideoDemandDay());
			mapKey.append("$");
			Long count = frequencyCount.get(mapKey);
			if(count==null){
				frequencyCount.put(mapKey.toString(), 1L);
			}else{
				frequencyCount.put(mapKey.toString(), count + 1L);
			}
		}
		
		private void adPosZoneFrequencyReport(Map<String, Long> frequencyCount, Map<String, AdPosFrequencyGather> adPosZoneFrequencyMap){
			adPosZoneFrequencyMap.clear();
			
			Set<String> mapKeys = frequencyCount.keySet();
			for(String mapKey : mapKeys){
				Long count = frequencyCount.get(mapKey);
				AdPosFrequencyGather gather = adPosZoneFrequencyMap.get(mapKey);
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
	
	public static class ReportStatisticsMap extends Mapper<LongWritable, Text, Text, Text>{
		private static String className = null;
		private Text outputKey = new Text();
		private long currTime = System.currentTimeMillis();
		private long count = 0L;
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			if(value==null || value.toString().trim().isEmpty())
				return ;
			count++;
			String mapKey = null;
			try {
				Class<?> gatherClass = Class.forName(className);
				Constructor<?> constructor = gatherClass.getConstructor(new Class[]{String.class});
				Gather gather = (Gather) constructor.newInstance(value.toString());
				mapKey = gather.getGatherKey();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			outputKey.set(mapKey);
			context.write(outputKey, value);
			if(count==10000){
				log.info("Init 10000 cost time:"+(System.currentTimeMillis() - currTime));
				count = 0L;
				currTime = System.currentTimeMillis();
			}
		}

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
			className = context.getConfiguration().get("className");
			log.info("Class Name:"+className);
		}
		
	}
	
	public static class AdPosZoneSegmentReportStatisticsReduce extends Reducer<Text, Text, NullWritable, Text>{
		private AdPosZoneSegmentGather gather = null;
		private Text output = new Text();
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			String[] keys = key.toString().split("\\$");
			gather = new AdPosZoneSegmentGather();
			gather.setCampaignId(keys[i++]);
			gather.setCampaignTrackId(keys[i++]);
			gather.setWorkstationId(keys[i++]);
			gather.setAdPosId(keys[i++]);
			gather.setZoneProvince(keys[i++]);
			gather.setZoneCity(keys[i++]);
			gather.setGatherDate(keys[i++]);
			gather.setGatherHour(keys[i++]);
			gather.setDemandCount(0L);
			gather.setDemandUniqueCount(0L);
			int j = 0;
			AdPosZoneSegmentGather tmpGather = null;
			for(Text value : values){
				if(value==null||value.toString().trim().isEmpty())
					continue;
				try {
					Class<?> gatherClass = Class.forName("hadoop.report.pojo.AdPosZoneSegmentGather");
					Constructor<?> constructor = gatherClass.getConstructor(new Class[]{String.class});
					tmpGather = (AdPosZoneSegmentGather) constructor.newInstance(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				} 
				long demandCount = tmpGather.getDemandCount();
				long demandUniqueCount = tmpGather.getDemandUniqueCount();
				gather.setDemandCount(gather.getDemandCount() + demandCount);
				gather.setDemandUniqueCount(gather.getDemandUniqueCount() + demandUniqueCount);
				j++;
			}
			
			output.set(gather.toString());
			context.write(NullWritable.get(), output);
		}
	}
	
	public static class AdPosZoneFrequencyReportStatisticsReduce extends Reducer<Text, Text, NullWritable, Text>{
		private AdPosFrequencyGather gather = null;
		private Text output = new Text();

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			String[] keys = key.toString().split("\\$");
			
			gather = new AdPosFrequencyGather();
			gather.setCampaignId(keys[i++]);
			gather.setCampaignTrackId(keys[i++]);
			gather.setWorkstationId(keys[i++]);
			gather.setAdPosId(keys[i++]);
			gather.setZoneProvince(keys[i++]);
			gather.setZoneCity(keys[i++]);
			gather.setGatherDate(keys[i++]);
			AdPosFrequencyGather tmpGather = null;
			for(Text value : values){
				if(value==null||value.toString().trim().isEmpty())
					continue;
				try {
					Class<?> gatherClass = Class.forName("hadoop.report.pojo.AdPosFrequencyGather");
					Constructor<?> constructor = gatherClass.getConstructor(new Class[]{String.class});
					tmpGather = (AdPosFrequencyGather) constructor.newInstance(value.toString());
				} catch (Exception e) {
					e.printStackTrace();
				} 
				gather.setFrequency1Count(tmpGather.getFrequency1Count() + gather.getFrequency1Count());
				gather.setFrequency2Count(tmpGather.getFrequency2Count() + gather.getFrequency2Count());
				gather.setFrequency3Count(tmpGather.getFrequency3Count() + gather.getFrequency3Count());
				gather.setFrequency4Count(tmpGather.getFrequency4Count() + gather.getFrequency4Count());
				gather.setFrequency5Count(tmpGather.getFrequency5Count() + gather.getFrequency5Count());
				gather.setFrequency6Count(tmpGather.getFrequency6Count() + gather.getFrequency6Count());
				gather.setFrequency7Count(tmpGather.getFrequency7Count() + gather.getFrequency7Count());
				gather.setFrequency8Count(tmpGather.getFrequency8Count() + gather.getFrequency8Count());
				gather.setFrequency9Count(tmpGather.getFrequency9Count() + gather.getFrequency9Count());
				gather.setFrequency10Count(tmpGather.getFrequency10Count() + gather.getFrequency10Count());
				gather.setFrequency11Count(tmpGather.getFrequency11Count() + gather.getFrequency11Count());
				gather.setFrequency12Count(tmpGather.getFrequency12Count() + gather.getFrequency12Count());
				gather.setFrequency13Count(tmpGather.getFrequency13Count() + gather.getFrequency13Count());
				gather.setFrequency14Count(tmpGather.getFrequency14Count() + gather.getFrequency14Count());
				gather.setFrequency15Count(tmpGather.getFrequency15Count() + gather.getFrequency15Count());
				gather.setFrequency16Count(tmpGather.getFrequency16Count() + gather.getFrequency16Count());
				gather.setFrequency17Count(tmpGather.getFrequency17Count() + gather.getFrequency17Count());
				gather.setFrequency18Count(tmpGather.getFrequency18Count() + gather.getFrequency18Count());
				gather.setFrequency19Count(tmpGather.getFrequency19Count() + gather.getFrequency19Count());
				gather.setFrequency20Count(tmpGather.getFrequency20Count() + gather.getFrequency20Count());
			}
			
			output.set(gather.toString());
			context.write(NullWritable.get(), output);
		}
	}
}
