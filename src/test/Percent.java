package test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Percent {
	public static void main(String[] args) {
//		int[] arr = go();
//		System.out.println(arr);
//		for (int tmp : arr) {
//			System.out.println(tmp);
//		}
//		System.out.println("----------------");
//		go2();
		
		DecimalFormat df = new DecimalFormat("####.##");
		System.out.println(df.format(0));
		
	}

	private static int[] getRandomArray(int size) {
		int[] percent = null;
		if (size > 0) {
			percent = new int[size];
			if (1 == size) {
				percent[0] = 100;

			} else {
				int i = 0;
				int num = 100;
				while (true) {
					percent[i] = new Random().nextInt(101);
					num -= percent[i];
					if (num == 0) {
						break;
					} else {
						if ((num < 0) || (i == size - 1)) {
							percent[i] += num;
							break;
						}
					}
					i++;
				}
			}
		}
		return percent;
	}

	public static int[] go() {
		Random r = new Random();
		List<Integer> list = new ArrayList<Integer>();
		int sum = 0;
		while (true) {
			int t = 0;
			while (t == 0) {
				t = r.nextInt(100);
			}
			sum += t;
			if (sum < 100) {
				list.add(t);
			}
			if (sum == 100) {
				list.add(t);
				int[] a = new int[list.size()];
				for (int i = 0; i < a.length; i++) {
					a[i] = list.get(i);
				}
				return a;
			}
			if (sum > 100) {
				sum -= t;
			}
		}
	}

	public static void compute(int[] data, int sum) {
		boolean[] state = new boolean[data.length];
		int p = 0;
		int temp = 0;
		while (true) {
			while (p != data.length) {
				if (!state[p]) {
					state[p] = true;
					temp += data[p];
					if (temp == sum) {
						for (int i = 0; i < data.length; i++) {
							if (state[i]) {
								System.out.print(data[i] + " ");
							}
						}
						return;
					}
					if (temp > sum) {
						state[p] = false;
						temp -= data[p];
					}
				}
				p++;
			}
			while (state[p - 1]) {
				state[p - 1] = false;
				temp -= data[p - 1];
				p--;
				if (p == 0) {
					return;
				}
			}
			while (!state[p - 1]) {
				p--;
				if (p == 0) {
					return;
				}
			}
			state[p - 1] = false;
			temp -= data[p - 1];
		}
	}

	public static void go2() {
		Random r = new Random();
		int[] data = new int[100];
		for (int i = 0; i < data.length; i++) {
			int t = 0;
			while (t == 0) {
				t = r.nextInt(100);
			}
			data[i] = t;
		}
		compute(data, 100);
	}

}
