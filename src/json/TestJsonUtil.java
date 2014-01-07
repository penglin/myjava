package json;

import net.sf.json.JSONObject;

public class TestJsonUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User(1,"lin",23);
		String bean = JsonUtil.beanToJson(user);
		System.out.println(bean);
		
		try {
			System.out.println(JsonUtil.toJson(user));
			System.out.println(JsonUtil.toJson(new Integer(23)));
			User jsonUser = (User) JsonUtil.fromJson(JSONObject.fromObject("{\"JSONUTIL-FOR-NAME\":\"json.User\",\"JSONUTIL-FOR-TYPE\":8,\"name\":\"lin\",\"age\":23,\"id\":3}"));
			System.out.println(jsonUser);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String json = "{\"Charge_Money\":47,\"Demand_Id\":\"0af62934705f513ec7bd0000003ed1e2513ec7c0\",\"Publish_Id\":\"402881ad3d3a732e013d3df3d6c1463a\",\"Ad_Play_Length\":10000,\"Ad_Duration\":10000,\"Play_End_Time\":\"2013年03月12日 14:14:31\",\"Pay_Money\":47,\"Workstation_Id\":\"tanx\",\"Ad_Pos_Id\":\"t-mm_33642060_342d3a732e013d3ee1b5cb248b\",\"Ad_Duration\":10000,\"Play_End_Time\":\"2013年03月12日 14:14:24\",\"Pay_Money\":13,\"Ad_Play_Length\":10000,\"Workstation_Id\":\"tanx\",\"Ad_Pos_Id\":\"t-mm_10024662_3445902_11178345\",\"Play_Begin_Time\":\"2013年03月12日 14:14:24\",\"Ad_Pos_Info\":\"\",\"detail_type\":\"play\"}";
		JSONObject jsonObj = JSONObject.fromObject(json);
		System.out.println(jsonObj.get("Ad_Play_Length"));
		jsonObj.get("Ad_Play_Length");
		System.out.println(jsonObj.getString("Ad_Pos_Id"));
		jsonObj.getString("Ad_Pos_Id");
		
		jsonObj.put("Ad_Play_Length", "122222");
		
		System.out.println(jsonObj.toString());
	}

}

class User{
	private int id;
	private String name;
	private int age;
	public User(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id:"+id+",name:"+name+",age:"+age;
	}
	
}
