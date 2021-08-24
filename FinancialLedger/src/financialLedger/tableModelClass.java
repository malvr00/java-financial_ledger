package financialLedger;

import javax.swing.table.AbstractTableModel;

public class tableModelClass extends AbstractTableModel {
	private Object data[][];
	private String columnName[];
	private int nColumn, nRow, nData;
	public tableModelClass (int column, String header[]) {
		nData = 0;
		nRow = 20;
		nColumn = column;
		columnName = header;
		data = new Object[nRow][nColumn];
	}
  // ���� �� return �Լ�
	public int getColumnCount() {
		return nColumn;
	} // getColumnCount end
	
  // ���� �� return �Լ�
	public int getRowCount() {
		return nRow;
	} // getRowCount end
	
  // �ش� ��ġ�� data return �Լ�
	public Object getValueAt(int row, int column) {
		return data[row][column];
	} // getVAlueAt end
	
  // �ش� ��ġ�� data �� �߰� �Լ�
	public void setValueAt(Object value, int row, int column) {
		if(row>=nRow)
			expand(row);
		data[row][column] = value;
	} // setValueAt end
	
  // ���� ũ�⺸�� ũ�� Ȯ�� �Լ�
	public void expand(int row) {
		Object temp[][] = new Object[nRow*2][nColumn];
		for(int i = 0; i<nRow; i++) {
			for(int j=0; j<nColumn; j++) {
				temp[i][j] = data[i][j];
			}
		}
		nRow *= 2;
		data = temp;
	} // expand end
	

	public int getDataCount() {
		return nData;
	} // getDataCount end
	
  // ��(�Ӽ�) �̸� return �Լ� 
	public String getColumnName(int col) {
		return columnName[col];
	} // getColumnName end

} // tableModelClass end
