package string;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class SQL {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("d.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		FileOutputStream out = new FileOutputStream("out.txt");
		String value = null;
		while((value=br.readLine())!=null){
			String[] arr = value.split(",");
			if(!"0".equals(arr[2])){
				String sql = "insert into video_play_402881062b82a2a3012b89aace5d33c6 select top "+arr[2]+" * from video_play_402881062b82a2a3012b89aace5d33c6 where publish_video_id='"+arr[0]+"' and workstation_id='"+arr[1]+"' and video_demand_day='2010年09月30日' and Is_Valid_Play!=1";
				out.write(sql.getBytes());
				out.write("\n".getBytes());
				System.out.println(sql);
			}
			if(!"0".equals(arr[3])){
				String sql = "update video_play_402881062b82a2a3012b89aace5d33c6 set video_match_type=2 where publish_video_id='"+arr[0]+"' and  workstation_id='"+arr[1]+"' and video_demand_day='2010年09月30日'";
				out.write(sql.getBytes());
				out.write("\n".getBytes());
				System.out.println(sql);
			}
		}
		out.flush();
		br.close();
		out.close();
	}

}
