package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.util.List;

import bean.PlanBean;
import dao.DAOException;

public class PlanSearchDAO {
	private Connection con;

	public PlanSearchDAO() throws DAOException {
		getConnection();
	}

	public List<PlanBean> findAll(String planNamePartial, Date checkIn, Date checkOut, String roomTypeStr) throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "SELECT * FROM plan_data P, hotel_data H WHERE P.hotel_id = h.hotel_id AND "
					+ "P.plan_name LIKE ? AND "
					+ "P.end_date >= ? AND "
					+ "P.end_date >= ? AND "
					+ "P.room_type = ? ORDER BY p.plan_id DESC";
						
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);

			// プレースホルダーの設定
			st.setString(1, "%" + planNamePartial + "%");
			st.setDate(2, checkIn);
			st.setDate(3, checkOut);
			st.setString(4, roomTypeStr);
			
			// SQLの実行
			rs = st.executeQuery();

			// 結果の取得および表示
			List<PlanBean> list = new ArrayList<PlanBean>();
			while (rs.next()) {
				
				String hotelName = rs.getString("hotel_name");
				String hotelAddress = rs.getString("hotel_address");
				Date checkInDate = rs.getDate("hotel_check_in");
				Date checkOutDate = rs.getDate("hotel_check_out");
				int planId = rs.getInt("plan_id");
				int hotelId = rs.getInt("hotel_id");
				String planName = rs.getString("plan_name");
				int planPrice = rs.getInt("plan_price");
				int roomQuantity = rs.getInt("room_quantity");
				String roomType = rs.getString("room_type");
				
				PlanBean bean = new PlanBean(
					hotelName,
					hotelAddress,
					checkInDate,
					checkOutDate,
					planId,
					hotelId,
					planName,
					planPrice,
					roomQuantity,
					roomType
				);
				list.add(bean);
			}
			// 検索結果情報をListとして返す
			return list;
			
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
