package number;

import java.text.DecimalFormat;
import java.text.ParseException;

 

public class NumberFarmat {

    public static void main(String[] args) {

       double sd = 323;

       

       //第一种方法 10000.0这个小数点后只表示保留小数，和位数没关系。

       double d1 = (double) (Math.round(sd*10000)/10000.0000000000); 

       double d2 = (double) (Math.round(sd*10000)/10000.0);

System.out.println("4位小数测试："+d2);
System.out.println(32/234);

System.out.println(0.0d==0);
       System.out.println(Double.parseDouble("0"));

       //第二种方法

       DecimalFormat df2  = new DecimalFormat("0.##");
       try {
    	   Number n = df2.parse("0.235");
    	   System.out.println(n.floatValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       DecimalFormat df3  = new DecimalFormat("##.000");

       System.out.println("3位小数："+df3.format(sd));

       System.out.println("2位小数："+df2.format(sd));
       
//       System.out.println(Double.parseDouble("0.00%"));
       
       System.out.println(Integer.MAX_VALUE);
       System.out.println(Long.MAX_VALUE);
       
       DecimalFormat df = new DecimalFormat("###,###.##");
       System.out.println(df.format(13545.23));
       
       System.out.println(Integer.MAX_VALUE);
       System.out.println(Long.MAX_VALUE);
       //8324840627
       //2147483647
       
//       System.out.println(Double.parseDouble("5%"));
       long t1 = 230785L;
       long t2 = 29L;
       System.out.println(t1%t2);
       
       Long fttt = null;
       long dfdf = 1L;
       System.out.println(fttt + dfdf);
    }

    
    
}



