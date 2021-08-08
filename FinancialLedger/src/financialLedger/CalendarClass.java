package financialLedger;

import java.util.*;
import java.awt.*;

public class CalendarClass {
  // Calendar 변수
	private Calendar cal;
  // 달력 요일
	private String strcalColumn[] = {"일","월","화","수","목","금","토"};		
  // 달력 일
	private int nCalDate[][] = new int[6][7];
	
	private int nCalYear;			// 달력 년
	private int nCalMon;			// 달력 월
	private int nStartDay;			// 달력 시작 일
	private int nLastDay;			// 달력 마지막 일
	private int nInputDate = 1;		// 입력 받은 날짜
	
	public CalendarClass() {
		cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		nStartDay = cal.get(Calendar.DAY_OF_WEEK);
		nLastDay = cal.getActualMaximum(Calendar.DATE);
		nCalMon = cal.get(Calendar.MONTH) + 1;
		nCalYear = cal.get(Calendar.YEAR);
	}
	
  // 달력날짜 셋팅 
	public void calSet(int year, int month) {
	  // 달력 set
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
	  // 달력 출력값 set
		nStartDay = cal.get(Calendar.DAY_OF_WEEK);
		nLastDay = cal.getActualMaximum(Calendar.DATE);
		nCalMon = cal.get(Calendar.MONTH) + 1;
		nCalYear = cal.get(Calendar.YEAR);
	} // calSet end
	
  // 현재 달력 년 return
	public int getYear() {
		return nCalYear;
	} // getYear end
	
  // 현재 달력 월 return
	public int getMonth() {
		return nCalMon;
	} // getMonth end
	
  // 달력 값 set
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
	
  // 달력출력
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
