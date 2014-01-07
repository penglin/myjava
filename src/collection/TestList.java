package collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

public class TestList {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < 104; i++) {
			strings.add("" + i);
		}

		List<String> subString = strings.subList(0, 100);
		subString.add(null);

		strings = strings.subList(100, strings.size());
		for (String string : strings) {
			System.out.println(string);
		}
		String userId = "sinajfkdsjfldk";
		userId = userId.replaceFirst("sina", "");
		System.out.println(userId);

		Map<String, String> map = new HashMap<String, String>();
		map.get(null);
		testddd();
		dddd();
		testChaunzhi();

	}

	@Test
	public void testMapList() {
		Map<String, List<String>> maplist = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("dd");
		list.add("dd");
		list.add("dd");
		maplist.put("list1", list);

		List<String> list2 = new ArrayList<String>();
		list2.add("dd");
		list2.add("dd");
		list2.add("dd");
		maplist.put("list2", list2);

		for (String key : maplist.keySet()) {
			List tmpList = maplist.get(key);
			// maplist.remove(key);
		}
	}

	public static void testChaunzhi() {
		List<JSONObject> weiboIds = new ArrayList<JSONObject>();
		JSONObject clickJson = new JSONObject();
		clickJson.put("Demand_Stamp", System.currentTimeMillis());
		clickJson.put("Demand_Date", "ddddd");
		weiboIds.add(clickJson);

		JSONObject tmp = weiboIds.iterator().next();
		tmp.put("Demand_Date", "ddd2222dd");

		System.out.println(clickJson);
	}

	public static void testddd() {
		List<String> weiboIds = new ArrayList<String>();
		for (int i = 0; i < 208; i++) {
			weiboIds.add("" + i);
		}

		if (weiboIds.size() > 100) {
			while (weiboIds.size() > 100) {
				List<String> subWeiboIds = weiboIds.subList(0, 100);
				weiboIds = weiboIds.subList(100, weiboIds.size());
				System.out.println("---");
			}
		}
	}

	public static void dddd() {
		List<String> keywords = new ArrayList<String>();
		// 垃圾、广告、讨厌凡客、真2、真二、水军
		keywords.add("垃圾");
		keywords.add("广告");
		keywords.add("讨厌凡客");
		keywords.add("真2");
		keywords.add("真二");
		keywords.add("水军");
		keywords.add("刷屏");

		StringBuffer sql = new StringBuffer("select Retweeted_User_WeiBo_Id from User_Status where ");
		for (String keyword : keywords) {
			sql.append("Retweeted_Text like '%");
			sql.append(keyword);
			sql.append("%' or ");
		}
		sql.append(" 1!=1 ");
		sql.append("union ");
		sql.append("select Status_Id from User_Status where ");
		for (String keyword : keywords) {
			sql.append("Text like '%");
			sql.append(keyword);
			sql.append("%' or ");
		}

		sql.append("Status_Type!=");
		sql.append("7");
		System.out.println(sql);
	}
	
	@Test
	public void testListIterator(){
		List<String> keywords = new ArrayList<String>();
		// 垃圾、广告、讨厌凡客、真2、真二、水军
		keywords.add("垃圾");
		keywords.add("广告");
		keywords.add("讨厌凡客");
		keywords.add("真2");
		keywords.add("真二");
		keywords.add("水军");
		keywords.add("刷屏");
		
		Iterator<String> it = keywords.iterator();
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		
		System.out.println(keywords.size());
	}
}
