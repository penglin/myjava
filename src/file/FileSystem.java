package file;

import java.io.File;
import java.util.LinkedList;

public class FileSystem {
	public static int num;

	public static void main(String[] args) {

		long a = System.currentTimeMillis();
		//String path="c:";
		num = 0;
		String[] lists = { "c:" };
		/*
		for(int i=0;i<lists.length;i++){
		 File file=new File(lists[i]);
		 scanDirRecursion(file);
		 */
		for (int i = 0; i < lists.length; i++) {
			//            scanDirNoRecursion(lists[i]);
			scanDirRecursion(new File(lists[i]));
		}

		System.out.print("�ļ�����:" + num);
		System.out.print("�ܺ�ʱ:");
		System.out.println(System.currentTimeMillis() - a);
	}

	//�ǵݹ�
	public static void scanDirNoRecursion(String path) {
		LinkedList list = new LinkedList();
		File dir = new File(path);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else {
				System.out.println(file[i].getAbsolutePath());
				num++;
			}
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = (File) list.removeFirst();//�׸�Ŀ¼
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);//Ŀ¼�����Ŀ¼�б����ؼ�
					else {
						System.out.println(file[i]);
						num++;
					}
				}
			} else {
				System.out.println(tmp);
				num++;
			}
		}
	}

	//�ݹ�   
	public static void scanDirRecursion(File file) {
		try {
			if (file.canRead()) {
				if (file.isDirectory()) {
					String[] files = file.list();
					if (files != null) {
						for (int i = 0; i < files.length; i++) {
							scanDirRecursion(new File(file, files[i]));
						}
					}
				} else {
					//if (file.getName().endsWith("ppt")) 
					System.out.println(file.getAbsolutePath());
					num++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}