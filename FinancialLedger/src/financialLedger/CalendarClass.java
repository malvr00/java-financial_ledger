package financialLedger;

import java.util.*;

public class CalendarClass {
  // Calendar ����
	private Calendar cal;
  // �޷� ����
	private String strcalColumn[] = {"��","��","ȭ","��","��","��","��"};		
  // �޷� ��
	private int nCalDate[][] = new int[5][7];
  
	private int nCalMon;			// �޷� ��
	private int nStartDay;			// �޷� ���� ��
	private int nLastDay;			// �޷� ������ ��
	private int nInputDate = 1;		// �Է� ���� ��¥
	
	public CalendarClass() {
		cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		nStartDay = cal.get(Calendar.DAY_OF_WEEK);
		nLastDay = cal.getActualMaximum(Calendar.DATE);
		nCalMon = cal.get(Calendar.MONTH) + 1;
	}
  // �޷³�¥ ���� 
	public void calSet(int year, int month, int day) {
		
	}
  // �޷� �� set
	public void claGet() {
		int j=0, i;
		for(i = 1; nInputDate<=nLastDay; i++) {
			if(i<nStartDay) {
				nCalDate[j][i] = 0;
			}else {
				nCalDate[j][(i-1)%7] = nInputDate;
				nInputDate++;
				if(i%7==0) {
					j++;
				}
			}
		}
		nInputDate = 1;
	}
  // �޷����
	public void drawCalendar() {
	// *Test ��� (**��������**)
		for(int i = 0; i<strcalColumn.length; i++) {
			System.out.print(strcalColumn[i] + "\t");
		}
		System.out.println();
		for(int i = 0; i<nCalDate.length; i++) {
			for(int j = 0; j<nCalDate[i].length; j++) {
				if(nCalDate[i][j] == 0) {
					System.out.print(" \t");
				}else {
					System.out.print(nCalDate[i][j] + "\t");
				}
			}
			System.out.println();
		}
	}
}
