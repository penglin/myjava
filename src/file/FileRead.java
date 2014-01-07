package file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


import net.sf.json.JSONObject;

public class FileRead {
	private static final String Split_String = new String(new char[]{1});
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		fileread("C:\\Documents and Settings\\≈Ì¡ÿ\\◊¿√Ê\\play.txt");
	}

	public static void fileread(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf8"));
		String line = null;
		String demandId = null;
		String adPosId = null;
		String publishId = null;
		Map<String,Long> map = new HashMap<String, Long>();
		while ((line = br.readLine()) != null) {
			JSONObject jsonObject = JSONObject.fromObject(line);
			demandId = jsonObject.getString("Demand_Id");
			adPosId = jsonObject.getString("Ad_Pos_Id");
			publishId = jsonObject.getString("Publish_Id");
			String key = (demandId + Split_String + adPosId + Split_String + publishId).toLowerCase();
			if(map.containsKey(key)){
				map.put(key, map.get(key)+ 1);
			}else{
				map.put(key, 1L);
			}
		}

		for(String key : map.keySet()){
			if(map.get(key)>1){
				System.out.println(key +"-->"+ map.get(key));
			}
		}
	}

}
