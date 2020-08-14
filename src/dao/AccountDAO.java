package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;


import bean.AccountBean;


public class AccountDAO {
	private Connection con;
	
	public AccountDAO() throws DAOException {
		getConnection();
	}
	
	public boolean loginAccount(int accountId, String password)
		throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM account WHERE account_id = ? and password = ?";
			st = con.prepareStatement(sql);
			st.setInt(1, accountId);
			st.setString(2, password);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				return true; 
				
			} else {
				return false;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");
			} finally {
				try {
					if(rs != null) rs.close();
					if(st != null) st.close();
					close();
				} catch (Exception e) {
					throw new DAOException("リソースの開放に失敗しました");
				}
			}
		
		
	}
	
	public int joinAccount(AccountBean bean) throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			int accountId = 0;
			String sql = "SELECT nextval('account_account_id_seq')";
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				accountId = rs.getInt(1) ;
			} 
			rs.close();
			st.close();
			
			sql = "INSERT INTO account "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			Calendar cl = Calendar.getInstance();
			Date today = new Date(cl.getTime().getTime());
			
			st = con.prepareStatement(sql);
			st.setInt(1, accountId);
			st.setString(2, bean.getFamilyName());
			st.setString(3, bean.getFirstName());
			st.setString(4, bean.getPostal());
			st.setString(5, bean.getAddress());
			st.setString(6, bean.getTel());
			st.setString(7, bean.getEmail());
			st.setDate(8, today);
			st.setString(9, bean.getPassword());
		
			st.executeUpdate();


			return accountId;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました");
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
		
		
		
		
		
	}	
	
	
	
	
	private void getConnection() throws DAOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql:march_db";
			String user = "student";
			String pass = "himitu";
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("接続に失敗しました");
		}
	}
	
	private void close() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
















}