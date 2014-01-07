package string;

import java.util.Arrays;

public class Test2 {
	public static void main(String[] args) {
		System.out.println("4028814c2a3bf05b012a3bf2afa219f9".hashCode());
		System.out.println("4028814c2a3bf05b012a3bf2afa219f9".toUpperCase().hashCode());
//		String str = "1316760865359k06exm7wxw1ko6a0xd6_61454	 	SG59OSHM001Q	SG5C70OB005U	 		 	 	 	 	 	 		 	 	http://finance.ifeng.com/news/20110923/4668893.shtml"+
//"	http://finance.ifeng.com	402880a32ce368f5012ce376dadd0023	SG59DE5G000R		1	1	1	0	1316394771890kv94hibqzdkolmn6946	123.190.225.233	辽宁省	盘锦市	联通	Windows XP	MSIE 8.0	.net		 	 				2011年09月23日	2011年09月23日 14:54:25	1316760865359";
		String str = "1312632729022atov768h1ixci6fcr50_72012		SGD4L24T0000	SGNC84T70000		NULL							NULL				NULL	402881092cabfe13012cb9dcf4e50e91	SGD6YWSW005G	http://www.adidasevent.com/dwyw	1	2	1	0	129718557660163wikktyggp4678vm0a	220.255.2.163	国外	无法定位		Windows 7			NULL	a		NULL	NULL	NULL	2011年08月06日	2011年08月06日 20:12:09	1312632729022";
//		String str = "http://www.renren.com/home	Accept-Language: zh-cn	Accept-Encoding: gzip, deflate	http://www.renren.co";
		//1312632729022atov768h1ixci6fcr50_72012@	@ @	@SGD4L24T0000@	@SGNC84T70000@	@ @	@@	@ @	@ @	@ @	@ @	@ @	@ @	@@	@ @	@ @	@ @	@@	@402881092cabfe13012cb9dcf4e50e91@	@SGD6YWSW005G@	@http://www.adidasevent.com/dwyw@	@1@	@2@	@1@	@0@	@129718557660163wikktyggp4678vm0a@	@220.255.2.163@	@国外@	@无法定位@	@ @	@Windows 7@	@ @	@ @	@@	@?	@ @	@@	@@	@@	@2011年08月06日@	@2011年08月06日 20:12:09@	@131263272902213126327290244gmlrpgffcch9z3gk59_72012@	@ @	@SGEZMQ3W00A2@	@SGF0929F00A3@	@ @	@@	@ @	@ @	@ @	@ @	@ @	@ @	@@	@ @	@ @	@ @	@@	@402881092cabfe13012cb9d878eb083a@	@SGEZ7KSX009R@	@@	@1@	@1@	@1@	@0@	@1306023149324n9t8bl7uxvquee38v84@	@124.77.37.187@	@上海市@	@徐汇区@	@电信@	@Windows XP@	@MSIE 8.0@	@.net@	@@	@zh-CN@	@ @	@@	@@	@@	@2011年08月06日@	@2011年08月06日 20:12:09@	@1312632729024
		String[] arr = str.split("\\t");
		System.out.println(arr.length);
		for(String tmp : arr){
			System.out.println(tmp.length());
		}
		String dd = null;
		System.out.println("".equalsIgnoreCase(dd));
		
		System.out.println((Long) null);
		System.out.println(3|3);
		System.out.println(3|2);
		String visitLogId = "13180518048150a3ggm3czhflc4als13";
		;
		System.out.println(Math.abs(visitLogId.hashCode())%6);
		System.out.println(Arrays.asList("402881092f688aa6012f7dbc4c151625,4028810935cc05780135ec13055914a2,4028810936c930890136c9c1f5ab0028,4028810936c930890136c9c1f5ea002a,4028810936c930890136c9e18a150361,4028810936c930890136c9e18a530363,402881862fd13298012fd354aaa60010".split(",")).size());
		System.out.println("fsdkfjd$jfkds".split("\\$").length);
		
		System.out.println("19:00:00".compareTo("18:52:22")>0);
		
		String tableName = "user_campaign_0000";
		String sql = "if   col_length( '"+tableName+"',   'Register_Id')   is   null  alter table "+tableName+"  add Register_Id varchar(300);";
		sql += "if col_length( '"+ tableName +"', 'Creative_Id') is null alter table "+ tableName + " add Creative_Id varchar(40);";
		sql += "if col_length( '"+ tableName +"', 'Keywords_Group_Id') is null alter table "+ tableName + " add Keywords_Group_Id varchar(40);";
		sql += "if col_length( '"+ tableName +"', 'Keywords_Id') is null alter table "+ tableName + " add Keywords_Id varchar(40);";
		
		System.out.println(sql);
	}
}
