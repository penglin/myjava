package hadoop.mapreduce.secondsort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class TextPair2 implements Writable {
	private Text userId;
	private Text userIp;
	
	public TextPair2() {
		set(new Text(),new Text());
	}
	
	public TextPair2(String userId, String userIp) {
		set(new Text(userId),new Text(userIp));
	}
	
	public TextPair2(Text userId, Text userIp) {
		set(userId,userIp);
	}

	public void set(Text userId,Text userIp){
		this.userId = userId;
		this.userIp = userIp;
	}
	
	public void set(String userId, String userIp){
		this.userId = new Text(userId);
		this.userIp = new Text(userIp);
	}

	public Text getUserId() {
		return userId;
	}

	public void setUserId(Text userId) {
		this.userId = userId;
	}

	public Text getUserIp() {
		return userIp;
	}

	public void setUserIp(Text userIp) {
		this.userIp = userIp;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		userId.readFields(input);
		userIp.readFields(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		userId.write(output);
		userIp.write(output);
	}

	/*public int compareTo(TextPair o) {
		int compare = this.userId.compareTo(o.getUserId());
		if(compare!=0)
			return compare;
		return this.userIp.compareTo(o.getUserIp());
	}*/

	@Override
	public int hashCode() {
		return userId.hashCode()*157 + userIp.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof TextPair2))
			return false;
		TextPair2 tmp = (TextPair2) obj;
		if(!this.userId.equals(tmp.getUserId()))
			return false;
		if(!this.userIp.equals(tmp.getUserIp()))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return this.userId.toString() +"\t"+ this.userIp.toString();
	}
}
