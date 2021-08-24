package financialLedger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FinancialLedger_Frame extends JFrame{
	private FinancialLedger_DAO db;
	private CalendarClass cal;
	private DlgDetail dlg2; // 사용내역 상세보기용
	private JDialog dlg1;   // 일일 사용내역 확인용
	private JTextField tf_cal, tf_money, tf_yeartot, tf_monthtot;
	private JTextArea discription;
	private JButton addBt, beforeBt, nextBt, detailBt;
	private JComboBox <String> categoryBox;
	
  // 1001 식대, 1002 교통비, 1003 생활비, 1004 수입
	private String dataModel[] = {"식대", "교통비", "생활비", "수입"};		
	
	private JLabel label1, label2, label3, label4, label5, label6, label7, label8;
	private int x = 20, y = 140;			// Rect 초기값
	private int size = 50;					// Rect Size
	private String year, month, day;		// 년, 월, 일 
	private String yeartotal, monthtotal;	// 년 총 금액, 월 총 금액
	public FinancialLedger_Frame(FinancialLedger_DAO db) {
		super("용돈기입장");
		this.db = db;
		cal = new CalendarClass();		// 달력 class 생성
	  // 달력 set
		cal.calGet();
		year = Integer.toString(cal.getYear());
		month = Integer.toString(cal.getMonth());
		
		totalset();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initForm();
	}
	
  // 화면디자인	
	public void initForm() {
		setLayout(null);
		setSize(680,600);
		Container cpane = getContentPane();
		
	  // Label
		label1 = new JLabel("날짜");
		label2 = new JLabel("사용금액");
		label3 = new JLabel("사용내역");
		label4 = new JLabel("총 년 사용금액");
		label5 = new JLabel("총 월 사용금액");
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel("카테고리");
	  // Text Field
		tf_cal = new JTextField();
		tf_money = new JTextField();
		tf_monthtot = new JTextField(monthtotal + "원");					
		tf_yeartot = new JTextField(yeartotal + "원");
		
		discription = new JTextArea();
		
	  // ComboBox
		categoryBox = new JComboBox<String>(new DefaultComboBoxModel<String>(dataModel));
		
	  // Button 
		addBt = new JButton("추가");
		beforeBt = new JButton("▼");
		nextBt = new JButton("▲");
		detailBt = new JButton("상세보기");
		
	  // Text Field 사용여부
		tf_cal.setEnabled(false);
		tf_yeartot.setEnabled(false);
		tf_monthtot.setEnabled(false);
		
		tf_yeartot.setHorizontalAlignment(JTextField.RIGHT);
		tf_monthtot.setHorizontalAlignment(JTextField.RIGHT);
		
		
	  // Scroll 설정
		discription.setLineWrap(true);
		JScrollPane scrollPane1 = new JScrollPane(discription);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		
	    
		label1.setBounds(380, 95, 60, 60);
		tf_cal.setBounds(445, 110, 200 ,30);
		
		label2.setBounds(380, 150, 60, 60);
		tf_money.setBounds(445, 165, 200, 30);
		
		label3.setBounds(380, 195, 60, 60);
		scrollPane1.setBounds(445,210, 200, 200);
		
		label4.setBounds(10,470, 80, 60);
		tf_yeartot.setBounds(110,485,200, 30);
		
		label5.setBounds(350,470, 80, 60);
		tf_monthtot.setBounds(450,485,200, 30);
		
		addBt.setBounds(585, 425, 60, 20);
		label6.setText(year);
		label6.setBounds(x,y-80,60,60);
		label7.setText(month + "월");
		label7.setBounds(x+40,y-80, 60, 60);
		
		beforeBt.setBounds(x+240, y-60,50,20);
		nextBt.setBounds(x+295, y-60,50,20);
		detailBt.setBounds(445, 425, 90, 20);
		
		label8.setBounds(380, y-80, 60, 60);
		categoryBox.setBounds(565, y-65, 80, 30);
		
	  // Event 처리
		beforeBt.addActionListener(new calMoveEvnet());		// 달력 이전
		nextBt.addActionListener(new calMoveEvnet());		// 달력 다음
		addBt.addActionListener(new AddEventhandle());		// 사용내역 추가
		detailBt.addActionListener(new viewsEvent());		// 사용내역 상세보기
		cpane.addMouseListener(new MouseEventHandle());		// 달력 정보 얻기
		
		cpane.add(label1);
		cpane.add(label2);
		cpane.add(label3);
		cpane.add(label4);
		cpane.add(label5);
		cpane.add(label6);
		cpane.add(label7);
		cpane.add(label8);
		cpane.add(tf_cal);
		cpane.add(tf_money);
		cpane.add(tf_yeartot);
		cpane.add(tf_monthtot);
		cpane.add(scrollPane1);
		cpane.add(addBt);
		cpane.add(beforeBt);
		cpane.add(nextBt);
		cpane.add(detailBt);
		cpane.add(categoryBox);
		repaint();
	}// initForm end
	
  // Dialog ( 쪽지형식 Dlg )
	public void dlgDesign() {
		ResultSet rs;
		int expense;			// 사용금액
		String usageHistory;	// 사용내역
		String sql;				// Query
		int cnt = seachDay();	// 일일 내역 Count

		Panel pan = new Panel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
	  // Dialog 생성
		dlg1 = new JDialog(this, "일일사용내역", true);
		dlg1.add(new JLabel(year + "년 " + month + "월 " + day + "일 "), "North");
		
	 // ********************************* 사용금액 및 사용용도 불러오기 ( Select ) ********************************* // 
		sql = "SELECT * FROM book WHERE dYear='" + year + "' AND dMonth='" + month + "' AND dDay='" + day + "' ORDER BY nExpense asc";
		rs = db.getResultSet(sql);
		
		try {
		  // ********************** 사용내역 출력부분 ********************** //
			if(cnt == 0) { 	// 지출 내역이 없을 경우
				pan.add(new JLabel("지출 내역이 없습니다."));
			}else {			// 지출 내역이 있는 경우
				for(int i=1; i<=cnt; i++) {
					rs.next();
					expense = rs.getInt("nExpense");
					usageHistory = rs.getString("strUsageHistory");
					pan.add(new JLabel("사용금액 : " + Integer.toString(expense) + "원 | " + "사용내역 : " + usageHistory));
				}				
			}
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
		
	  // 마우스 위치에 띄우기
		dlg1.setLocation(getMousePosition());
		
		dlg1.add(pan,"Center");
		dlg1.pack();
		dlg1.setVisible(true);
	} // dlgDesign end
	
// 상세보기 Dlg
	public void dlgDetails() {
		dlg2 = new DlgDetail(this, db, "상세보기");
		dlg2.dlginitForm();
	} // dlgDetails end
	
// ************************ Count Query **********************//
 // 일일 사용내역 개수 Count
	public int seachDay() {
		ResultSet rs;
		ResultSetMetaData meta;
		
		String sql;			// Query 
		int colCnt = 0;		// 일일 등록 갯수
		
	  // ************ Select Count ************ //
		sql = "SELECT COUNT(*) as count FROM book "
				+ "WHERE dYear='" + year + "' AND dMonth='" + month + "' AND dDay='" + day + "'" ;
		rs = db.getResultSet(sql);
		
		try {
			rs.next();
			meta = rs.getMetaData();
		  // 구한 개수 대입
			colCnt = rs.getInt(meta.getColumnName(1));		
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
		return colCnt;
	} // seachDay end
	
// ************************ 사용 총 금액 Query ************************ //
	public String totalamount(String sw) {
		ResultSet rs;
		ResultSetMetaData meta;
		
		String sql, total = null;
	  // ************ Select Sum | nExpense Total ************ //
		if(sw == "y") // y 입력받을 시 년 총액 계산
			sql = "SELECT sum(nExpense) as total FROM book WHERE dYear='" + year + "'";
		else 
			sql = "SELECT sum(nExpense) as total FROM book WHERE dMonth='" + month + "'";
	  
		
		rs = db.getResultSet(sql);
		
		try {
			rs.next();
			meta = rs.getMetaData();
			total = rs.getString(meta.getColumnName(1));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return total;
	}
  // 달력 Frame
	public void drawRects(Graphics gra) {
		for(int row=0; row<7; row++) {
			for(int col=0; col<7; col++) {
				gra.drawRect(x+(col*size), y+(row*size), size, size);
			}
		}
	}// drawRects end
	
	public void paint(Graphics g) {
		super.paint(g);
		drawRects(g);
		cal.drawCalendar(x,y,g);		// 달력 Draw
	}// paint end
	
  // Button Event 
	class calMoveEvnet implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int nyear = Integer.parseInt(year);
			int nmonth = Integer.parseInt(month);
			if(e.getActionCommand() == "▼") {
				nmonth--;
				
				if(nmonth<1) {		// 1월 이하로 내려가면 12월로 변경 && 이전 해로
					nmonth = 12;
					nyear--;
				}
				cal.calSet(nyear, nmonth);
				
				year = Integer.toString(nyear);
				month = Integer.toString(nmonth);
				label6.setText(year);
				label7.setText(month + "월");
				
				totalset();
				tf_monthtot.setText(monthtotal + "원");
				tf_yeartot.setText(yeartotal + "원");
			}else {
				nmonth++;
				
				if(nmonth>12) {		// 12월 초과하면 1월로 변경 && 다음 해로
					nmonth = 1;
					nyear++;
				}
				
				cal.calSet(nyear, nmonth);
				
				year = Integer.toString(nyear);
				month = Integer.toString(nmonth);
				label6.setText(year);
				label7.setText(month + "월");
				
				totalset();
				tf_monthtot.setText(monthtotal + "원");
				tf_yeartot.setText(yeartotal + "원");
			}
			cal.calGet();
			repaint();
		}	
	} // calMoveEvnet end
	
	class AddEventhandle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql;
			int categoryNo;
		// ************ 입력받은 날 사용 금액 & 사용용도 ( Insert ) ************ // 
			if(discription.getText().equals("")) {	// 입력된값 없으면 error
				System.out.println("입력누락");	// 임시
			}else {
				categoryNo = categoryChange();
				sql  = "INSERT INTO book (dYear, dMonth, dDay, nExpense, strUsageHistory, category) VALUES('"+ year + "', '" + month + "', '" + day + "', " 
						+ Integer.parseInt(tf_money.getText()) + ", '" + discription.getText() + "', " + categoryNo + ")";
				db.Exectue(sql);
				totalset();
				tf_monthtot.setText(monthtotal + "원");
				tf_yeartot.setText(yeartotal + "원");
			}
			
		// Text reset
			tf_money.setText("");
			discription.setText("");
		}
	} // AddEventhandle end
	
  // dlg 상세보기 Button Event
	class viewsEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dlgDetails();		// 상세보기 창 띄우기
		}
	}
	
  // Mouse Event
	class MouseEventHandle extends MouseAdapter{
		private int ndayX, ndayY;	// 화면의 달력 클릭 좌표
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {	// ** Mouse Left Click
				if(e.getX() < 355 && e.getY() < 459 && e.getY() > 160) {
				 // 좌표를 Index화
					ndayX = (e.getX() - 10) / 50;
					ndayY = ((e.getY() - 110) / 50) - 1;
				// 달력 일 얻기
					day = Integer.toString(cal.getDay(ndayX, ndayY));
					tf_cal.setText(year + "-" + month + "-" + day);
				}else {
					System.out.println("범위를 벗아났습니다."); // 임시
					repaint();
				}
			}else if(e.getButton() == MouseEvent.BUTTON3){ // ** Mouse right Click
				dlgDesign();
			}
		}
	} // MouseEventHandle end

  // 년 & 월 총 지출내역 Set
	public void totalset() {
		yeartotal = (totalamount("y") != null)?totalamount("y"): "0";
		monthtotal = (totalamount("m") != null)?totalamount("m"):"0";
	}// totalset end
	
 // 카테고리 코드화
	public int categoryChange() {
		// 1001 식대, 1002 교통비, 1003 생활비, 1004 수입
		int cateNo = categoryBox.getSelectedIndex();		// Category Combo Box Index
		int category = 0;
		switch(cateNo) {
			case 0:		// 식대
				category = 1001;
				break;
			case 1:		// 교통비
				category = 1002;
				break;
			case 2:		// 생활비
				category = 1003;
				break;
			case 3:		// 수입
				category = 1004;
				break;
		}
		
		return category;
	} // categoryChange
}
