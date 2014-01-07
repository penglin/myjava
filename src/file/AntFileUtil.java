package file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class AntFileUtil {
	public static void main(String[] args) throws IOException {
		String sql = "insert into Task_Url (Task_Url_Id,Task_Id,Task_Url_Name,Task_Url) values ('id','14','name','url');";
		Map<String,String> nameAndUrl = getWebNameAndWebUrl("ant.txt");
		Set<Entry<String, String>> tmp = nameAndUrl.entrySet();
		Iterator<Entry<String, String>> it = tmp.iterator();
		int i = 452;
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String name = entry.getKey();
			String url = entry.getValue();
			String sql_ = sql.replace("name", name);
			sql_ = sql_.replace("id", (i++)+"");
			sql_ = sql_.replace("url", url);
			System.out.println(sql_);
//			System.out.println("delete from Task_Url where Task_Url_Id='"+(i++)+"'");
		}
	}
	
	public static Map<String,String> getWebNameAndWebUrl(String file) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		Map<String,String> nameAndUrl = new HashMap<String, String>();
		while((line=br.readLine())!=null){
			String[] values = line.split(",");
			nameAndUrl.put(values[0], values[1]);
		}
		return nameAndUrl;
	}
}
