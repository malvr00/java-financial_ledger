package financialLedger;

import java.util.*;

public class FinancialLedgerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FinancialLedger_Frame frame;
		FinancialLedger_DAO db = new FinancialLedger_DAO();
		frame = new FinancialLedger_Frame(db);
		frame.setVisible(true);
		/* Calendar test
		Calendar test = Calendar.getInstance();
		test.set(Calendar.YEAR, 2013);
		test.set(Calendar.DATE, 1);
		int mm = test.get(Calendar.YEAR);
		int dd = test.getActualMaximum(Calendar.DATE);
		int ii = test.get(Calendar.DATE);
		int ee = test.get(Calendar.DAY_OF_WEEK);
		System.out.println(mm + " " + dd + " " + ee + " " + ii);*/
	}

}
