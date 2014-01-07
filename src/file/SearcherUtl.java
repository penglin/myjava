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

public class SearcherUtl {
	public static void main(String[] args) throws IOException {
		Map<String,String> nameAndUrl = getWebNameAndWebUrl("ant.txt");
		
		Map<String,String> needSearchNameAndUrl = getWebNameAndWebUrl("d.txt");
		Set<Entry<String, String>> tmp = needSearchNameAndUrl.entrySet();
		Iterator<Entry<String, String>> it = tmp.iterator();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String url = entry.getKey();
			String name = entry.getValue();
			String[] arr = url.replace("http://", "").replaceAll("www", "").split("\\.");
			
			Set<Entry<String, String>> tmp2 = nameAndUrl.entrySet();
			Iterator<Entry<String, String>> it2 = tmp2.iterator();
			while(it2.hasNext()){
				Entry<String, String> entry2 = it2.next();
				String name2 = entry2.getKey();
				String url2 = entry2.getValue().replaceAll("http://", "").replaceAll("www", "");
				String[] values = url2.split("\\.");
				int length = values.length;
				for(int i=length;i>0;i--){
					String newUul = "";
					newUul += values[i-1];
					if(newUul.equalsIgnoreCase("cn")||newUul.equalsIgnoreCase("net")||newUul.equalsIgnoreCase("tv")||newUul.equalsIgnoreCase("com")){
						continue;
					}
					for(int j=arr.length;j>0;j--){
//						System.out.println(arr[j-1]);
						if(arr[j-1].toLowerCase().equalsIgnoreCase(newUul.toLowerCase())){
							name = name2;
//							System.out.println(arr[j-1].toLowerCase()+"-----"+(newUul.toLowerCase()));
							break;
						}
					}
					if(name.trim().length()>0)
						break;
				}
				if(name.trim().length()>0)
					break;
			}
			System.out.println(name+"\t"+url);
//			System.out.println("delete from Task_Url where Task_Url_Id='"+(i++)+"'");
		}
	}
	
	public static Map<String,String> getWebNameAndWebUrl(String file) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		Map<String,String> nameAndUrl = new HashMap<String, String>();
		while((line=br.readLine())!=null){
			String[] values = line.split(",");
			if(values.length>1){
				nameAndUrl.put(values[0], values[1]);
			}else{
				nameAndUrl.put(values[0], "");
			}
		}
		return nameAndUrl;
	}
}
