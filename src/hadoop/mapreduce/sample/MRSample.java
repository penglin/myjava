package hadoop.mapreduce.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MRSample extends Configured implements Tool {
	final static String SPLIT_CHAR = "\t"; 
	
	final static int TRACK_TYPE_IMP = 1;
	final static int TRACK_TYPE_CLICK = 2;
	
	final static int INDEX_CAMPAIGN_ID = 0;
	final static int INDEX_USER_ID = 4;
	final static int INDEX_TRACK_TYPE = 5;
	
	@Override
	public int run(String[] args) throws Exception {
		Job job = new Job();
		job.setJobName("MRSample");
		job.setJarByClass(MRSample.class);
		
		job.setMapperClass(SampleMapper.class);
		job.setReducerClass(SampleReducer.class);
		
		job.setNumReduceTasks(1);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		//set input path
//		FileInputFormat.setInputPaths(job, new Path[]{});//设置方式1
		FileInputFormat.addInputPath(job, new Path("/penglin/test/input/hadoop.txt"));//设置方式2
		
		//set output path
		Path out = new Path("/penglin/test/output");
		FileSystem fs = FileSystem.get(getConf());
		if(fs.exists(out)){
			fs.delete(out, true);
		}
		FileOutputFormat.setOutputPath(job, out);
		
		//other job setter
//		job.setCombinerClass(cls)
//		job.setGroupingComparatorClass(cls)
//		job.setSortComparatorClass(cls)
//		job.setPartitionerClass(cls)
		
		return job.waitForCompletion(true)==true?0:1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ToolRunner.run(new MRSample(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	static class SampleMapper extends Mapper<LongWritable,Text,Text,Text>{
		private Text reduceKey = new Text();
		private String tmpValue = null;
		private String[] splits; 
		private String userId = null;
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			//value 是以\t分隔的字符串 格式:campaign_id\tcampaign_track_id\tworkstation_id\tad_pos_id\tuser_id\ttrack_type
			tmpValue = value.toString();
			if(tmpValue.isEmpty())
				return ;
			
			splits = tmpValue.split(SPLIT_CHAR);
			userId = splits[INDEX_USER_ID];
			
			reduceKey.set(userId);
			context.write(reduceKey, value);
		}
		
	}
	
	static class SampleReducer extends Reducer<Text, Text, NullWritable, Text>{
		private String tmpValue = null;
		private String[] splits; 
		private int trackType = 0;
		private Text output = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			for(Text value : values){
				tmpValue = value.toString();
				splits = tmpValue.split(SPLIT_CHAR);
				campaignTrackGather(splits);
			}
			
			//输出
			for(CampaignTrackGather tmp : campaignTrackerGatherMap.values()){
				output.set(tmp.toString());
				context.write(NullWritable.get(), output);
			}
		}
		
		private Map<String,CampaignTrackGather> campaignTrackerGatherMap = new HashMap<String, CampaignTrackGather>();
		private String campaignId = null;
		//统计基于活动的报表数据
		protected void campaignTrackGather(String[] splits) {
			campaignId = splits[INDEX_CAMPAIGN_ID];
			trackType = Integer.valueOf(splits[INDEX_TRACK_TYPE]);
			
			CampaignTrackGather camapaignTrackGather = campaignTrackerGatherMap.get(campaignId);
			if(camapaignTrackGather==null){
				camapaignTrackGather = new CampaignTrackGather(campaignId);
				campaignTrackerGatherMap.put(campaignId, camapaignTrackGather);
			}
			
			if(trackType==TRACK_TYPE_IMP){
				camapaignTrackGather.setImp(camapaignTrackGather.getImp()+1);
				camapaignTrackGather.setImpUnique(1);
			}else{
				camapaignTrackGather.setClick(camapaignTrackGather.getClick()+1);
				camapaignTrackGather.setClickUnique(1);
			}
		}
		
	}
	
	static class CampaignTrackGather {
		private String campaignId;
		private Integer imp;
		private Integer impUnique;
		private Integer click;
		private Integer clickUnique;
		
		public CampaignTrackGather(String campaignId) {
			this.campaignId = campaignId;
		}

		public CampaignTrackGather(String campaignId, Integer imp, Integer impUnique, Integer click, Integer clickUnique) {
			super();
			this.campaignId = campaignId;
			this.imp = imp;
			this.impUnique = impUnique;
			this.click = click;
			this.clickUnique = clickUnique;
		}
		
		@Override
		public int hashCode() {
			return campaignId.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if(obj==null || !(obj instanceof CampaignTrackGather))
				return false;
			return campaignId.equals(((CampaignTrackGather)obj).getCampaignId());
		}
		
		@Override
		public String toString() {
			return campaignId+SPLIT_CHAR+this.getImp()+SPLIT_CHAR+this.getImpUnique()+SPLIT_CHAR+this.getClick()+SPLIT_CHAR+this.getClickUnique();
		}

		public String getCampaignId() {
			return campaignId;
		}
		public void setCampaignId(String campaignId) {
			this.campaignId = campaignId;
		}
		public Integer getImp() {
			return imp==null?0:imp;
		}
		public void setImp(Integer imp) {
			this.imp = imp;
		}
		public Integer getImpUnique() {
			return impUnique==null?0:impUnique;
		}
		public void setImpUnique(Integer impUnique) {
			this.impUnique = impUnique;
		}
		public Integer getClick() {
			return click==null?0:click;
		}
		public void setClick(Integer click) {
			this.click = click;
		}
		public Integer getClickUnique() {
			return clickUnique==null?0:clickUnique;
		}
		public void setClickUnique(Integer clickUnique) {
			this.clickUnique = clickUnique;
		}
		
	}
}
