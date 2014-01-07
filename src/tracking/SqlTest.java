package tracking;


/**
 * @author liuwy
 *
 */
public class SqlTest {

	public static void main(String[] args) {
		int statTime = 113;
		int endTime = 116;
		String ip = "1.2.3.21";
		
		for(int i = statTime;i<= endTime; i++){
			if(i%100>=60){
				continue;
			}
			System.out.println("select count(1) from  Video_Play_1_" + "0000".substring((i+"").length(), 4)+i + " where user_ip='"+ ip +"'");
			if(i!= endTime){
				System.out.println("union all");
			}
		}
	}
}