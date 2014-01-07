package test;

import java.util.Arrays;
import java.util.Comparator;

import net.sf.json.JSONObject;

public class BananaBread {
	public static void main(String[] args) {
		Integer[] array = { 3, 1, 4, 1, 5, 9 };
		System.out.println(array[0]>array[1]);
		Arrays.sort(array, new Comparator<Integer>() {
			public int compare(Integer i1, Integer i2) {
				return i1 < i2 ? -1 : (i2 > i1 ? 1 : 0);
			}
		});
		
		Arrays.sort(array);
		System.out.println(Arrays.toString(array));
		
		System.out.println((7 + 2)>>>1);
		System.out.println(9/2);
		
		
		String tt = "fdsfd";
		System.out.println(tt);
		testString(tt);
		System.out.println(tt);
		
		String jsonString = "{\"gatherDate\":\"2012年03月19日\",\"campaignId\":\"aaaaaaaaaaaaaaa_visitor_id_0\",\"clickCount\":0,\"clickUniqueCount\":0,\"channelId\":\"\",\"demandCount\":1,\"demandIpCount\":0,\"zoneCity\":\"白银市\",\"adPosId\":\"workstation_id_workstation_id0ad_pos\",\"campaignTrackId\":\"aaaaaaaaaaaaaaa_visitor_id_01\",\"demandUvCount\":0,\"gatherHour\":\"21\",\"clickUvCount\":0,\"demandUniqueCount\":1,\"clickIpCount\":0,\"zoneProvince\":\"甘肃省\",\"adPosZoneSegmentGatherId\":\"\",\"workstationId\":\"workstation_id_workstation_id0\"}";
		JSONObject json = JSONObject.fromObject(jsonString);
		System.out.println(json.getString("gatherHour"));
		
		String filterRule = "2012年03月19日 10:03:12".substring(0,17);
		System.out.println(filterRule);
	}
	
	private static void testBoolean (Boolean flag){
		if(flag.booleanValue()){
			flag = new Boolean(false);
			System.out.println("come in");
		}
	}
	private static void testString (String tt){
		tt = new String("fdsfdfdsfds");
	}
}