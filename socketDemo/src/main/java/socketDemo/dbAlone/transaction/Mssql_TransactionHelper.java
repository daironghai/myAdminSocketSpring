package socketDemo.dbAlone.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import socketDemo.dbAlone.MyJdbcFactory;
import socketDemo.dbAlone.transaction.impl.TransactionHelper;

public class Mssql_TransactionHelper implements TransactionHelper {
	
	
	
	
	@Override
	public int executeTransaction(String sql){
		if(null==sql && "".equals(sql)){
			return -1;
		}
		List<String> sqlList = new ArrayList<String>();
		sqlList.add(sql);
		return executeTransaction(sqlList);
	}
	
	
	@Override
	public int executeTransaction(List<String> sqlList){
		int ret = 0;
		Connection conn = null;
		try {
			conn = MyJdbcFactory.getConnection();
			conn.setAutoCommit(false);
			
			Statement statement = conn.createStatement();
			for (String sql : sqlList) {
				ret += statement.executeUpdate(sql);
			}
			statement.close();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			ret = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		return ret;
	}


	@Override
	public ResultSet select(String sql) {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = MyJdbcFactory.getConnection();
			Statement statement = conn.createStatement();
			
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}



	@Override
	public int executeTransaction(String[] sqlList) {
		return executeTransaction(Arrays.asList(sqlList));
	}
	
	
	
	
}
