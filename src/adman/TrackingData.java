package adman;

public class TrackingData {
	public static void main(String[] args) {
		System.out.println(ClassLoader.getSystemResource("").getPath());
		int day = 3;
		int hour = 0;
		int min = 0;
		assert day == 3 : "day��ֵ��3";
		System.out.println("fsdfsd'fsdf".replaceAll("'", "''"));
		System.out.println("����ʧ�ܣ���Ϊ�� �� 'te' ���Ѵ�������Ϊ 'visit_date_index' ��������ͳ����Ϣ��".indexOf("�Ѵ�������Ϊ 'visit_date_index' ������"));
		System.out.println("000".substring(0, 3 - ("" + 4465464646L % 2000).length()));
		System.out.println(  4465464646L % 2000);
		System.out.println(day);
		/*for(int i=hour;i<24;i++){
			for(int j=min;j<60;j++){
				String tableName = day+ ("00".substring(0,2-(""+i).length())+i)+"00".substring(0,2-(""+j).length())+j;
				String sql = "select * from visit_log_"+tableName;
				System.out.println(sql);
				
			}
		}*/
		
		for(int index=0;index<2000;index++){
			String tableIndex = "0000".substring(0,4-(index+"").length())+index;
			//��������
			System.out.println(tableIndex);
		}
	}
}