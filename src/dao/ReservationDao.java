package dao;

import java.util.Calendar;

import bean.ReservationBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DAOException;

public class ReservationDao {
	private Connection con;

	public ReservationDao() throws DAOException {
		getConnection();
	}
	
	public int getAccountReserved(int accountId)
		throws DAOException {
			
	if (con == null)
		getConnection();
		
		int count = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		// システム日付け取得
		Calendar cl = Calendar.getInstance();
		Date today = new Date(cl.getTime().getTime());
		
		try {
			String sql = "SELECT COUNT(*) FROM reservation_ledger WHERE account_id = ? AND check_out_date > ?";
			st = con.prepareStatement(sql);
			st.setInt(1, accountId);
			st.setDate(2, today);
							
			rs = st.executeQuery();
				
			if (rs.next()) 
				count = rs.getInt("count");
			
			return count;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}
	// チェックイン〜チェックアウト日の間の予約済み件数取得
	public int getAllReservedCount(int planId, Date checkInDate, Date checkOutDate)
		throws DAOException {
		
		if (con == null)
			getConnection();
		
		int count = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		// チェックアウト日を-1
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		Date beforDate = new Date(cal.getTime().getTime());
		
		try {
			String sql = "SELECT COUNT(*) FROM reserved_count WHERE plan_id = ? "
					+ "AND stay_date BETWEEN ? AND ?";

			st = con.prepareStatement(sql);
			st.setInt(1, planId);
			st.setDate(2, checkInDate);
			st.setDate(3, beforDate);
						
			rs = st.executeQuery();
			
			if (rs.next()) 
				count = rs.getInt("count");
		
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}
	
	public int getReservedSum(int planId, Date stayDate)
			throws DAOException {
			
			if (con == null)
				getConnection();
			
			int count = 0;
			PreparedStatement st = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT SUM(reserved_count) FROM reserved_count WHERE plan_id = ? and stay_date = ?";

				st = con.prepareStatement(sql);
				st.setInt(1, planId);
				st.setDate(2, stayDate);
							
				rs = st.executeQuery();
				
				if (rs.next()) 
					count = rs.getInt("sum");
			
				return count;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			} finally {
				try {
					// リソースの開放
					if(rs != null) rs.close();
					if(st != null) st.close();
					close();
				} catch (Exception e) {
					throw new DAOException("リソースの開放に失敗しました。");
				}
			}
		}
	
	// 予約管理台帳テーブル登録
	public void setReservationLedger(ReservationBean detail) throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		// システム日付け取得
		Calendar cl = Calendar.getInstance();
		Date today = new Date(cl.getTime().getTime());
		
		try {
			String sql = "INSERT INTO reservation_ledger(reservation_id,account_id, plan_id, reservation_date, check_in_date, check_out_date, room_count) "
					+ "VALUES(?,?,?,?,?,?,?)";
			
			// 予約IDの取得 Serial型の暗黙シーケンスから取得
			int reservationId = 0;
			String nextsql = "SELECT nextval('reservation_ledger_reservation_id_seq')";
			st = con.prepareStatement(nextsql);
			rs = st.executeQuery();
			if (rs.next()) {
				reservationId = rs.getInt(1);
			}
			rs.close();
			st.close();
			
			st = con.prepareStatement(sql);
			st.setInt(1, reservationId);
			st.setInt(2, detail.getAccountId());
			st.setInt(3, detail.getPlanId());
			st.setDate(4, today);
			st.setDate(5, detail.getCheckInDate());
			st.setDate(6, detail.getCheckOutDate());
			st.setInt(7, detail.getReservedCount());
			
			st.executeUpdate();
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}
	
	public void setReservedCount(ReservationBean detail) throws DAOException {
		if (con == null)
			getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO reserved_count(reserved_count_id, hotel_id, plan_id, stay_date, reserved_count) "
					+ "VALUES(?,?,?,?,?)";

			// チェックイン日を設定	
			Calendar date = Calendar.getInstance();
			date.setTime(detail.getCheckInDate());
			
			// 宿泊数設定
			Date stayDate = null;
			int stayCount = detail.getLodgmentDays();
			for (int i = 0; i < stayCount ; i++) {
				
				if (i == 0) {
					stayDate = new Date(date.getTime().getTime());					
				}
				
				// 予約済みIDの取得 Serial型の暗黙シーケンスから取得
				int reservedCountId = 0;
				String nextSql = "SELECT nextval('reserved_count_reserved_count_id_seq')";
				st = con.prepareStatement(nextSql);
				rs = st.executeQuery();
				if (rs.next()) {
					reservedCountId = rs.getInt(1);
				}
				rs.close();
				st.close();
				
				st = con.prepareStatement(sql);
				st.setInt(1, reservedCountId);
				st.setInt(2, detail.getHotelId());
				st.setInt(3, detail.getPlanId());
				st.setDate(4, stayDate);
				st.setInt(5, detail.getReservedCount());
			
				st.executeUpdate();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(stayDate);
				cal.add(Calendar.DATE, +1);
				stayDate = new Date(cal.getTime().getTime());
			}			
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの登録に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	private void getConnection() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			// URL、ユーザ名、パスワードの設定
			String url = "jdbc:postgresql:march_db";
			String user = "student";
			String pass = "himitu";
			// データベースへの接続
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("接続に失敗しました。");
		}
		
	}
	
	private void close() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
}
