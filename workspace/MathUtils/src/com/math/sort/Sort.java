package com.math.sort;

public class Sort {

	/*
	 * ================================================ ���ܣ�ѡ������
	 * ���룺�������ƣ�Ҳ���������׵�ַ����������Ԫ�ظ���
	 * ================================================
	 */
	/*
	 * ==================================================== �㷨˼���������
	 * 
	 * ��Ҫ�����һ�����У�ѡ����С��һ�������һ��λ�õ��������� Ȼ����ʣ�µ�������������С����ڶ���λ�õ������������ѭ��
	 * �������ڶ����������һ�����Ƚ�Ϊֹ��
	 * 
	 * ѡ�������ǲ��ȶ��ġ��㷨���Ӷ�O(n2)--[n��ƽ��]
	 * =====================================================
	 */
	void select_sort(int[] x, int n) {
		int i, j, min, t;

		for (i = 0; i < n - 1; i++) /* Ҫѡ��Ĵ�����0~n-2��n-1�� */
		{
			min = i; /* ���赱ǰ�±�Ϊi������С���ȽϺ��ٵ��� */
			for (j = i + 1; j < n; j++)/* ѭ���ҳ���С�������±����ĸ� */
			{
				if (x[j] < x[min]) {
					min = j; /* ������������ǰ���С������������±� */
				}
			}

			if (min != i) /* ���min��ѭ���иı��ˣ�����Ҫ�������� */
			{
				t = x[i];
				x[i] = x[min];
				x[min] = t;
			}
		}
	}

	/*
	 * ================================================ ���ܣ�ֱ�Ӳ�������
	 * ���룺�������ƣ�Ҳ���������׵�ַ����������Ԫ�ظ���
	 * ================================================
	 */
	/*
	 * ==================================================== �㷨˼���������
	 * 
	 * ��Ҫ�����һ�����У�����ǰ��(n-1) [n>=2] �����Ѿ����� ��˳��ģ�����Ҫ�ѵ�n�����嵽ǰ����������У�ʹ����n����
	 * Ҳ���ź�˳��ġ���˷���ѭ����ֱ��ȫ���ź�˳��
	 * 
	 * ֱ�Ӳ����������ȶ��ġ��㷨ʱ�临�Ӷ�O(n2)--[n��ƽ��]
	 * =====================================================
	 */
	void insert_sort(int[] x, int n) {
		int i, j, t;

		for (i = 1; i < n; i++) /* Ҫѡ��Ĵ�����1~n-1��n-1�� */
		{
			/*
			 * �ݴ��±�Ϊi������ע�⣺�±��1��ʼ��ԭ����ǿ�ʼʱ ��һ�������±�Ϊ0������ǰ��û���κ���������һ������Ϊ �����ź�˳��ġ�
			 */
			t = x[i];
			for (j = i - 1; j >= 0 && t < x[j]; j--) /*
													 * ע�⣺j=i-1��j--����������±�Ϊi������
													 * ����ǰ�����������Ҳ���λ�á�
													 */
			{
				x[j + 1] = x[j]; /*
								 * �����������������Ų������������t���±�Ϊ0������С����Ҫ������ǰ�棬j==-1���˳�ѭ��
								 */
			}

			x[j + 1] = t; /* �ҵ��±�Ϊi�����ķ���λ�� */
		}
	}

	/*
	 * ================================================ ���ܣ�ð������
	 * ���룺�������ƣ�Ҳ���������׵�ַ����������Ԫ�ظ���
	 * ================================================
	 */
	/*
	 * ==================================================== �㷨˼���������
	 * 
	 * ��Ҫ�����һ�����У��Ե�ǰ��δ�ź���ķ�Χ�ڵ�ȫ���������� ���¶����ڵ����������ν��бȽϺ͵������ýϴ�������³�����
	 * С������ð������ÿ�������ڵ����ȽϺ������ǵ�����������Ҫ ���෴ʱ���ͽ����ǻ�����
	 * 
	 * ������һ�ָĽ���ð���㷨������¼��ÿһ��ɨ�������³����� λ��k���������Լ������ѭ��ɨ��Ĵ�����
	 * 
	 * ð���������ȶ��ġ��㷨ʱ�临�Ӷ�O(n2)--[n��ƽ��]
	 * =====================================================
	 */

	void bubble_sort(int[] x, int n) {
		int j, k, h, t;

		for (h = n - 1; h > 0; h = k) /* ѭ����û�бȽϷ�Χ */
		{
			for (j = 0, k = 0; j < h; j++) /* ÿ��Ԥ��k=0��ѭ��ɨ������k */
			{
				if (x[j] > x[j + 1]) /* ��ķ��ں��棬С�ķŵ�ǰ�� */
				{
					t = x[j];
					x[j] = x[j + 1];
					x[j + 1] = t; /* ��ɽ��� */
					k = j; /* ��������³���λ�á�����k����Ķ��������ź��˵ġ� */
				}
			}
		}
	}

	void bubble_sort_wjx(int[] x, int n) {
		int j, h, t;
		boolean sortting = true;

		for (h = n - 2; sortting && h > 0; h++) /* ѭ����û�бȽϷ�Χ */
		{
			for (j = 0, sortting = false; j < h; j++) /* ÿ��Ԥ��k=0��ѭ��ɨ������k */
			{
				if (x[j] > x[j + 1]) /* ��ķ��ں��棬С�ķŵ�ǰ�� */
				{
					t = x[j];
					x[j] = x[j + 1];
					x[j + 1] = t; /* ��ɽ��� */
					sortting = true; /* ��Ч������������Ҳ������ŷ��ɡ� */
				}
			}
		}
	}

	/*
	 * ================================================ ���ܣ�ϣ������
	 * ���룺�������ƣ�Ҳ���������׵�ַ����������Ԫ�ظ���
	 * ================================================
	 */
	/*
	 * ==================================================== �㷨˼���������
	 * 
	 * ��ֱ�Ӳ��������㷨�У�ÿ�β���һ������ʹ��������ֻ����1���ڵ㣬 ���ҶԲ�����һ����û���ṩ�κΰ���������Ƚ������Զ���루��Ϊ
	 * ������������ʹ�����ƶ�ʱ�ܿ�����Ԫ�أ������һ�αȽϾͿ������� ���Ԫ�ؽ�����D.L.shell��1959�����������������������㷨��ʵ��
	 * ����һ˼�롣�㷨�Ƚ�Ҫ�����һ������ĳ������d�ֳ������飬ÿ���� ��¼���±����d.��ÿ����ȫ��Ԫ�ؽ�������Ȼ������һ����С������
	 * �������У���ÿ�����ٽ������򡣵���������1ʱ������Ҫ����������ֳ� һ�飬������ɡ�
	 * 
	 * ����ĺ�����һ��ϣ�������㷨��һ��ʵ�֣�����ȡ���е�һ��Ϊ������ �Ժ�ÿ�μ��룬ֱ������Ϊ1��
	 * 
	 * ϣ�������ǲ��ȶ��ġ� =====================================================
	 */
	void shell_sort(int[] x, int n) {
		int h, j, k, t;

		for (h = n / 2; h > 0; h = h / 2) /* �������� */
		{
			for (j = h; j < n; j++) /* ���ʵ���Ͼ��������ֱ�Ӳ������� */
			{
				t = x[j];
				for (k = j - h; (k >= 0 && t < x[k]); k -= h) {
					x[k + h] = x[k];
				}
				x[k + h] = t;
			}
		}
	}

}