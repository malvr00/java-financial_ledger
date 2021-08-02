package financialLedger;

public class FinancialLedgerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FinancialLedger_Frame frame;
		FinancialLedger_DAO db = new FinancialLedger_DAO();
		frame = new FinancialLedger_Frame(db);
		frame.setVisible(true);
	}

}
