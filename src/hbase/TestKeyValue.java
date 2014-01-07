package hbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.KeyValue.Type;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class TestKeyValue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String family = "mp";
		List<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("d"), System.currentTimeMillis(), Type.Put,Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("o"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("a"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("u"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("h"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("b"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		list.add(new KeyValue(Bytes.toBytes("click"), Bytes.toBytes(family), Bytes.toBytes("i"), System.currentTimeMillis(), Type.Put, Bytes.toBytes("date")));
		Collections.sort(list, KeyValue.COMPARATOR);
		
		Result result = new Result(list);
		KeyValue[] kvs = result.raw();
		System.out.println(new String(result.getRow()));
		
		KeyValue kv = result.getColumnLatest(Bytes.toBytes(family), Bytes.toBytes("i"));
		System.out.println(kv);
		System.out.println(kv.heapSize());
	}

	public static KeyValue getColumnLatest(Result result, byte[] family, byte[] qualifier) {
		KeyValue[] kvs = result.raw(); // side effect possibly.
		if (kvs == null || kvs.length == 0) {
			return null;
		}
		int pos = binarySearch(kvs, family, qualifier);
		if (pos == -1) {
			return null;
		}
		KeyValue kv = kvs[pos];
		if (kv.matchingColumn(family, qualifier)) {
			return kv;
		}
		return null;
	}

	protected static int binarySearch(final KeyValue[] kvs, final byte[] family, final byte[] qualifier) {
		KeyValue searchTerm = KeyValue.createFirstOnRow(kvs[0].getRow(), family, qualifier);

		// pos === ( -(insertion point) - 1)
		int pos = Arrays.binarySearch(kvs, searchTerm, KeyValue.COMPARATOR);
		// never will exact match
		if (pos < 0) {
			pos = (pos + 1) * -1;
			// pos is now insertion point
		}
		if (pos == kvs.length) {
			return -1; // doesn't exist
		}
		return pos;
	}
}
