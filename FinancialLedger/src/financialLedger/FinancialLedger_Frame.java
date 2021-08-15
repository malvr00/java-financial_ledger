package financialLedger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class FinancialLedger_Frame extends JFrame{
	private FinancialLedger_DAO db;
	private CalendarClass cal;
	
	private JTextField tf_cal, tf_money, tf_yeartot, tf_monthtot;
	private JTextArea discription;
	private JButton addBt, beforeBt, nextBt;
	private JLabel label1, label2, label3, label4, label5, label6, label7;
	private int x = 20, y = 140;		// Rect 초기값
	private int size = 50;				// Rect Size
	private String year, month, day;
	public FinancialLedger_Frame(FinancialLedger_DAO db) {
		super("용돈기입장");
		this.db = db;
		cal = new CalendarClass();		// 달력 class 생성
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initForm();
	}
	
  // 화면디자인	
	public void initForm() {
		setLayout(null);
		setSize(680,600);
		Container cpane = getContentPane();
	  // 달력 set
		//cal.calSet(2021, 7);
		cal.calGet();
		year = Integer.toString(cal.getYear());
		month = Integer.toString(cal.getMonth());
		
	  // Label
		label1 = new JLabel("날짜");
		label2 = new JLabel("사용금액");
		label3 = new JLabel("사용내역");
		label4 = new JLabel("총 년 사용금액");
		label5 = new JLabel("총 월 사용금액");
		label6 = new JLabel();
		label7 = new JLabel();
	  // Text Field
		tf_cal = new JTextField();
		tf_money = new JTextField();
		tf_yeartot = new JTextField("0원");
		tf_monthtot = new JTextField("0원");
		
		discription = new JTextArea();
		
	  // Button 
		addBt = new JButton("추가");
		beforeBt = new JButton("▼");
		nextBt = new JButton("▲");
		
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
		
	  // Evnet 처리
		beforeBt.addActionListener(new calMoveEvnet());		// 달력 이전
		nextBt.addActionListener(new calMoveEvnet());		// 달력 다음
		addBt.addActionListener(new AddEventhandle());		// 사용내역 추가
		cpane.addMouseListener(new MouseEventHandle());		// 달력 정보 얻기
		
		cpane.add(label1);
		cpane.add(label2);
		cpane.add(label3);
		cpane.add(label4);
		cpane.add(label5);
		cpane.add(label6);
		cpane.add(label7);
		cpane.add(tf_cal);
		cpane.add(tf_money);
		cpane.add(tf_yeartot);
		cpane.add(tf_monthtot);
		cpane.add(scrollPane1);
		cpane.add(addBt);
		cpane.add(beforeBt);
		cpane.add(nextBt);
		repaint();
	}// initForm end
	
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
			}
			cal.calGet();
			repaint();
		}	
	} // calMoveEvnet end
	
	class AddEventhandle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql;
			ResultSet rs;
			
			sql  = "INSERT INTO book SET dYear = " + year + " dMonth = " + month + " dDay = " + day + " nExpense = " + discription.getText() +
					" strUsageHistory = " + tf_money.getText();
			db.Exectue(sql);
			System.out.println(sql);
			//tf_money
		}
	} // AddEventhandle end
	
  // Mouse Event
	class MouseEventHandle extends MouseAdapter{
		private int ndayX, ndayY;	// 화면의 달력 클릭 좌표
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == MouseEvent.BUTTON1) {
				if(e.getX() < 355 && e.getY() < 459 && e.getY() > 160) {
				 // 좌표를 Index화
					ndayX = (e.getX() - 10) / 50;
					ndayY = ((e.getY() - 110) / 50) - 1;
				// 달력 일 얻기
					day = Integer.toString(cal.getDay(ndayX, ndayY));
					tf_cal.setText(year + "-" + month + "-" + day);
					System.out.println(ndayX + "\t" + ndayY + "\tX = " + e.getX() + "Y = " + e.getY());
					System.out.println(day);
				}else {
					System.out.println("범위를 벗아났습니다."); // 임시
				}
			}
		}
	} // MouseEventHandle end
	
	
}
