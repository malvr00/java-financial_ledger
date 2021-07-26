package financialLedger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FinancialLedger_Frame extends JFrame{
	private FinancialLedger_DAO db;
	public FinancialLedger_Frame(FinancialLedger_DAO db) {
		this.db = db;
	}
}
