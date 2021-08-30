package financialLedger;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;


public class DlgDetail extends JDialog{
	private FinancialLedger_DAO db; 				// DataBase Class
	private FinancialLedger_Frame functions;        // FinancialLedger_Frame class 
	private JComboBox <String> cb1;					// Category ComboBox
	private Panel pan1, pan12, pan13, pan14, pan2;	// Panel
													// Year, Month Choice & total amount
	private JTextField tf_dlgyear, tf_dlgmonth, tf_yearTotal, tf_monthTotal;
	private JButton dlgaddBt;						// View Event Bt
	private JTable table;							// View Table
	private tableModelClass tableModel;				// Table Model
	private JScrollPane scrollPane;					// Scroll
	private int dataCount;							// Table Row Count
	// 1001 �Ĵ�, 1002 �����, 1003 ��Ȱ��, 1004 ����
	private String dataModel[] = {"�Ĵ�", "�����", "��Ȱ��", "�ҵ�"};		// ComBoBox Model
	private String columnName[] = {"��", "��", "��", "ī�װ�", "�ݾ�"};		// Table Model Column
	
	public DlgDetail(Frame frame, FinancialLedger_DAO db, String title, FinancialLedger_Frame functions) {
		super(frame, title, true);
		this.db = db;
	  // function reuse
		this.functions = functions;
		
	  // table Setting
		tableModel = new tableModelClass(columnName.length, columnName);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(470, 224));
		scrollPane = new JScrollPane(table);
		
		dataCount = 0;
	}
	
	public void dlginitForm() {
	  // pan12
		pan1 = new Panel();
		pan12 = new Panel();
		pan13 = new Panel();
		pan14 = new Panel();
		pan1.setLayout(new BorderLayout());
		cb1 = new JComboBox<String>(new DefaultComboBoxModel<String>(dataModel));
		tf_dlgyear = new JTextField(5);
		tf_dlgmonth = new JTextField(5);
		
		pan12.add(cb1);
		pan12.add(tf_dlgyear);
		pan12.add(new JLabel("��"));
		pan12.add(tf_dlgmonth);
		pan12.add(new JLabel("��"));
				
	 // pan13
		dlgaddBt = new JButton("����");
		dlgaddBt.addActionListener(new DlgViewEvnetHandle());
		pan13.add(dlgaddBt);
		
	 // pan14
		tf_yearTotal = new JTextField("0��",15);
		tf_monthTotal = new JTextField("0��",15);
		
		tf_yearTotal.setEnabled(false);
		tf_monthTotal.setEnabled(false);
		tf_yearTotal.setHorizontalAlignment(JTextField.RIGHT);
		tf_monthTotal.setHorizontalAlignment(JTextField.RIGHT);
		
		pan14.add(new JLabel("�� �� �ݾ�"));
		pan14.add(tf_yearTotal);
		pan14.add(new JLabel("�� �� �ݾ�"));
		pan14.add(tf_monthTotal);
		
	 // pan1	
		pan1.add(pan12, "North");
		pan1.add(pan13,"East");
		pan1.add(pan14,"West");
		
	 // pan2
		pan2 = new Panel();
		pan2.add(scrollPane);
		
		add(pan1, "North");
		add(pan2,"Center");
		
		setLocation(100,100);
		revalidate();
		pack();
		setVisible(true);
	}
	
	public void inputTable(int cnt, String year, String month, String day, String category, int total) {
		table.setValueAt(year, 	cnt, 0);
		table.setValueAt(month, cnt, 1);
		table.setValueAt(day,   cnt, 2);
		table.setValueAt(category, cnt, 3);
		table.setValueAt(total, cnt, 4);
	}
	
	public void removeTable(int row) {
		table.setValueAt(null, row, 0);
		table.setValueAt(null, row, 1);
		table.setValueAt(null, row, 2);
		table.setValueAt(null, row, 3);
		table.setValueAt(null, row, 4);
	}
	class DlgViewEvnetHandle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ResultSet rs, cate;
			String sql;
			// ��, ��, ��, ī�װ�, �� �ݾ�
			String year = tf_dlgyear.getText(), month = tf_dlgmonth.getText(), day = null, strcategory = null, monthTotal = null, yearTotal = null;
			int price;			// ���ݾ�
			int categoryNo = functions.categoryChange(cb1.getSelectedIndex());
		  // *********************** �Է��� ��, �� ���� Select *********************** //
			sql = "SELECT * FROM book WHERE dYear = '" + year + "' AND dMonth = '" + month + "' AND "
					+ "category = " + categoryNo + " ORDER BY nExpense ASC";
			rs = db.getResultSet(sql);
			
		  // *********************** ī�װ� Select *********************** //
			sql = "SELECT * FROM category WHERE num = " + categoryNo;
			cate = db.getResultSet(sql);
			
		  // Table �ʱ�ȭ
			for(int i =0; i<=dataCount; i++) {
				removeTable(i);
			}
			
			try {
				cate.next();
				strcategory = cate.getString("cat");
			// Table Set
				for(dataCount = 0; rs.next(); dataCount++) {
					year = rs.getString("dYear");
					month = rs.getString("dMonth");
					day = rs.getString("dDay");
					price = rs.getInt("nExpense");
					inputTable(dataCount, year, month, day, strcategory, price);
				}
				yearTotal = (functions.totalamount("y", year, month, categoryNo) !=null)? functions.totalamount("y", year, month, categoryNo):"0";
				monthTotal = (functions.totalamount("m", year, month, categoryNo) !=null)? functions.totalamount("m", year, month, categoryNo):"0";
				tf_yearTotal.setText(yearTotal + "��");
				tf_monthTotal.setText(monthTotal + "��");
			}catch(SQLException e1) {
				System.out.println(e1.getMessage());
			}
			repaint();		// Table �ٽñ׸��� ( �۵�����. ��ü�� ã���� )
		}
	} // DlgAddEvnetHandle end
	
}
