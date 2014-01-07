package tracking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import db.DBConnection;

public class GetMachineTable {
	static String[] ips = new String[]{"192.168.1.76","192.168.1.77","192.168.1.78","192.168.1.79","192.168.1.80","192.168.1.81","192.168.1.154","192.168.1.155","192.168.1.156","192.168.1.157","192.168.1.158","192.168.1.159"};
	static int j = 0;
	static String[] headers = new String[]{"Visit_Log_Id","Refer_Visit_Log_Id","Campaign_Id","Campaign_Track_Id","Enter_Refer_Workstation_Url","Enter_Refer_Workstation_Domain","Enter_Refer_Workstation_Id","Enter_Refer_Ad_Pos_Id","Enter_Refer_Campaign_Track_Id","Enter_Refer_Keyword","Enter_Refer_Searcher","Refer_Workstation_Url","Refer_Workstation_Domain","Refer_Workstation_Id","Refer_Ad_Pos_Id","Now_Url","Now_Url_Domain","Workstation_Id","Ad_Pos_Id","Target_Url","Campaign_Track_Type","Visit_Type","Visit_State","Rubbish_Type","Visitor_Id","Visitor_Ip","Zone_Province","Zone_City","Isp_Name","Os_Name","Browser_Version","Browser_Plus","Resolution_Type","Browser_Language","Flash_Version","Time_On_Page","Is_Enter","Is_Exit","Visit_Date","Visit_Time","Time_Stamp","Session_Id","Adn_User_Id","User_Agent"};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbName = "analysis_20120513";
//		String visitorId = "13315311974726peg61x1fx4rcm3fg8d";
		String[] visitorIds = new String[]{"1336867553426lhgmyiu7i010zrw31bf","1336447304088eetuvn05r1sz0tqpice",
				"1336741634203h5an5l2uthi8l8gd0b1","133690955510242rxfxqhnmyph7rmq25"
					};
//		String[] visitorIds = new String[]{"1309225651182toxnbm2r7haq8bmdx8c","132584326203161r9643pb53302w1w24mb4ul36g","1325846973828n32mz1dyp1tq2o42q7x7jl1nsdv"};
//		String[] visitorIds = new String[]{"13315311974726peg61x1fx4rcm3fg8d"};
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File("D:\\tracking\\明细数据.xls"));
			WritableFont wf = new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,false);
			WritableCellFormat format = new WritableCellFormat(wf);
			WritableSheet sheet = null;
			sheet = book.createSheet("明细数据", 0); 
			sheet.getSettings().setDefaultColumnWidth(20); 
			putRow(sheet,j++,Arrays.asList(headers));
			for(String visitorId : visitorIds){
				int ipSize = ips.length;
				int hashCode = Math.abs(visitorId.substring(0, 10).hashCode());
				int targetSettleIndex = hashCode%ipSize;
				System.out.println(ips[targetSettleIndex]);
				
				int hasCode = Math.abs(visitorId.hashCode());
				String tableName = "user_campaign_" + "0000".substring(0, 4 - ("" + hasCode % 2000).length()) + hasCode % 2000;
				
				System.out.println("select * from "+tableName+" where visitor_id='"+visitorId+"'");
				getDetail(ips[targetSettleIndex],dbName,tableName,visitorId,sheet);
			}
			book.write(); 
			book.close();
//			System.out.println(isNumeric(ips[targetSettleIndex]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void getDetail(String ip,String dbName,String tableName,String visitorId,WritableSheet sheet) throws Exception{
		String url = "jdbc:sqlserver://"+ip+":1433;DatabaseName="+dbName+";";
		String pwd = "Adsit!@#$";
		String user = "sa";
		DBConnection db = new DBConnection(url,user,pwd);
		String sql = "select * from "+tableName+" where visitor_id='"+visitorId+"'";
		
		ResultSet rs = db.getList(sql);
		while(rs.next()){
			List<String> datas = new ArrayList<String>();
			for(String tmp : headers){
				Object obj = rs.getObject(tmp);
				datas.add(obj==null?"":obj.toString());
			}
			putRow(sheet,j++,datas);
		}
		db.close();
	}

	public static void  putRow(WritableSheet ws, int rowNum, List<String> list){
		//设置单元格格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
		
		WritableCellFormat wcf_string = new WritableCellFormat(wf,NumberFormats.THOUSANDS_INTEGER);
		WritableCellFormat wcf_float = new WritableCellFormat(wf,NumberFormats.FLOAT);
		WritableCellFormat wcf_percent = new WritableCellFormat(wf, NumberFormats.PERCENT_FLOAT);
		
		try {
			wcf_percent.setAlignment(Alignment.CENTRE);
			wcf_percent.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_string.setAlignment(Alignment.CENTRE);
			wcf_string.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_float.setAlignment(Alignment.CENTRE);
			wcf_float.setVerticalAlignment(VerticalAlignment.CENTRE);
		//				wcfF.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);//设置细边   框          
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//循环写入单元格
		for(int j=0; j<list.size(); j++) {//写一行
			try {
				if(isNumeric(list.get(j))){
					Number num = null;
					if(list.get(j).contains("%")){
						num = new Number(j, rowNum, Double.parseDouble(list.get(j).substring(0,list.get(j).length()-1))/100,wcf_percent);
						ws.addCell(num);
					}else{
						num = new Number(j,rowNum,Double.parseDouble(list.get(j)),wcf_string);
						ws.addCell(num);
					}
				}else{
					if(list.get(j).matches("[\\d*,\\d*]+")){
						DecimalFormat df = new DecimalFormat("#,##;(#,##0)");
						double num=0.0;
						try {
							num = df.parse(list.get(j)).doubleValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Number cell = new Number(j,rowNum,num,wcf_string);
						ws.addCell(cell);
					}else if(list.get(j).matches("[\\d*,\\d*]+[\\.]{1}[\\d*]+")){
						DecimalFormat df = new DecimalFormat("###,###.##");
						double num=0.0;
						try {
							num = df.parse(list.get(j)).doubleValue();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Number cell = new Number(j,rowNum,num,wcf_float);
						ws.addCell(cell);
					}else{
						Label cell = new Label(j, rowNum, list.get(j),wcf_string);
						ws.addCell(cell);
					}
				}
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}

	}
	/**
	 * 判断传入的字符串是否全为数字或者是一个百分数
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]+[\\.]{1}[0-9]+[\\%]");
	    return pattern.matcher(str).matches();   
	}
}
