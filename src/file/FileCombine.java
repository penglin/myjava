package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileCombine {
	// 将多个文件合成一个文件，其中fileNames是一个字符串数组，包括了所有参加合并操作的文件的全路径，TargetFileName指的是合成后的文件的全路径
	public static void merge(String[] fileNames, String TargetFileName) throws Exception {
		File fin = null;
		// 构建文件输出流
		File fout = new File(TargetFileName);
		FileOutputStream out = new FileOutputStream(fout);
		for (int i = 0; i < fileNames.length; i++) {
			// 打开文件输入流
			fin = new File(fileNames[i]);
			FileInputStream in = new FileInputStream(fin);
			// 从输入流中读取数据，并写入到文件数出流中
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
		}
		out.close();
		System.out.println("合并文件" + TargetFileName + "中的内容如下：");
	}

	public static void readFileMessage(String fileName) {// 将合成的文件中的内容读出
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String string = null;
			// 按行读取内容，直到读入null则表示读取文件结束
			while ((string = reader.readLine()) != null) {
				System.out.println(string);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void main(final String[] args) throws Exception {
		// 合并文件
		String[] fileNames = { "D:/tmpfile/1.txt", "D:/tmpfile/2.txt", "D:/tmpfile/3.txt", "D:/tmpfile/4.txt" };
		String newFileName = "D:/tmpfile/total.txt";
		FileCombine.merge(fileNames, newFileName);
		FileCombine.readFileMessage(newFileName);
	}
}
