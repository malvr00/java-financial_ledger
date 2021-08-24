package financialLedger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FinancialLedger_Frame extends JFrame{
	private FinancialLedger_DAO db;
	private CalendarClass cal;
	private DlgDetail dlg2; // ��볻�� �󼼺����
	private JDialog dlg1;   // ���� ��볻�� Ȯ�ο�
	private JTextField tf_cal, tf_money, tf_yeartot, tf_monthtot;
	private JTextArea discription;
	private JButton addBt, beforeBt, nextBt, detailBt;
	private JComboBox <String> categoryBox;
	
  // 1001 �Ĵ�, 1002 �����, 1003 ��Ȱ��, 1004 ����
	private String dataModel[] = {"�Ĵ�", "�����", "��Ȱ��", "����"};		
	
	private JLabel label1, label2, label3, label4, label5, label6, label7, label8;
	private int x = 20, y = 140;			// Rect �ʱⰪ
	private int size = 50;					// Rect Size
	private String year, month, day;		// ��, ��, �� 
	private String yeartotal, monthtotal;	// �� �� �ݾ�, �� �� �ݾ�
	public FinancialLedger_Frame(FinancialLedger_DAO db) {
		super("�뵷������");
		this.db = db;
		cal = new CalendarClass();		// �޷� class ����
	  // �޷� set
		cal.calGet();
		year = Integer.toString(cal.getYear());
		month = Integer.toString(cal.getMonth());
		
		totalset();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initForm();
	}
	
  // ȭ�������	
	public void initForm() {
		setLayout(null);
		setSize(680,600);
		Container cpane = getContentPane();
		
	  // Label
		label1 = new JLabel("��¥");
		label2 = new JLabel("���ݾ�");
		label3 = new JLabel("��볻��");
		label4 = new JLabel("�� �� ���ݾ�");
		label5 = new JLabel("�� �� ���ݾ�");
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel("ī�װ�");
	  // Text Field
		tf_cal = new JTextField();
		tf_money = new JTextField();
		tf_monthtot = new JTextField(monthtotal + "��");					
		tf_yeartot = new JTextField(yeartotal + "��");
		
		discription = new JTextArea();
		
	  // ComboBox
		categoryBox = new JComboBox<String>(new DefaultComboBoxModel<String>(dataModel));
		
	  // Button 
		addBt = new JButton("�߰�");
		beforeBt = new JButton("��");
		nextBt = new JButton("��");
		detailBt = new JButton("�󼼺���");
		
	  // Text Field ��뿩��
		tf_cal.setEnabled(false);
		tf_yeartot.setEnabled(false);
		tf_monthtot.setEnabled(false);
		
		tf_yeartot.setHorizontalAlignment(JTextField.RIGHT);
		tf_monthtot.setHorizontalAlignment(JTextField.RIGHT);
		
		
	  // Scroll ����
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
		label7.setText(month + "��");
		label7.setBounds(x+40,y-80, 60, 60);
		
		beforeBt.setBounds(x+240, y-60,50,20);
		nextBt.setBounds(x+295, y-60,50,20);
		detailBt.setBounds(445, 425, 90, 20);
		
		label8.setBounds(380, y-80, 60, 60);
		categoryBox.setBounds(565, y-65, 80, 30);
		
	  // Event ó��
		beforeBt.addActionListener(new calMoveEvnet());		// �޷� ����
		nextBt.addActionListener(new calMoveEvnet());		// �޷� ����
		addBt.addActionListener(new AddEventhandle());		// ��볻�� �߰�
		detailBt.addActionListener(new viewsEvent());		// ��볻�� �󼼺���
		cpane.addMouseListener(new MouseEventHandle());		// �޷� ���� ���
		
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
	
  // Dialog ( �������� Dlg )
	public void dlgDesign() {
		ResultSet rs;
		int expense;			// ���ݾ�
		String usageHistory;	// ��볻��
		String sql;				// Query
		int cnt = seachDay();	// ���� ���� Count

		Panel pan = new Panel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
	  // Dialog ����
		dlg1 = new JDialog(this, "���ϻ�볻��", true);
		dlg1.add(new JLabel(year + "�� " + month + "�� " + day + "�� "), "North");
		
	 // ********************************* ���ݾ� �� ���뵵 �ҷ����� ( Select ) ********************************* // 
		sql = "SELECT * FROM book WHERE dYear='" + year + "' AND dMonth='" + month + "' AND dDay='" + day + "' ORDER BY nExpense asc";
		rs = db.getResultSet(sql);
		
		try {
		  // ********************** ��볻�� ��ºκ� ********************** //
			if(cnt == 0) { 	// ���� ������ ���� ���
				pan.add(new JLabel("���� ������ �����ϴ�."));
			}else {			// ���� ������ �ִ� ���
				for(int i=1; i<=cnt; i++) {
					rs.next();
					expense = rs.getInt("nExpense");
					usageHistory = rs.getString("strUsageHistory");
					pan.add(new JLabel("���ݾ� : " + Integer.toString(expense) + "�� | " + "��볻�� : " + usageHistory));
				}				
			}
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
		
	  // ���콺 ��ġ�� ����
		dlg1.setLocation(getMousePosition());
		
		dlg1.add(pan,"Center");
		dlg1.pack();
		dlg1.setVisible(true);
	} // dlgDesign end
	
// �󼼺��� Dlg
	public void dlgDetails() {
		dlg2 = new DlgDetail(this, db, "�󼼺���");
		dlg2.dlginitForm();
	} // dlgDetails end
	
// ************************ Count Query **********************//
 // ���� ��볻�� ���� Count
	public int seachDay() {
		ResultSet rs;
		ResultSetMetaData meta;
		
		String sql;			// Query 
		int colCnt = 0;		// ���� ��� ����
		
	  // ************ Select Count ************ //
		sql = "SELECT COUNT(*) as count FROM book "
				+ "WHERE dYear='" + year + "' AND dMonth='" + month + "' AND dDay='" + day + "'" ;
		rs = db.getResultSet(sql);
		
		try {
			rs.next();
			meta = rs.getMetaData();
		  // ���� ���� ����
			colCnt = rs.getInt(meta.getColumnName(1));		
		}catch(SQLException e) {
			System.out.println("SQLException " + e.getMessage());
		}
		return colCnt;
	} // seachDay end
	
// ************************ ��� �� �ݾ� Query ************************ //
	public String totalamount(String sw) {
		ResultSet rs;
		ResultSetMetaData meta;
		
		String sql, total = null;
	  // ************ Select Sum | nExpense Total ************ //
		if(sw == "y") // y �Է¹��� �� �� �Ѿ� ���
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
  // �޷� Frame
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
		cal.drawCalendar(x,y,g);		// �޷� Draw
	}// paint end
	
  // Button Event 
	class calMoveEvnet implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int nyear = Integer.parseInt(year);
			int nmonth = Integer.parseInt(month);
			if(e.getActionCommand() == "��") {
				nmonth--;
				
				if(nmonth<1) {		// 1�� ���Ϸ� �������� 12���� ���� && ���� �ط�
					nmonth = 12;
					nyear--;
				}
				cal.calSet(nyear, nmonth);
				
				year = Integer.toString(nyear);
				month = Integer.toString(nmonth);
				label6.setText(year);
				label7.setText(month + "��");
				
				totalset();
				tf_monthtot.setText(monthtotal + "��");
				tf_yeartot.setText(yeartotal + "��");
			}else {
				nmonth++;
				
				if(nmonth>12) {		// 12�� �ʰ��ϸ� 1���� ���� && ���� �ط�
					nmonth = 1;
					nyear++;
				}
				
				cal.calSet(nyear, nmonth);
				
				year = Integer.toString(nyear);
				month = Integer.toString(nmonth);
				label6.setText(year);
				label7.setText(month + "��");
				
				totalset();
				tf_monthtot.setText(monthtotal + "��");
				tf_yeartot.setText(yeartotal + "��");
			}
			cal.calGet();
			repaint();
		}	
	} // calMoveEvnet end
	
	class AddEventhandle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql;
			int categoryNo;
		// ************ �Է¹��� �� ��� �ݾ� & ���뵵 ( Insert ) ************ // 
			if(discription.getText().equals("")) {	// �ԷµȰ� ������ error
				System.out.println("�Է´���");	// �ӽ�
			}else {
				categoryNo = categoryChange();
				sql  = "INSERT INTO book (dYear, dMonth, dDay, nExpense, strUsageHistory, category) VALUES('"+ year + "', '" + month + "', '" + day + "', " 
						+ Integer.parseInt(tf_money.getText()) + ", '" + discription.getText() + "', " + categoryNo + ")";
				db.Exectue(sql);
				totalset();
				tf_monthtot.setText(monthtotal + "��");
				tf_yeartot.setText(yeartotal + "��");
			}
			
		// Text reset
			tf_money.setText("");
			discription.setText("");
		}
	} // AddEventhandle end
	
  // dlg �󼼺��� Button Event
	class viewsEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dlgDetails();		// �󼼺��� â ����
		}
	}
	
  // Mouse Event
	class MouseEventHandle extends MouseAdapter{
		private int ndayX, ndayY;	// ȭ���� �޷� Ŭ�� ��ǥ
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {	// ** Mouse Left Click
				if(e.getX() < 355 && e.getY() < 459 && e.getY() > 160) {
				 // ��ǥ�� Indexȭ
					ndayX = (e.getX() - 10) / 50;
					ndayY = ((e.getY() - 110) / 50) - 1;
				// �޷� �� ���
					day = Integer.toString(cal.getDay(ndayX, ndayY));
					tf_cal.setText(year + "-" + month + "-" + day);
				}else {
					System.out.println("������ ���Ƴ����ϴ�."); // �ӽ�
					repaint();
				}
			}else if(e.getButton() == MouseEvent.BUTTON3){ // ** Mouse right Click
				dlgDesign();
			}
		}
	} // MouseEventHandle end

  // �� & �� �� ���⳻�� Set
	public void totalset() {
		yeartotal = (totalamount("y") != null)?totalamount("y"): "0";
		monthtotal = (totalamount("m") != null)?totalamount("m"):"0";
	}// totalset end
	
 // ī�װ� �ڵ�ȭ
	public int categoryChange() {
		// 1001 �Ĵ�, 1002 �����, 1003 ��Ȱ��, 1004 ����
		int cateNo = categoryBox.getSelectedIndex();		// Category Combo Box Index
		int category = 0;
		switch(cateNo) {
			case 0:		// �Ĵ�
				category = 1001;
				break;
			case 1:		// �����
				category = 1002;
				break;
			case 2:		// ��Ȱ��
				category = 1003;
				break;
			case 3:		// ����
				category = 1004;
				break;
		}
		
		return category;
	} // categoryChange
}
