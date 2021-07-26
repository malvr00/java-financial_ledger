package financialLedger;

import java.util.*;

public class CalendarClass {
  // Calendar 변수
	private Calendar cal;
  // 달력 요일
	private String strcalColumn[] = {"일","월","화","수","목","금","토"};		
  // 달력 일
	private int nCalDate[][] = new int[5][7];
  
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
	}
  // 달력날짜 셋팅 
	public void calSet(int year, int month, int day) {
		
	}
  // 달력 값 set
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
  // 달력출력
	public void drawCalendar() {
	// *Test 출력 (**수정예정**)
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
