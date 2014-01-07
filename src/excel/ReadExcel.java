package excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.adsit.common.util.DateUtil;

public class ReadExcel {
	private static short color = 0;
	public static void main(String[] args) throws Exception, IOException {
//		test();
		
		/*XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("test.xlsx")));
		Sheet sheet = wb.getSheetAt(0);
		int merges = sheet.getNumMergedRegions();
		System.out.println(merges);
		
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		for(int i=firstRow;i<lastRow;i++){
			Row row = sheet.getRow(i);
			int firstColnum = row.getFirstCellNum();
			int lastColnum = row.getLastCellNum();
			System.out.println("------------第"+i+"行-------------");
			for(int j=firstColnum;j<lastColnum;j++){
				Cell cell = row.getCell(j);
				if(cell==null)
					continue;
				int type = cell.getCellType();
				switch (type) {
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue()+"\t");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue()+"\t");
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
		
		int r = 1;
		int c = 14;
		Row row = sheet.getRow(r);
		for(int i=c;i<row.getLastCellNum();i++){
			Cell cell = row.getCell(i);
			if(cell==null)
				continue;
			try {
				CellRangeAddress cra = cell.getArrayFormulaRange();
				cra.formatAsString();
				int firstColumn = cra.getFirstColumn();
				int lastColumn = cra.getLastColumn();
				System.out.println(firstColumn + "--To--" + lastColumn);
			} catch (Exception e) {
				// TODO: handle exception
			}
			int type = cell.getCellType();
			switch (type) {
			case Cell.CELL_TYPE_NUMERIC:
				System.out.print(cell.getNumericCellValue()+"\t");
				break;
			case Cell.CELL_TYPE_STRING:
				System.out.print(cell.getStringCellValue()+"\t");
				break;
			default:
				break;
			}
		}
		System.out.println();
		for(int i=0;i<sheet.getNumMergedRegions();i++){
			CellRangeAddress cra = sheet.getMergedRegion(i);
			String value = cra.toString();
			int firstColumn = cra.getFirstColumn();
			int lastColumn = cra.getLastColumn();
			for(int j=firstColumn;j<=lastColumn;j++){
				if(sheet.getRow(3).getCell(j)==null)
					continue;
				String date = sheet.getRow(3).getCell(j).toString();
				System.out.print(date+"--");
			}
			System.out.println(value+"--value--"+firstColumn + "--To--" + lastColumn);
		}
		
		Object[] obj = getSpanPot(sheet, 1, 2, 3, 14);
		boolean flag = Boolean.parseBoolean(obj[0].toString());
		System.out.println(flag);*/
		
		readExcel();
	}
	
	private static void readExcel() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("adpos.txt")));
		String line = null;
		Map<String,String> nameAndUrl = new HashMap<String, String>();
		while((line=br.readLine())!=null){
			String[] values = line.split("\t");
			nameAndUrl.put(values[1]+values[2]+values[3], values[0]);
		}
		br.close();
		System.out.println(nameAndUrl.get("娇韵诗_site1.首页PV"));
		
		FileOutputStream out = new FileOutputStream("sql.txt");
		FileOutputStream out2 = new FileOutputStream("sql2.txt");
		
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("jiao.xlsx")));
		Sheet sheet = wb.getSheetAt(0);
		int merges = sheet.getNumMergedRegions();
		System.out.println(merges);
		
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		
		int gatherDate = 0;
		int campaignTrackName = 1;
		int workstatinName = 2;
		int contentClassName = 3;
		int adPosName = 4;
		String landingPageAdPosId = nameAndUrl.get("娇韵诗_site1.首页PV");
		Row startRow = sheet.getRow(0);
		SimpleDateFormat simpleDateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		for(int i=firstRow+1;i<=lastRow;i++){
			Row row = sheet.getRow(i);
			int firstColnum = row.getFirstCellNum();
			int lastColnum = row.getLastCellNum();
	        Date d = simpleDateFormatSimple.parse(row.getCell(gatherDate).toString());
	        String date = simpleDateFormat.format(d);
//			System.out.println("------------第"+i+"行-------------");
			String enterReferAdPosId = null;
			if(row.getCell(3).toString().equals("直接访问")){
				enterReferAdPosId="";
			}else{
				enterReferAdPosId = nameAndUrl.get(row.getCell(workstatinName).toString()+row.getCell(contentClassName).toString()+row.getCell(adPosName).toString());
			}
			for(int j=11;j<lastColnum;j=j+2){
				String siteAdPosId = nameAndUrl.get("娇韵诗_site"+startRow.getCell(j).toString().replaceAll("\\_PV|\\_Click", ""));
				int num = (int)Double.parseDouble(row.getCell(j).toString());
				if(i==74){
					System.out.println(row.getCell(workstatinName).toString()+row.getCell(contentClassName).toString()+row.getCell(adPosName).toString());
				}
				if(num==0){
					continue;
				}
				//row.getCell(workstatinName).toString()+row.getCell(contentClassName).toString()+row.getCell(adPosName).toString()+startRow.getCell(j).toString().replaceAll("\\_PV|\\_Click", "")+
				String sql = "insert into visit_log_test select top "+num+" newid() as [Visit_Log_Id]"+
						",Visit_Log_Id as [Refer_Visit_Log_Id]"+
						",[Campaign_Id]"+
						",[Campaign_Track_Id]"+
						",[Enter_Refer_Url]"+
						",[Enter_Refer_Url_Domain]"+
						",[Enter_Refer_Obj_Id]"+
						",[Enter_Refer_Obj_Point_Id]"+
						",[Enter_Refer_Campaign_Track_Id]"+
						",[Enter_Refer_Keyword]"+
						",[Enter_Refer_Searcher]"+
						",[Refer_Url]"+
						",[Refer_Url_Domain]"+
						",'4028810934e5976801353d5e33ec074c_sp' as  [Refer_Obj_Id]"+
						",'"+landingPageAdPosId+"' as [Refer_Obj_Point_Id]"+
						",[Now_Url]"+
						",[Now_Url_Domain]"+
						",[Now_Obj_Id]"+
						",'"+siteAdPosId+"' as [Now_Obj_Point_Id]"+
						",[Target_Url]"+
						",[Campaign_Track_Type]"+
						",2 [Visit_Type]"+
						",[Visit_State]"+
						",[Rubbish_Data_Type]"+
						",[Visitor_Id]"+
						",[Visitor_Ip]"+
						",[Province_Name]"+
						",[Province_City_Name]"+
						",[Isp_Name]"+
						",[Os_Name]"+
						",[Browser_Version]"+
						",[Browser_Plus]"+
						",[Resolution_Type]"+
						",[Browser_Language]"+
						",'click_add' as [Flash_Version]"+
						",[Time_On_Page]"+
						",[Is_Enter]"+
						",[Is_Exit]"+
						",[Visit_Date]"+
						",[Visit_Date_Time]"+
						",[Time_Stamp]"+
						",[Session_Id]"+
						",[Adn_User_Id]"+
						",[User_Agent]"+
						",[Enter_Refer_Campaign_Id] from visit_log_test where Enter_Refer_Obj_Point_Id='"+enterReferAdPosId+"' and now_obj_point_id='"+landingPageAdPosId+"'" +
						" and Is_Exit=0 and visit_Type=1" +//SQL语句这里不同
						" and Visit_Date='"+date+"' order by rand()";
				if("16.抢先体验-首页左侧".equals(startRow.getCell(j).toString().replaceAll("\\_PV|\\_Click", ""))){
					sql = "insert into visit_log_test select top "+num+" newid() as [Visit_Log_Id]"+
					",Visit_Log_Id as [Refer_Visit_Log_Id]"+
					",[Campaign_Id]"+
					",[Campaign_Track_Id]"+
					",[Enter_Refer_Url]"+
					",[Enter_Refer_Url_Domain]"+
					",[Enter_Refer_Obj_Id]"+
					",[Enter_Refer_Obj_Point_Id]"+
					",[Enter_Refer_Campaign_Track_Id]"+
					",[Enter_Refer_Keyword]"+
					",[Enter_Refer_Searcher]"+
					",[Refer_Url]"+
					",[Refer_Url_Domain]"+
					",'4028810934e5976801353d5e33ec074c_sp' as [Refer_Obj_Id]"+
					",'"+landingPageAdPosId+"' as [Refer_Obj_Point_Id]"+
					",[Now_Url]"+
					",[Now_Url_Domain]"+
					",[Now_Obj_Id]"+
					",'"+siteAdPosId+"' as [Now_Obj_Point_Id]"+
					",[Target_Url]"+
					",[Campaign_Track_Type]"+
					",2 [Visit_Type]"+
					",[Visit_State]"+
					",[Rubbish_Data_Type]"+
					",[Visitor_Id]"+
					",[Visitor_Ip]"+
					",[Province_Name]"+
					",[Province_City_Name]"+
					",[Isp_Name]"+
					",[Os_Name]"+
					",[Browser_Version]"+
					",[Browser_Plus]"+
					",[Resolution_Type]"+
					",[Browser_Language]"+
					",'click_add_special' as [Flash_Version]"+
					",[Time_On_Page]"+
					",[Is_Enter]"+
					",[Is_Exit]"+
					",[Visit_Date]"+
					",[Visit_Date_Time]"+
					",[Time_Stamp]"+
					",[Session_Id]"+
					",[Adn_User_Id]"+
					",[User_Agent]"+
					",[Enter_Refer_Campaign_Id] from visit_log_test where Enter_Refer_Obj_Point_Id='"+enterReferAdPosId+"' and now_obj_point_id='"+landingPageAdPosId+"'" +
					" and Visit_Date='"+date+"' and visit_Type=1 order by rand()";
					
					out2.write(sql.getBytes());
					out2.write("\n".getBytes());
				}else{
					out.write(sql.getBytes());
					out.write("\n".getBytes());
				}
				
//				System.out.println(sql);
			}
		}
		System.out.println(lastRow);
		out.flush();
		out.close();
		out2.flush();
		out2.close();
	}
	
	
	private static Object[] getSpanPot(Sheet sheet,int rowMonthIndex ,int rowDateIndex, int rowDataIndex ,int startColnumIndex){
		Object[] obj = new Object[2];
		boolean flag = true;
		Map<String,String> dateToData = new HashMap<String, String>();
		Row monthRow = sheet.getRow(rowMonthIndex);
		Row dateRow = sheet.getRow(rowDateIndex);
		Row dataRow = sheet.getRow(rowDataIndex);
		String month = "";
		for(int i=startColnumIndex;i<dateRow.getPhysicalNumberOfCells();i++){
			Cell cell = dateRow.getCell(i);
			if(cell==null||cell.toString().trim().length()==0)
				continue;
			String value = monthRow.getCell(i)==null?"":monthRow.getCell(i).toString();
			if(value.length()>0){
				month = value;
			}
			String date = month+"-"+((int)Double.parseDouble(dateRow.getCell(i).toString()));
			String data = dataRow.getCell(i)==null||dataRow.getCell(i).toString().length()==0?"":((int)Double.parseDouble(dataRow.getCell(i).toString()))+"";
			if(dateToData.containsKey(date)){
//				error.append("第["+(rowDateIndex+1)+"]行填写的点位排期的日期有误，请修改");
				flag = false;
				break;
			}
			dateToData.put(date, data);
			System.out.println(date+"----"+data);
		}
		obj[0] = flag;
		obj[1] = dateToData;
		return obj;
	}
	
	
	public static void copySheet(Sheet sourceSheet,Sheet targetSheet){
		
		
	}
	
	public static void test() throws FileNotFoundException, IOException{
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("test.xlsx")));
		Sheet sourceSheet = wb.getSheetAt(0);
		
//		XSSFWorkbook wbTarget = new XSSFWorkbook();
		Sheet targetSheet = wb.createSheet("tews");
		int i ;
		CellRangeAddress cra;
		Row sourceRow;
		Row targetRow;
		Cell sourceCell;
		Cell targetCell;
		int cType;
		int j;
		// 拷贝合并的单元格
		for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			cra = sourceSheet.getMergedRegion(i);
			CellRangeAddress newCra = new CellRangeAddress(cra.getFirstRow(),cra.getLastRow(),cra.getFirstColumn(),cra.getLastColumn());
			targetSheet.addMergedRegion(newCra);
		}
		// 设置列宽
		for (i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
			sourceRow = sourceSheet.getRow(i);
			if (sourceRow != null) {
				for (j = sourceRow.getLastCellNum(); j > sourceRow
						.getFirstCellNum(); j--) {
					targetSheet
							.setColumnWidth(j, sourceSheet.getColumnWidth(j));
					targetSheet.setColumnHidden(j, false);
				}
				break;
			}
		}
		
		// 拷贝行并填充数据
		for (i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
			sourceRow = sourceSheet.getRow(i);
			if (sourceRow == null) {
				continue;
			}
			targetRow = targetSheet.createRow(i);
			targetRow.setHeight(sourceRow.getHeight());
			for (j = sourceRow.getFirstCellNum(); j < sourceRow.getLastCellNum(); j++) {
				sourceCell = sourceRow.getCell(j);
				if (sourceCell == null) {
					continue;
				}
				targetCell = targetRow.createCell(j);
//				CellStyle style = targetCell.getSheet().getWorkbook().createCellStyle();
				targetCell.setCellStyle(setCellStyle(targetCell.getSheet().getWorkbook().createCellStyle(),sourceCell.getCellStyle(),sourceCell.toString()));
				
				cType = sourceCell.getCellType();
				targetCell.setCellType(cType);
				switch (cType) {
				case Cell.CELL_TYPE_BOOLEAN:
					targetCell.setCellValue(sourceCell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					targetCell
							.setCellErrorValue(sourceCell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					// parseFormula这个函数的用途在后面说明
					targetCell.setCellFormula(parseFormula(sourceCell
							.getCellFormula()));
					break;
				case Cell.CELL_TYPE_NUMERIC:
					targetCell.setCellValue(sourceCell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					targetCell
							.setCellValue(sourceCell.getRichStringCellValue());
					break;
				}

			}

		}
		
		File targetExcel = new File("testTarget.xlsx");
		if(targetExcel.exists()){
			targetExcel.delete();
			targetExcel.createNewFile();
		}
		
		FileOutputStream fileOut = new FileOutputStream(targetExcel);
		wb.write(fileOut);
		fileOut.close();
	}
	
	/**
	 * 生成一个单元格样式
	 * @param style
	 * @return
	 */
	private static CellStyle setCellStyle(CellStyle style,CellStyle sourceStyle,String value){
		style.setBorderBottom(sourceStyle.getBorderBottom());
		style.setBorderLeft(sourceStyle.getBorderLeft());
		style.setBorderRight(sourceStyle.getBorderRight());
		style.setBorderTop(sourceStyle.getBorderTop());
		style.setAlignment(sourceStyle.getAlignment());
		style.setVerticalAlignment(sourceStyle.getVerticalAlignment());
		style.setDataFormat(sourceStyle.getDataFormat());
		System.out.println(color+"--"+value);
		style.setFillForegroundColor(color++);
//		style.setFillForegroundColor((short)62);
		style.setFillPattern(sourceStyle.getFillPattern());
		return sourceStyle;
	}
	
	private static String parseFormula(String pPOIFormula) {
		final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
		StringBuffer result = null;
		int index;

		result = new StringBuffer();
		index = pPOIFormula.indexOf(cstReplaceString);
		if (index >= 0) {
			result.append(pPOIFormula.substring(0, index));
			result.append(pPOIFormula.substring(index
					+ cstReplaceString.length()));
		} else {
			result.append(pPOIFormula);
		}

		return result.toString();
	}
}
