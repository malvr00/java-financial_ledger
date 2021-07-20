package financialLedger;

import java.sql.*;

public class FinancialLedger_DAO {
	private Connection con;
	private Statement smt;
	public FinancialLedger_DAO() {		// 생성자에서 dbConnction
		try {
			con = DriverManager.getConnection("jdbc:ucanaccess://db1.accdb;memory=false");
			smt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
  // try catch 사용빈도 줄이기 위해 함수로 만듬.
	public ResultSet getResultSet(String sql) {
		try {
			return smt.executeQuery(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void Exectue(String sql) {
		try {
			smt.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Close() {
		try {
			smt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
