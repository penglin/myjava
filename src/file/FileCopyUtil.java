package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileCopyUtil {

	private int state = 0;
	private String basePath;
	private int count = 0;

	/**
	 * 通过文件路径拷贝文件
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFileFromFilePath(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (state == 0) {
				basePath = oldPath;
				state = 1;
			}
			File oldFile = new File(oldPath);
			// 判断是否为文件夹
			if (oldFile.isDirectory()) {
				File[] files = oldFile.listFiles();
				for (File file : files) {
					// System.out.println(file.getPath());
					this.copyFileFromFilePath(file.getPath(), newPath);
				}
				return;
			}

			// 编写新文件目标地址
			String newFileName = newPath + oldFile.getPath().substring(basePath.length());
			File newFile = new File(newFileName);
			newFile.getParentFile().mkdirs();
			// 文件不存在创建
			if (!newFile.exists())
				newFile.createNewFile();
			InputStream inStream = new FileInputStream(oldPath);
			FileOutputStream fs = new FileOutputStream(newFile, false);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			// 打印
			System.out.println(++count + " 源:[" + oldPath + "],目标:[" + newFileName + "].复制完成.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FileCopyUtil().copyFileFromFilePath(args[0], args[1]);
		// new FileCopyUtil().copyFileFromFilePath("E:\\sql script\\", "\\\\192.168.0.164\\gongxiang\\");
	}

}
