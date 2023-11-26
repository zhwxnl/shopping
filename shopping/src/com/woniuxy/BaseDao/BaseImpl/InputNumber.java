package com.woniuxy.BaseDao.BaseImpl;

import java.util.Scanner;

public class InputNumber {
	private static Scanner sc = new Scanner(System.in);
		// ��ȡָ����Χ��ֵ
	private static <T, U> U getNum(T min, T max, Class<U> c) {
		while (true) {
			
			try {
				U n = null;
				double nn = 0;
				double mi = Double.valueOf(min.toString());
				double ma = Double.valueOf(max.toString());
				if (min.getClass() == Integer.class) {
					Integer num = Integer.valueOf(sc.nextInt());
					nn = (double) num;
					n = c.cast(num);
				}
				if (min.getClass() == Double.class) {
					Double num = Double.valueOf(sc.nextDouble());
					nn = num;
					n = c.cast(num);
				}
				if (!(mi==0&& ma==0)) {
					if (nn >= mi && nn <= ma) {
						return n;
					}
					System.out.println("���������밴Ҫ�����룡");
					continue;
				}
				return n;
			} catch (Exception e) {
				System.out.println("�����������������֣�");
				sc = new Scanner(System.in);
			}
		}
	}

	public static int getNum(int min, int max) {
		return getNum(min, max, Integer.class);
	}
	//��ȡint��ֵ
	public static int getIntNum() {
		return getNum(0, 0, Integer.class);
	}

	//��ȡdouble��ֵ
	public static double getDoubleNum() {
		return getNum(0.0, 0.0, Double.class);
	}

}
