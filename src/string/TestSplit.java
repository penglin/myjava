package string;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TestSplit {
	public static final String Split_Char = new String(new char[]{1});
	public static void main(String[] args) {
		String s = "dpcnm9gkow7odk5ds19emrcnp7z4l9d2f505b3f1eqzrayfile-fv40288109397a7bfe01397bdb61ee03501348157215nzmtnw60g3glt3vyaxe4b14028810935d24a620135d27b2ede00794028810935d24a620135d2932a93009c110.242.113.8¡™Õ®∫”±± ° Øº“◊Ø –1340202510uovg39gl6xc0zfvz8kpc09rayfile*qzrayfile-fvhttp://www.rayfile.com/files/fb0ed7b0-ff7b-11e1-a736-0015c55db73d/%20+?Cy8Ev?13481572146152Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 691; .NET CLR 2.0.50727)end";
		String s1 = "do33gpm5aix0svh7ol3epreotegriw689505b3f1eqzgmw-fv1348157214mtqw11o6c9752wiy0mli61114.238.156.5µÁ–≈ADSLΩ≠À’ °ª¥∞≤ –1344864415hzs1hgqi26k5yrlfncyuc4gmw*qzgmw-fvhttp://e.gmw.cn/2012-09/20/content_5149351.htm13481572143140Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)end";
		s.split(Split_Char);
		System.out.println(s.split(Split_Char).length);
		System.out.println(s1.split(Split_Char).length);
		System.out.println("1348156798855".length());
		
		String s2 = "d0fn0ha4cyzb1y5ughu5p7gupzuw99n54505c695adiscuz_3362843_0021348233563fyef6sbpv6gm2gm46d9434220.246.44.162œ„∏€adx*discuz_3362843_002http://bbs.txtbbs.com/forum.php?mod=viewthread&tid=251459&fromuid=404565613482335625190Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; NP06)end";
		String[] ss = s2.split(Split_Char);
		System.out.println(s2.split(Split_Char).length);
		
		String hh = "d0fn0ha4cyzb1y5ughu5p7gupzuw99n54505c695adiscuz_3362843_0021348233563fyef6sbpv6gm2gm46d9434220.246.44.162œ„∏€adx*discuz_3362843_002http://bbs.txtbbs.com/forum.php?mod=viewthread&tid=251459&fromuid=4045656"
				+"\n13482335625190Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; NP06)end";
		System.out.println(hh);;
		System.out.println(hh.replaceAll("\n", ""));;
		
		try {
			System.out.println(URLDecoder.decode("VideoDemand_tsp8bdlu486c122b1k6hd1491fya1rbd509721ca%241352081866000%24p8prkgwsnwrqg54lp7mfgrwug62ywv9a.log","GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ddd = "fsdfsdfsdfsdfewfsd"+Split_Char+"jfsdlkfjlsdflsdlf"+"fdsjlfsjdlfjdslfjds";
		String ddd2 = Split_Char+"_precise_"+Split_Char;
		String ddd3 = ""+Split_Char+"";
		String tmp = ddd + ddd2 + ddd3;
		String[] sss = tmp.split(ddd2);
		System.out.println(sss.length);
		for(String t : sss){
			System.out.println(t);
		}
		
		String douhao = "jfdsjflsd,kdfjslfjdslkfjsldfj";
		System.out.println(douhao.split(",").length);
	}
}
