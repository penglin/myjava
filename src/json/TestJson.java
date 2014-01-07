package json;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


import security.Base64Util;
import security.Crypto;

public class TestJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONArray ja = new JSONArray();
		ja.add(0, 123);
//		ja.add(0,new JSONObject().put("age", 23));
		ja.add(1, "lin");
		ja.add(2, "lin870521");
		/*List l = new ArrayList();
		l.add("fa");
		l.add(32);*/
		System.out.println(ja.toString());
		
		JSONObject jo = new JSONObject();
		jo.put("name", "lin");
		jo.put("age", 11);
//		jo.put("json", ja);
		List list = new ArrayList();
		list.add("dfjslk");
		list.add(3829);
//		jo.put("collection", list);
//		jo.accumulate("address", "beijing");
		
		System.out.println("---"+jo.toString());
		System.out.println(jo.get("age"));
		
		JSONObject jo2 = new JSONObject();
		System.out.println(jo2.toString());
		System.out.println(jo2.get("age"));
		
		try {
			byte[] keybyte = Crypto.generateDESedeKey();
			System.out.println(keybyte.toString());
			
			String cipher = Base64Util.encode(Crypto.encryptDESede(keybyte,Crypto.decryptDESede(keybyte,Crypto.encryptDESede(keybyte, jo.toString().getBytes()))));
			System.out.println(cipher);
			
			String plaintext = new String(Crypto.decryptDESede(keybyte,Crypto.encryptDESede(keybyte, Crypto.decryptDESede(keybyte, Base64Util.decode(cipher)))));
			System.out.println(plaintext);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonConfig jc = new JsonConfig();
		Map map = jc.getClassMap();
		System.out.println(map);
		
		JSONObject.fromObject("fas");
		
	}

}
