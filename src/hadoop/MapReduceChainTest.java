package hadoop;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapReduceChainTest extends Configured implements Tool{
	private static final Log log = LogFactory.getLog(MapReduceChainTest.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		try {
			int res = ToolRunner.run(new Configuration(), new MapReduceChainTest(), args);
			System.exit(res);
		} catch (Exception e) {
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JobConf conf = new JobConf(getConf(),MapReduceChainTest.class);
		
		JobConf mapAConf = new JobConf(false);
	    mapAConf.set("a", "A");
	    ChainMapper.addMapper(conf, AMap.class, LongWritable.class, Text.class,
	                          LongWritable.class, Text.class, true, mapAConf);

	    ChainMapper.addMapper(conf, BMap.class, LongWritable.class, Text.class,
	                          LongWritable.class, Text.class, false, null);

	    JobConf reduceConf = new JobConf(false);
	    reduceConf.set("a", "C");
	    ChainReducer.setReducer(conf, CReduce.class, LongWritable.class, Text.class,
	                            LongWritable.class, Text.class, true, reduceConf);

	    ChainReducer.addMapper(conf, DMap.class, LongWritable.class, Text.class,
	                           LongWritable.class, Text.class, false, null);

	    JobConf mapEConf = new JobConf(false);
	    mapEConf.set("a", "E");
	    ChainReducer.addMapper(conf, EMap.class, LongWritable.class, Text.class,
	                           LongWritable.class, Text.class, true, mapEConf);

	    FileInputFormat.setInputPaths(conf, "/penglin/chainInput");
	    FileOutputFormat.setOutputPath(conf, new Path("/penglin/chainOutput"));

	    JobClient jc = new JobClient(conf);
	    jc.submitJob(conf);
		return 0;
	}
	

	  public static class AMap extends IDMap {
	    public AMap() {
	      super("A", "AMap", true);
	    }
	  }

	  public static class BMap extends IDMap {
	    public BMap() {
	      super("B", "BMap", false);
	    }
	  }

	  public static class CReduce extends IDReduce {
	    public CReduce() {
	      super("C", "CReduce");
	    }
	  }
	  
	  public static class CReduce2 extends IDReduce {
		    public CReduce2() {
		      super("C2", "CReduce2");
		    }
		  }
		  

	  public static class DMap extends IDMap {
	    public DMap() {
	      super("D", "DMap", false);
	    }
	  }

	  public static class EMap extends IDMap {
	    public EMap() {
	      super("E", "EMap", true);
	    }
	  }

	  public static class IDMap
	    implements Mapper<LongWritable, Text, LongWritable, Text> {
	    private JobConf conf;
	    private String name;
	    private String prop;
	    private boolean byValue;

	    public IDMap(String name, String prop, boolean byValue) {
	      this.name = name;
	      this.prop = prop;
	      this.byValue = byValue;
	    }

	    public void configure(JobConf conf) {
	      this.conf = conf;
	    }

	    public void map(LongWritable key, Text value,
	                    OutputCollector<LongWritable, Text> output,
	                    Reporter reporter) throws IOException {
	      key.set(10);
	      output.collect(key, value);
	      log.info("prop:"+prop);
	    }

	    public void close() throws IOException {
	    }
	  }

	  public static class IDReduce
	    implements Reducer<LongWritable, Text, LongWritable, Text> {

	    private JobConf conf;
	    private String name;
	    private String prop;
	    private boolean byValue = false;

	    public IDReduce(String name, String prop) {
	      this.name = name;
	      this.prop = prop;
	    }

	    public void configure(JobConf conf) {
	      this.conf = conf;
	    }

	    public void reduce(LongWritable key, Iterator<Text> values,
	                       OutputCollector<LongWritable, Text> output,
	                       Reporter reporter) throws IOException {
	      while (values.hasNext()) {
	        Text value = values.next();
	        key.set(10);
	        output.collect(key, value);
	      }
	      log.info("prop:"+prop);
	    }

	    public void close() throws IOException {}
	  }

}
