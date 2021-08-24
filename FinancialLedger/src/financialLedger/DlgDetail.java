package financialLedger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DlgDetail extends JDialog{
	private FinancialLedger_DAO db; 	// db
	private JComboBox <String> cb1;					// Category ComboBox
	private Panel pan1, pan12, pan13, pan2;			// Panel
	private JTextField tf_dlgyear, tf_dlgmonth;		// Year, Month Choice
	private JButton dlgaddBt;						// View Event Bt
	private JTable table;							// View Table
	private tableModelClass tableModel;				// Table Model
	JScrollPane scrollPane;							// Scroll
	
	// 1001 �Ĵ�, 1002 �����, 1003 ��Ȱ��, 1004 ����
	private String dataModel[] = {"�Ĵ�", "�����", "��Ȱ��", "����"};	// ComBoBox Model
	private String columnName[] = {"��", "��", "ī�װ�", "�� �ݾ�"};	// Table Mobel Column
	
	public DlgDetail(Frame frame, FinancialLedger_DAO db, String title) {
		super(frame, title, true);
		this.db = db;
		
	  // table Setting
		tableModel = new tableModelClass(columnName.length, columnName);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(470, 224));
		scrollPane = new JScrollPane(table);
	}
	
	public void dlginitForm() {
	  // pan12
		pan1 = new Panel();
		pan12 = new Panel();
		pan13 = new Panel();
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
		
	 // pan1	
		pan1.add(pan12, "North");
		pan1.add(pan13,"East");
		
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
	
	public void inputTable(int cnt, String year, String month, String category, int total) {
		table.setValueAt(year, cnt, 0);
		table.setValueAt(month, cnt, 1);
		table.setValueAt(category, cnt, 2);
		table.setValueAt(total, cnt, 3);
	}
	
	class DlgViewEvnetHandle implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			inputTable(0,"2021","8","test",10);
			repaint();		// Table �ٽñ׸��� ( �۵�����. ��ü�� ã���� )
		}
	} // DlgAddEvnetHandle end
}
