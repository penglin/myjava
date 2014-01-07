package hbase;

import java.net.URI;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.regionserver.HRegion;
import org.apache.hadoop.hbase.regionserver.KeyValueScanner;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MetaUtils;
import org.apache.hadoop.hbase.util.Writables;

@SuppressWarnings("unused")
public class HDFSFileAddToRegion {

	static boolean addToMeta = true;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("param1 is the hdfs path,param2 is addTo Meta table default=true,can set false");
		if (args.length == 2) {
			if (args[1].equals("false"))
				addToMeta = false;
		}
		if (args.length >= 1) {
			FileSystem fs = FileSystem.get(URI.create("hdfs://a6-5:9000/"),Conf.getConfiguration());
			String line;
			MetaUtils mu = null;
			try {
				FSDataInputStream fin = fs.open(new Path("/hbase/user_behavior/fb4946077422af8429a6a4e8f7df448e/.regioninfo"));
				byte[] b = new byte[fin.available()];
				fin.read(b);
				fin.close();

				HRegionInfo hri = Writables.getHRegionInfo(b);
				// —È÷§region¥Ê‘⁄
				mu = new MetaUtils(Conf.getConfiguration());//
//				HRegion hr = mu.getRegionFromInfo(hri);
//				hr.compactStores();
				
//				Scan scan = new Scan();
//				TreeSet<byte[]> ns = new TreeSet<byte[]>();
//				ns.add(Bytes.toBytes("uid1"));
//				Store store = hr.getStore("baseInfo".getBytes());
//				System.out.println("first kv ");

				if (addToMeta) {
					Put put = new Put(hri.getRegionName());
					System.out.println(hri.toString());
					put.add(HConstants.CATALOG_FAMILY, HConstants.REGIONINFO_QUALIFIER, b);
					HTable me = new HTable(Conf.getConfiguration(), ".META.");
					me.put(put);
					me.flushCommits();
					me.close();
					System.out.println("save to meta.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (new Path(args[0]).getParent().getParent().equals(new Path("/hbase"))) {
//					fs.delete(new Path(args[0]), true);
					System.out.println("delete bad file " + args[0]);
				}
			} finally {
				mu.shutdown();
			}
			//			HBaseAdmin admin = new HBaseAdmin(Conf.getConfiguration());
			//			admin.flush(".META.");
			//			Thread.sleep(1 * 1000);
			//			admin.majorCompact(".META.");
		}
		System.exit(0);
	}

}
