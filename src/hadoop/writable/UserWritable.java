package hadoop.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UserWritable implements WritableComparable<UserWritable> {
	private String name;
	private Long age;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	public UserWritable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWritable(String name, Long age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		System.out.println("write");
		out.writeUTF(name);
//		out.writeBytes(name);
		out.writeLong(age);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		System.out.println("read");
		name = in.readUTF();
		age = in.readLong();
	}

	@Override
	public int compareTo(UserWritable user) {
		return user.getName().compareTo(this.getName())!=0?(user.getAge().compareTo(getAge())):user.getName().compareTo(this.getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode() + age.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserWritable){
			UserWritable user = (UserWritable) obj;
			
			return user.getName().equals(getName())?user.getAge().longValue()==getAge():false;
		}
		return false;
	}

}
