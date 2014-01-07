package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileCombine {
	// ������ļ��ϳ�һ���ļ�������fileNames��һ���ַ������飬���������вμӺϲ��������ļ���ȫ·����TargetFileNameָ���Ǻϳɺ���ļ���ȫ·��
	public static void merge(String[] fileNames, String TargetFileName) throws Exception {
		File fin = null;
		// �����ļ������
		File fout = new File(TargetFileName);
		FileOutputStream out = new FileOutputStream(fout);
		for (int i = 0; i < fileNames.length; i++) {
			// ���ļ�������
			fin = new File(fileNames[i]);
			FileInputStream in = new FileInputStream(fin);
			// ���������ж�ȡ���ݣ���д�뵽�ļ���������
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
		}
		out.close();
		System.out.println("�ϲ��ļ�" + TargetFileName + "�е��������£�");
	}

	public static void readFileMessage(String fileName) {// ���ϳɵ��ļ��е����ݶ���
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String string = null;
			// ���ж�ȡ���ݣ�ֱ������null���ʾ��ȡ�ļ�����
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
		// �ϲ��ļ�
		String[] fileNames = { "D:/tmpfile/1.txt", "D:/tmpfile/2.txt", "D:/tmpfile/3.txt", "D:/tmpfile/4.txt" };
		String newFileName = "D:/tmpfile/total.txt";
		FileCombine.merge(fileNames, newFileName);
		FileCombine.readFileMessage(newFileName);
	}
}