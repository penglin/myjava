package test;

/**  
 * @author ×Óµ¯¸ç  
 *   
 */  
public class TestRef {   
  
    public static void main(String[] args) {   
        StringBuffer a = new StringBuffer("a");   
        StringBuffer b = new StringBuffer("b");   
        append(a, b);   
        System.out.println(a.toString() + "," + b.toString());   
        b = a;   
        System.out.println(a.toString() + "," + b.toString());   
        
        int a1 = 5;
        int b1 = 6;
        change(a1,b1);
        System.out.println(a1 +"--"+ b1);
        
        System.out.println("SpecialAreaZone.TrackAdPos = 402881092bb83992012bb900660b0061-402881092bb83992012bb8f930860051\\:\\u00B9\\u00E3\\u00B6\\u00AB\\u00CA\\u00A1;\\u00B9\\u00E3\\u00D6\\u00DD\\u00CA\\u00D0,402881092bb83992012bb900660b0061-402881092bb83992012bb8f98e170054\\:\\u00B9\\u00E3\\u00B6\\u00AB\\u00CA\\u00A1;\\u00B9\\u00E3\\u00D6\\u00DD\\u00CA\\u00D0,402881092bb83992012bb900660b0061-402881092bb83992012bb8f9fb390057\\:\\u00B9\\u00E3\\u00B6\\u00AB\\u00CA\\u00A1;\\u00B9\\u00E3\\u00D6\\u00DD\\u00CA\\u00D0");
        
        System.out.println("djflsjflsdj,-".split(",")[1]);
    }   
  
    public static void append(StringBuffer a, StringBuffer b) {   
        a.append(b);   
        b = a;   
        
        b.append("dd");
    }   
    
    public static void change(int a,int b){
    	int tmp;
    	tmp = a;
    	a = b;
    	b = tmp;
    }
}  