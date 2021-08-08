package financialLedger;

import java.util.*;
import java.awt.*;

public class CalendarClass {
  // Calendar ����
	private Calendar cal;
  // �޷� ����
	private String strcalColumn[] = {"��","��","ȭ","��","��","��","��"};		
  // �޷� ��
	private int nCalDate[][] = new int[6][7];
	
	private int nCalYear;			// �޷� ��
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
		nCalYear = cal.get(Calendar.YEAR);
	}
	
  // �޷³�¥ ���� 
	public void calSet(int year, int month) {
	  // �޷� set
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
	  // �޷� ��°� set
		nStartDay = cal.get(Calendar.DAY_OF_WEEK);
		nLastDay = cal.getActualMaximum(Calendar.DATE);
		nCalMon = cal.get(Calendar.MONTH) + 1;
		nCalYear = cal.get(Calendar.YEAR);
	} // calSet end
	
  // ���� �޷� �� return
	public int getYear() {
		return nCalYear;
	} // getYear end
	
  // ���� �޷� �� return
	public int getMonth() {
		return nCalMon;
	} // getMonth end
	
  // �޷� �� set
	public void calGet() {
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
	}// claGet end
	
  // �޷����
	public void drawCalendar(int x, int y, Graphics gra) {
		for(int i = 0; i<strcalColumn.length; i++) {
			gra.drawString(strcalColumn[i], x+20+(50*i), y+30);
		}
		System.out.println();
		for(int i = 0; i<nCalDate.length; i++) {
			for(int j = 0; j<nCalDate[i].length; j++) {
				if(nCalDate[i][j] == 0) {
					gra.drawString("\t", x+20+(50*i), y+80);
				}else {
					gra.drawString(Integer.toString(nCalDate[i][j]), x+20+(50*j), y+80+(50*i));
				}
			}
		}
	}// drawCalendar end
}// CalendarClass end
