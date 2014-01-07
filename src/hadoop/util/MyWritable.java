package hadoop.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class MyWritable implements WritableComparable<MyWritable>{
	private Text visitorId;
	private LongWritable pv;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyWritable myWritable = new MyWritable("penglin",123L);
		MyWritable myWritable2 = new MyWritable("penglin",123L);
		boolean flag = myWritable.equals(myWritable2);
		System.out.println(flag);
		System.out.println(myWritable.toString());
	}

	public Text getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(Text visitorId) {
		this.visitorId = visitorId;
	}

	public LongWritable getPv() {
		return pv;
	}

	public void setPv(LongWritable pv) {
		this.pv = pv;
	}

	public MyWritable(Text visitorId, LongWritable pv) {
		this.setVisitorId(visitorId);
		this.setPv(pv);
	}

	public MyWritable() {
		this.setVisitorId(new Text());
		this.setPv(new LongWritable());
	}
	
	public MyWritable(String visitorId, long pv) {
		this.setVisitorId(new Text(visitorId));
		this.setPv(new LongWritable(pv));
	}
	
	@Override
	public void readFields(DataInput input) throws IOException {
		visitorId.readFields(input);
		pv.readFields(input);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		visitorId.write(out);
		pv.write(out);
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof MyWritable))
			return false;
		MyWritable tmp = (MyWritable) obj;
		if(!tmp.getVisitorId().equals(this.visitorId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.visitorId.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.visitorId +"\t"+ this.pv.get();
	}

	@Override
	public int compareTo(MyWritable tmp) {
		int result = this.visitorId.compareTo(tmp.getVisitorId());
		if(result!=0)
			return result;
		
		return this.pv.compareTo(tmp.getPv());
	}
}
