package adman;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.ConnectionFactory;

public class DataModify {
	public static void main(String[] args) throws SQLException {
//		modify();
//		modifyWorksation();
//		modifyCampaign();
		System.out.println(Integer.MAX_VALUE);
		Object obj = null;
		System.out.println((Long)obj);
		java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("HH");
		System.out.println(df.format(utilDate));
	}
	
	public static void modify() throws SQLException{
		String sql = "SELECT " +
				"af.Campaign_id" +
				",af.[Campaign_Track_Id]" +
				",af.[Workstation_Id]" +
				",af.[Ad_Pos_Id]" +
				" ,((case when af.[Frequency_Type]=1 then a.demand_count else a.click_count end )-(af.[Frequency_1_Count]" +
				"+af.[Frequency_2_Count]*2" +
				" +af.[Frequency_3_Count]*3" +
				" +af.[Frequency_4_Count]*4" +
				" +af.[Frequency_5_Count]*5" +
				"+af.[Frequency_6_Count]*6" +
				"+af.[Frequency_7_Count]*7" +
				"+af.[Frequency_8_Count]*8" +
				"+af.[Frequency_9_Count]*9" +
				"+af.[Frequency_10_Count]*10" +
				"+af.[Frequency_11_Count]*11" +
				"+af.[Frequency_12_Count]*12" +
				"+af.[Frequency_13_Count]*13" +
				"+af.[Frequency_14_Count]*14" +
				"+af.[Frequency_15_Count]*15" +
				"+af.[Frequency_16_Count]*16" +
				"+af.[Frequency_17_Count]*17" +
				"+af.[Frequency_18_Count]*18" +
				"+af.[Frequency_19_Count]*19)) as sukm" +
				",cast ((case when af.[Frequency_20_Count] = 0 then 0 else af.[Frequency_20_Count]*20 + ceiling(rand()*10)*af.[Frequency_20_Count] end) as int) as kk" +
				",af.[Frequency_1_Count]" +
				",af.[Frequency_2_Count]" +
				",af.[Frequency_3_Count]" +
				",af.[Frequency_4_Count]" +
				",af.[Frequency_5_Count]" +
				",af.[Frequency_6_Count]" +
				",af.[Frequency_7_Count]" +
				",af.[Frequency_8_Count]" +
				",af.[Frequency_9_Count]" +
				",af.[Frequency_10_Count]" +
				",af.[Frequency_11_Count]" +
				",af.[Frequency_12_Count]" +
				",af.[Frequency_13_Count]" +
				",af.[Frequency_14_Count]" +
				",af.[Frequency_15_Count]" +
				",af.[Frequency_16_Count]" +
				",af.[Frequency_17_Count]" +
				",af.[Frequency_18_Count]" +
				",af.[Frequency_19_Count]" +
				",af.[Frequency_20_Count]" +
				",af.[Frequency_Type]" +
				", (case when af.[Frequency_Type]=1 then a.demand_unique_count else a.click_unique_count end )as c" +
				" FROM [Ad_Pos_Frequency_Gather] af,Ad_Pos_Gather a" +
				"  where af.Campaign_id='SGB4IQMK0012' and " +
				"af.Campaign_id=a.Campaign_id " +
				"and af.[Campaign_Track_Id]=a.[Campaign_Track_Id]" +
				" and af.[Workstation_Id]=a.[Workstation_Id]" +
				" and af.[Ad_Pos_Id]=a.[Ad_Pos_Id]" +
				"  and af.[Frequency_Type] =3 ";
		
		Connection con  = ConnectionFactory.getConnection();
		Statement sst = con.createStatement();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		List<String> sqlList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		while(rs.next()){
			String Campaign_id = rs.getString("Campaign_id");
			String Campaign_Track_Id = rs.getString("Campaign_Track_Id");
			String Workstation_Id = rs.getString("Workstation_Id");
			String Ad_Pos_Id = rs.getString("Ad_Pos_Id");
			int Frequency_Type = rs.getInt("Frequency_Type");
			int Frequency_20_Count = rs.getInt("Frequency_20_Count");
			long sukm = rs.getLong("sukm");
			long kk = rs.getLong("kk");
			System.out.println("Frequency_Type:"+Frequency_Type+"Campaign_Track_Id:"+Campaign_Track_Id+",Workstation_Id:"+Workstation_Id+",adPosId:"+Ad_Pos_Id+",sukm:"+sukm+",kk:"+kk+",sukm-kk="+(sukm - kk));
			long cha = sukm - kk;
			/*if(cha<0)
				continue;*/
			int times = -1;
			long addTimes = -1;
			int i = 0;
			while(times==-1){
				if(Frequency_20_Count!=0&&(sukm/Frequency_20_Count)<100){
					break;
				}else if(sukm<100&&kk==0&&sukm>20){
					times = 20;
					addTimes = 1;
				}else if(kk==0&&sukm<20){
					times = 21;
					addTimes = 1;
				}else if(cha%3==0){
					times = 3;
					addTimes = cha/3;
				}else if(cha%2==0){
					times = 2;
					addTimes = cha/2;
				}
				
				cha = cha + i;
				i ++ ;
//				System.out.println("i:"+i+",cha:"+cha);
			}
//			--SGB41YPH0010
			if(times==-1){
				continue;
			}
			StringBuffer updateSql = new StringBuffer("update [Ad_Pos_Frequency_Gather] set ");
			if(times==21){
				System.out.println("=================================================");
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes*20);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==20){
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==3){
				updateSql.append("[Frequency_3_Count]=Frequency_3_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}else if(times==2){
				updateSql.append("[Frequency_2_Count]=Frequency_2_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}
			updateSql.append(" where Campaign_id='"+Campaign_id+"'");
			updateSql.append(" and Campaign_Track_Id='"+Campaign_Track_Id+"'");
			updateSql.append(" and Workstation_Id='"+Workstation_Id+"'");
			updateSql.append(" and Ad_Pos_Id='"+Ad_Pos_Id+"'");
			updateSql.append(" and Frequency_Type="+Frequency_Type+"");
			System.out.println(updateSql.toString()+";");
			map.put(Campaign_id+Campaign_Track_Id+Workstation_Id+Ad_Pos_Id+Frequency_Type, updateSql.toString());
			sqlList.add(updateSql.toString());
			sst.execute(updateSql.toString());
		}
		rs.close();
		st.close();
		sst.close();
		/*st = con.createStatement();
		System.out.println(map.keySet().size());
		for(int j=0 ;j<sqlList.size();j++){
			st.addBatch(sqlList.get(j));
		}
		System.out.println(sqlList.size());
		st.executeBatch();
		st.close();*/
	}
	
	public static void modifyWorksation() throws SQLException{
		String sql = "SELECT " +
				"af.Campaign_id" +
				",af.[Campaign_Track_Id]" +
				",af.[Workstation_Id]" +
				" ,((case when af.[Frequency_Type]=1 then a.demand_count else a.click_count end )-(af.[Frequency_1_Count]" +
				"+af.[Frequency_2_Count]*2" +
				" +af.[Frequency_3_Count]*3" +
				" +af.[Frequency_4_Count]*4" +
				" +af.[Frequency_5_Count]*5" +
				"+af.[Frequency_6_Count]*6" +
				"+af.[Frequency_7_Count]*7" +
				"+af.[Frequency_8_Count]*8" +
				"+af.[Frequency_9_Count]*9" +
				"+af.[Frequency_10_Count]*10" +
				"+af.[Frequency_11_Count]*11" +
				"+af.[Frequency_12_Count]*12" +
				"+af.[Frequency_13_Count]*13" +
				"+af.[Frequency_14_Count]*14" +
				"+af.[Frequency_15_Count]*15" +
				"+af.[Frequency_16_Count]*16" +
				"+af.[Frequency_17_Count]*17" +
				"+af.[Frequency_18_Count]*18" +
				"+af.[Frequency_19_Count]*19)) as sukm" +
				",cast ((case when af.[Frequency_20_Count] = 0 then 0 else af.[Frequency_20_Count]*20 + ceiling(rand()*10)*af.[Frequency_20_Count] end) as int) as kk" +
				",af.[Frequency_1_Count]" +
				",af.[Frequency_2_Count]" +
				",af.[Frequency_3_Count]" +
				",af.[Frequency_4_Count]" +
				",af.[Frequency_5_Count]" +
				",af.[Frequency_6_Count]" +
				",af.[Frequency_7_Count]" +
				",af.[Frequency_8_Count]" +
				",af.[Frequency_9_Count]" +
				",af.[Frequency_10_Count]" +
				",af.[Frequency_11_Count]" +
				",af.[Frequency_12_Count]" +
				",af.[Frequency_13_Count]" +
				",af.[Frequency_14_Count]" +
				",af.[Frequency_15_Count]" +
				",af.[Frequency_16_Count]" +
				",af.[Frequency_17_Count]" +
				",af.[Frequency_18_Count]" +
				",af.[Frequency_19_Count]" +
				",af.[Frequency_20_Count]" +
				",af.[Frequency_Type]" +
				", (case when af.[Frequency_Type]=1 then a.demand_unique_count else a.click_unique_count end )as c" +
				" FROM [Workstation_Frequency_Gather] af,Workstation_Gather a" +
				"  where af.Campaign_id='SGB4IQMK0012' and " +
				"af.Campaign_id=a.Campaign_id " +
				"and af.[Campaign_Track_Id]=a.[Campaign_Track_Id]" +
				" and af.[Workstation_Id]=a.[Workstation_Id]" +
				"  and af.[Frequency_Type] =3 ";
		
		Connection con  = ConnectionFactory.getConnection();
		Statement sst = con.createStatement();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		List<String> sqlList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		while(rs.next()){
			String Campaign_id = rs.getString("Campaign_id");
			String Campaign_Track_Id = rs.getString("Campaign_Track_Id");
			String Workstation_Id = rs.getString("Workstation_Id");
			int Frequency_Type = rs.getInt("Frequency_Type");
			int Frequency_20_Count = rs.getInt("Frequency_20_Count");
			long sukm = rs.getLong("sukm");
			long kk = rs.getLong("kk");
			long cha = sukm - kk;
			/*if(cha<0)
				continue;*/
			System.out.println("Frequency_Type:"+Frequency_Type+"Campaign_Track_Id:"+Campaign_Track_Id+",Workstation_Id:"+Workstation_Id+",sukm:"+sukm+",kk:"+kk+",sukm-kk="+(sukm - kk));
			int times = -1;
			long addTimes = -1;
			int i = 0;
			while(times==-1){
				if(Frequency_20_Count!=0&&(sukm/Frequency_20_Count)<100){
					break;
				}else if(sukm<100&&kk==0&&sukm>20){
					times = 20;
					addTimes = 1;
				}else if(kk==0&&sukm<20){
					times = 21;
					addTimes = 1;
				}else if(cha%3==0){
					times = 3;
					addTimes = cha/3;
				}else if(cha%2==0){
					times = 2;
					addTimes = cha/2;
				}
				
				cha = cha + i;
				i ++ ;
//				System.out.println("i:"+i+",cha:"+cha);
			}
			
			if(times==-1){
				continue;
			}
			
//			--SGB41YPH0010
			StringBuffer updateSql = new StringBuffer("update [Workstation_Frequency_Gather] set ");
			if(times==21){
				System.out.println("=================================================");
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes*20);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==20){
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==3){
				updateSql.append("[Frequency_3_Count]=Frequency_3_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}else if(times==2){
				updateSql.append("[Frequency_2_Count]=Frequency_2_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}
			updateSql.append(" where Campaign_id='"+Campaign_id+"'");
			updateSql.append(" and Campaign_Track_Id='"+Campaign_Track_Id+"'");
			updateSql.append(" and Workstation_Id='"+Workstation_Id+"'");
			updateSql.append(" and Frequency_Type="+Frequency_Type+"");
			System.out.println(updateSql.toString()+";");
			map.put(Campaign_id+Campaign_Track_Id+Workstation_Id+Frequency_Type, updateSql.toString());
			sqlList.add(updateSql.toString());
			sst.execute(updateSql.toString());
		}
		rs.close();
		st.close();
		sst.close();
		/*st = con.createStatement();
		System.out.println(map.keySet().size());
		for(int j=0 ;j<sqlList.size();j++){
			st.addBatch(sqlList.get(j));
		}
		System.out.println(sqlList.size());
		st.executeBatch();
		st.close();*/
	}
	
	public static void modifyCampaign() throws SQLException{
		String sql = "SELECT " +
				"af.Campaign_id" +
				",af.[Campaign_Track_Id]" +
				" ,((case when af.[Frequency_Type]=1 then a.demand_count else a.click_count end )-(af.[Frequency_1_Count]" +
				"+af.[Frequency_2_Count]*2" +
				" +af.[Frequency_3_Count]*3" +
				" +af.[Frequency_4_Count]*4" +
				" +af.[Frequency_5_Count]*5" +
				"+af.[Frequency_6_Count]*6" +
				"+af.[Frequency_7_Count]*7" +
				"+af.[Frequency_8_Count]*8" +
				"+af.[Frequency_9_Count]*9" +
				"+af.[Frequency_10_Count]*10" +
				"+af.[Frequency_11_Count]*11" +
				"+af.[Frequency_12_Count]*12" +
				"+af.[Frequency_13_Count]*13" +
				"+af.[Frequency_14_Count]*14" +
				"+af.[Frequency_15_Count]*15" +
				"+af.[Frequency_16_Count]*16" +
				"+af.[Frequency_17_Count]*17" +
				"+af.[Frequency_18_Count]*18" +
				"+af.[Frequency_19_Count]*19)) as sukm" +
				",cast ((case when af.[Frequency_20_Count] = 0 then 0 else af.[Frequency_20_Count]*20 + ceiling(rand()*10)*af.[Frequency_20_Count] end) as int) as kk" +
				",af.[Frequency_1_Count]" +
				",af.[Frequency_2_Count]" +
				",af.[Frequency_3_Count]" +
				",af.[Frequency_4_Count]" +
				",af.[Frequency_5_Count]" +
				",af.[Frequency_6_Count]" +
				",af.[Frequency_7_Count]" +
				",af.[Frequency_8_Count]" +
				",af.[Frequency_9_Count]" +
				",af.[Frequency_10_Count]" +
				",af.[Frequency_11_Count]" +
				",af.[Frequency_12_Count]" +
				",af.[Frequency_13_Count]" +
				",af.[Frequency_14_Count]" +
				",af.[Frequency_15_Count]" +
				",af.[Frequency_16_Count]" +
				",af.[Frequency_17_Count]" +
				",af.[Frequency_18_Count]" +
				",af.[Frequency_19_Count]" +
				",af.[Frequency_20_Count]" +
				",af.[Frequency_Type]" +
				", (case when af.[Frequency_Type]=1 then a.demand_unique_count else a.click_unique_count end )as c" +
				" FROM [Campaign_Track_Frequency_Gather] af,Campaign_Track_Gather a" +
				"  where af.Campaign_id='SGB4IQMK0012' and " +
				"af.Campaign_id=a.Campaign_id " +
				"and af.[Campaign_Track_Id]=a.[Campaign_Track_Id]" +
				"  and af.[Frequency_Type] =3 ";
		
		Connection con  = ConnectionFactory.getConnection();
		Statement sst = con.createStatement();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		List<String> sqlList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		while(rs.next()){
			String Campaign_id = rs.getString("Campaign_id");
			String Campaign_Track_Id = rs.getString("Campaign_Track_Id");
			int Frequency_Type = rs.getInt("Frequency_Type");
			int Frequency_20_Count = rs.getInt("Frequency_20_Count");
			long sukm = rs.getLong("sukm");
			long kk = rs.getLong("kk");
			long cha = sukm - kk;
			if(cha<0)
				continue;
			System.out.println("Frequency_Type:"+Frequency_Type+"Campaign_Track_Id:"+Campaign_Track_Id+",sukm:"+sukm+",kk:"+kk+",sukm-kk="+(sukm - kk));
			int times = -1;
			long addTimes = -1;
			int i = 0;
			while(times==-1){
				if(Frequency_20_Count!=0&&(sukm/Frequency_20_Count)<100){
					break;
				}else if(sukm<100&&kk==0&&sukm>20){
					times = 20;
					addTimes = 1;
				}else if(kk==0&&sukm<20){
					times = 21;
					addTimes = 1;
				}else if(cha%3==0){
					times = 3;
					addTimes = cha/3;
				}else if(cha%2==0){
					times = 2;
					addTimes = cha/2;
				}
				
				cha = cha + i;
				i ++ ;
//				System.out.println("i:"+i+",cha:"+cha);
			}
//			--SGB41YPH0010
			if(times==-1)
				continue;
			
			StringBuffer updateSql = new StringBuffer("update [Campaign_Track_Frequency_Gather] set ");
			if(times==21){
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes*20);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==20){
				updateSql.append("[Frequency_1_Count]=Frequency_1_Count-");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_20_Count=Frequency_20_Count+");
				updateSql.append(addTimes);
			}else if(times==3){
				updateSql.append("[Frequency_3_Count]=Frequency_3_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}else if(times==2){
				updateSql.append("[Frequency_2_Count]=Frequency_2_Count+");
				updateSql.append(addTimes);
				updateSql.append(",Frequency_1_Count=Frequency_1_Count-");
				updateSql.append(addTimes);
			}
			updateSql.append(" where Campaign_id='"+Campaign_id+"'");
			updateSql.append(" and Campaign_Track_Id='"+Campaign_Track_Id+"'");
			updateSql.append(" and Frequency_Type="+Frequency_Type+"");
			System.out.println(updateSql.toString()+";");
			map.put(Campaign_id+Campaign_Track_Id+Frequency_Type, updateSql.toString());
			sqlList.add(updateSql.toString());
			sst.execute(updateSql.toString());
		}
		rs.close();
		st.close();
		sst.close();
		/*st = con.createStatement();
		System.out.println(map.keySet().size());
		for(int j=0 ;j<sqlList.size();j++){
			st.addBatch(sqlList.get(j));
		}
		System.out.println(sqlList.size());
		st.executeBatch();
		st.close();*/
	}
}
