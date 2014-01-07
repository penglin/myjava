package db;

public class SQL {
	public static void main(String[] args) {
		int hourTime;
		String date;
		String beginIndex;
		String endIndex;
		String caseWhen;
		String sql;
		String end;
		hourTime = 14;
		beginIndex = "0000";
		endIndex = "2400";
		date = "20110701";
		caseWhen = "";
		end = "";
		sql = "select count(hour),b,c from (\n";

		while (beginIndex.compareTo(endIndex) <= 0) {
			String tmp = beginIndex;
			beginIndex = getIndxe(beginIndex, hourTime);
			caseWhen = caseWhen + "\ncase when TO_CHAR(operate_time,'YYYYMMDDHH24MISS') between '" + date + tmp + "' and '" + date + beginIndex + "' then '"
					+ tmp + "-" + beginIndex + "' else ";
			end = end + "end ";
		}
		sql = sql + "select " + caseWhen + " 'ÆäËû' " + end + "  as hour,b,c from table ) tmp group by hour";
		System.out.println(sql);
	}

	private static String getIndxe(String beginIndex, int hourTime) {
		long time = (Long.parseLong(beginIndex) + hourTime);
		if (time>=60) {
			time = time - 60 + 100;
		}
		beginIndex = "0000".substring(0, 4 - (time+"").length()) + time;
		return beginIndex;
	}
}
