package adman;

public class TrackingData {
	public static void main(String[] args) {
		System.out.println(ClassLoader.getSystemResource("").getPath());
		int day = 3;
		int hour = 0;
		int min = 0;
		assert day == 3 : "day的值是3";
		System.out.println("fsdfsd'fsdf".replaceAll("'", "''"));
		System.out.println("操作失败，因为在 表 'te' 上已存在名称为 'visit_date_index' 的索引或统计信息。".indexOf("已存在名称为 'visit_date_index' 的索引"));
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
			//添加任务
			System.out.println(tableIndex);
		}
	}
}
