package hadoop.report;

import file.pojo.MyVideoPlay;
import hadoop.report.pojo.AdPosFrequencyGather;
import hadoop.report.pojo.AdPosZoneSegmentGather;

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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
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

public class JingZhunPaiReport extends Configured implements Tool {
	private static final Log log = LogFactory.getLog(JingZhunPaiReport.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("v0.1");
			log.info("JingZhunPaiReport start");
			int res = ToolRunner.run(new Configuration(), new JingZhunPaiReport(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(JingZhunPaiReport.class);
		job.setJobName("Save_In_Reduce");
		
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setMapperClass(ReportMap.class);
		job.setCombinerClass(ReportCombiner.class);
		job.setReducerClass(ReportReduce.class);
		job.setNumReduceTasks(18);
		
		job.setInputFormatClass(TextInputFormat.class);
//		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileSystem fs = FileSystem.newInstance(getConf());
		Path path = new Path("/penglin/reportStatistics");
		if(fs.exists(path)){
			fs.delete(path, true);
		}
		Path[] paths = new Path[]{new Path("/penglin/report2/201203211229"),
									new Path("/penglin/report2/201203211314"),
									new Path("/penglin/report2/201203211358"),
									new Path("/penglin/report2/201203211359"),
									new Path("/penglin/report2/201203211443"),
									new Path("/penglin/report2/201203211444")};
		FileInputFormat.setInputPaths(job, paths);
		FileOutputFormat.setOutputPath(job, path);
		
		MultipleOutputs.addNamedOutput(job, "AdPosZoneSegment", TextOutputFormat.class,
				 NullWritable.class, Text.class);
		
		MultipleOutputs.addNamedOutput(job, "AdPosZoneFrequency", TextOutputFormat.class,
				 NullWritable.class, Text.class);
		
		boolean success = job.waitForCompletion(true);
		
//		boolean success = true;
		/*if(success){
			success = runAdPosZoneSegmentReportJob();
		}*/
		
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
		FileSystem fs = FileSystem.newInstance(conf);
		String inputPath = "/penglin/reportStatistics";
		String filterName = "AdPosZoneSegment";
		String outputPath = "/penglin/segment";
		
		String[] dimensions = new String[]{"campaignId","campaignTrackId","workstationId","adPosId","zoneProvince","zoneCity","gatherDate","gatherHour"};
		job.getConfiguration().setStrings("dimensions", dimensions);
		
		return runReportJob(fs, job, inputPath, filterName, outputPath);
	}
	
	private boolean runAdPosZoneFrequencyReportJob() throws IOException, InterruptedException, ClassNotFoundException{
		Job job = initReportJob("Statistics_Zone_Frequency_Report", NullWritable.class, Text.class, Text.class,
				Text.class, ReportStatisticsMap.class, ReportCombiner.class, AdPosZoneFrequencyReportStatisticsReduce.class, 18, 
				TextInputFormat.class, TextOutputFormat.class);
		
		FileSystem fs = FileSystem.newInstance(getConf());
		String[] dimensions = new String[]{"campaignId","campaignTrackId","workstationId","adPosId","zoneProvince","zoneCity","gatherDate"};
		String inputPath = "/penglin/reportStatistics";
		String filterName = "AdPosZoneFrequency";
		String outputPath = "/penglin/frequency";
		
		job.getConfiguration().setStrings("dimensions", dimensions);
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
		statisticJob.setJarByClass(JingZhunPaiReport.class);
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

	public static class ReportReduce extends Reducer<Text, Text, Text, Text> {
		private static Map<String, AdPosZoneSegmentGather> adPosZoneSegmentMap = null;
		private static Map<String, AdPosFrequencyGather> adPosZoneFrequencyMap = null;
		private final static String[] adPosZoneSegmentKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay","videoDemandHour"};
		private final static String[] adPosZoneFrequencyKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay"};
//		private final static String[] adPosZoneFrequencyKeys = new String[]{"orderId","workstationId","videoDemandDay"};
		
		private MyVideoPlay play = null;
		private JSONObject jsonObject = null;
		
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
				long tmpTime = System.currentTimeMillis();
				sb.append(map.get(key));
				sb.append("\n");
//				context.write(new Text(outPutKey), new Text(map.get(key).toString()));
			
				if(System.currentTimeMillis() - currTime >5*1000){
					log.info("write out cost time:"+(System.currentTimeMillis() - tmpTime));
					currTime = System.currentTimeMillis();
				}
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
			AdPosZoneSegmentGather gather = adPosZoneSegmentMap.get(mapKey);
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
	
	public static void setValueByPropertyName(Object obj,String propertyName,Class[] classes,Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method methode = obj.getClass().getDeclaredMethod(propertyName, classes);
		methode.invoke(obj, args);
	}
	
	public static class ReportStatisticsMap extends Mapper<LongWritable, Text, Text, Text>{
		private static String[] dimensions = null;
		private JSONObject jsonObject ;
		private Text outputKey = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			if(value==null || value.toString().trim().isEmpty())
				return ;
			jsonObject = JSONObject.fromObject(value.toString());
			String mapKey = getVideoPlayMapKeyUseJSON(jsonObject, dimensions);
			outputKey.set(mapKey);
			context.write(outputKey, value);
		}

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			super.setup(context);
			dimensions = context.getConfiguration().getStrings("dimensions");
		}
		
	}
	
	public static class AdPosZoneSegmentReportStatisticsReduce extends Reducer<Text, Text, NullWritable, Text>{
		private JSONObject jsonObject = null;
		private AdPosZoneSegmentGather gather = null;
		private Text output = new Text();
		
		private static long currTime = System.currentTimeMillis(); 
		
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
			for(Text value : values){
				if(value==null||value.toString().trim().isEmpty())
					continue;
				jsonObject = JSONObject.fromObject(value.toString());
				long demandCount = jsonObject.getLong("demandCount");
				long demandUniqueCount = jsonObject.getLong("demandUniqueCount");
				gather.setDemandCount(gather.getDemandCount() + demandCount);
				gather.setDemandUniqueCount(gather.getDemandUniqueCount() + demandUniqueCount);
				j++;
			}
			
			if(System.currentTimeMillis() - currTime >10*1000){
				log.info("key:"+key.toString()+",Size:"+ j);
				currTime = System.currentTimeMillis();
			}
			output.set(gather.toString());
			context.write(NullWritable.get(), output);
		}
	}
	
	public static class AdPosZoneFrequencyReportStatisticsReduce extends Reducer<Text, Text, NullWritable, Text>{
		private JSONObject jsonObject = null;
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
			
			for(Text value : values){
				if(value==null||value.toString().trim().isEmpty())
					continue;
				jsonObject = JSONObject.fromObject(value.toString());
				long frequency1Count = jsonObject.getLong("frequency1Count");
				gather.setFrequency1Count(frequency1Count + gather.getFrequency1Count());
				long frequency2Count = jsonObject.getLong("frequency2Count");
				gather.setFrequency1Count(frequency2Count + gather.getFrequency2Count());
				long frequency3Count = jsonObject.getLong("frequency3Count");
				gather.setFrequency1Count(frequency3Count + gather.getFrequency3Count());
				long frequency4Count = jsonObject.getLong("frequency4Count");
				gather.setFrequency1Count(frequency4Count + gather.getFrequency4Count());
				long frequency5Count = jsonObject.getLong("frequency5Count");
				gather.setFrequency1Count(frequency5Count + gather.getFrequency5Count());
				long frequency6Count = jsonObject.getLong("frequency6Count");
				gather.setFrequency1Count(frequency6Count + gather.getFrequency6Count());
				long frequency7Count = jsonObject.getLong("frequency7Count");
				gather.setFrequency1Count(frequency7Count + gather.getFrequency7Count());
				long frequency8Count = jsonObject.getLong("frequency8Count");
				gather.setFrequency1Count(frequency8Count + gather.getFrequency8Count());
				long frequency9Count = jsonObject.getLong("frequency9Count");
				gather.setFrequency1Count(frequency9Count + gather.getFrequency9Count());
				long frequency10Count = jsonObject.getLong("frequency10Count");
				gather.setFrequency1Count(frequency10Count + gather.getFrequency10Count());
				long frequency11Count = jsonObject.getLong("frequency11Count");
				gather.setFrequency1Count(frequency11Count + gather.getFrequency11Count());
				long frequency12Count = jsonObject.getLong("frequency12Count");
				gather.setFrequency1Count(frequency12Count + gather.getFrequency12Count());
				long frequency13Count = jsonObject.getLong("frequency13Count");
				gather.setFrequency1Count(frequency13Count + gather.getFrequency13Count());
				long frequency14Count = jsonObject.getLong("frequency14Count");
				gather.setFrequency1Count(frequency14Count + gather.getFrequency14Count());
				long frequency15Count = jsonObject.getLong("frequency15Count");
				gather.setFrequency1Count(frequency15Count + gather.getFrequency15Count());
				long frequency16Count = jsonObject.getLong("frequency16Count");
				gather.setFrequency1Count(frequency16Count + gather.getFrequency16Count());
				long frequency17Count = jsonObject.getLong("frequency17Count");
				gather.setFrequency1Count(frequency17Count + gather.getFrequency17Count());
				long frequency18Count = jsonObject.getLong("frequency18Count");
				gather.setFrequency1Count(frequency18Count + gather.getFrequency18Count());
				long frequency19Count = jsonObject.getLong("frequency19Count");
				gather.setFrequency1Count(frequency19Count + gather.getFrequency19Count());
				long frequency20Count = jsonObject.getLong("frequency20Count");
				gather.setFrequency1Count(frequency20Count + gather.getFrequency20Count());
			}
			
			output.set(gather.toString());
			context.write(NullWritable.get(), output);
		}
	}
}
