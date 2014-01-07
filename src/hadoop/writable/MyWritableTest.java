package hadoop.writable;

import hadoop.util.MyWritable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class MyWritableTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MyWritable myWritable = new MyWritable("penglin",123L);
		byte[] bytes = serialize(myWritable);
		System.out.println(new String(deserialize(myWritable, bytes),"GBK"));;
		
		
		UserWritable user = new UserWritable("penglin", 7L);
		UserWritable user1 = new UserWritable("penglin", 7L);
		System.out.println(user.equals(user1));
		
		byte[] userBytes = serialize(user);
		UserWritable newUser = new UserWritable();
		newUser.setName("daye");
		deserialize(newUser, userBytes);
		System.out.println(newUser.getName()+","+newUser.getAge());
	}

	public static byte[] serialize(Writable writable) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		writable.write(dataOut);
		dataOut.close();
		return out.toByteArray();
	}
	
	public static byte[] deserialize(Writable writable,byte[] bytes) throws IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream dataIn = new DataInputStream(in);
		writable.readFields(dataIn);
		dataIn.close();
		return bytes;
	}
}
