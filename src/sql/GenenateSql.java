package sql;

import java.util.Date;

import cn.adsit.common.util.DateUtil;

public class GenenateSql {
	public static void main(String[] args) throws Exception {
		String[] arr = new String[]{"Video_Play_1"};
		//"Value_Add_1","Video_Click_1","Video_Demand_1",
		StringBuffer sb = new StringBuffer();
		for(String tmp : arr){
			for(int i=1;i<30;i++){
				String index = i<10?"0"+i:i+"";
				String sql = "select " +
						" (select iva_workstation_name from DB102.[iva].dbo.c_iva_workstation where iva_workstation_id = t.now_obj_id) as 媒体名称" +
						",(select content_class_name from DB102.[pre-roll].dbo.content_class where content_class_id = (select content_class_id from DB102.[pre-roll].dbo.ad_pos as ap where ap.ad_pos_id = t.now_obj_point_id)) 频道名称" +
						",(select ad_pos_name from DB102.[pre-roll].dbo.ad_pos as ap where ap.ad_pos_id = t.now_obj_point_id) 版位名称" +
						",t.* " +
						" from BACKUP_ADMAN_201111"+index+".dbo.Visit_Log_201111"+index+" t" +
						" where t.visitor_id in (" +
						" select visitor_id from test_visitors) ";
				sql += " \nunion all\n ";
				sb.append(sql);
			}
		}
		System.out.println(sb.toString());
		System.out.println("---------------------------------------------------");
		genSql();
	}
	
	
	private static void genSql () throws Exception{
		String sql = "select * from BACKUP_ADMAN_20120310.";
		String beginDate = "2012年05月23日";
		String endDate = "2012年06月26日";
		String nextDate = "2012年05月23日";
		while(beginDate.compareTo(endDate)<=0){
			Date simpleDate = DateUtil.parseDate(beginDate);
			String date = DateUtil.formateSimpleDate(simpleDate);
			System.out.println("select * from BACKUP_ADMAN_"+date+".dbo.Visit_Log_"+date+" where campaign_id='NLILFGU70024' \n union all");
			beginDate = DateUtil.formateDate(new Date(simpleDate.getTime() + 24*60*60*1000));
		}
	}
}
