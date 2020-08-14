package servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOException;
import dao.ReservationDao;
import bean.PlanBean;
import bean.ReservationBean;


/**
 * Servlet implementation class reservedServlet
 */
@WebServlet("/reservedServlet")
public class ReservedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null) { // セッションオブジェクトなし
			request.setAttribute("message",
					"セッションが切れています。もう一度トップページより操作してください。");
			gotoPage(request, response, "/error.jsp");
			return;
		}
		
		try {
			String action = request.getParameter("action");
			// 戻る
			//if (action == null || action.length() == 0 || action.equals("hogehoge")) {
			//	gotoPage(request, response, "/xxx.jsp");
			
			// 予約内容確認画面に遷移
			if (action.equals("check")) {
				PlanBean bean = (PlanBean) session.getAttribute("plan");
				int planId = bean.getPlanId();
				Date checkInDate = bean.getCheckInDate();
				Date checkOutDate = bean.getCheckOutDate();
				
				ReservationDao dao = new ReservationDao();

				// アカウントID取得
				int accountId = (int) session.getAttribute("account");
				int accountCount = dao.getAccountReserved(accountId);
				
				// 5件以上予約が残っている場合はエラー
				if (accountCount > 5) {
					request.setAttribute("message", "5件以上予約を行うことはできません");
					gotoPage(request, response, "/error.jsp");
					return;
				}
				
				// 予約部屋数
				int quantity = Integer.parseInt(request.getParameter("quantity"));

				// 部屋の予約数が宿の部屋数より多い
				int roomQuantity = bean.getRoomQuantity();
				if (quantity > roomQuantity) {
					request.setAttribute("message", "部屋に空きがないため予約を行えません");
					gotoPage(request, response, "/error.jsp");
					return;
				}

				ReservationBean rbBean = new ReservationBean();

				// 宿泊日数取得
				long datetime1 = checkInDate.getTime();
				long datetime2 = checkOutDate.getTime();
				long one_date_time = 1000 * 60 * 60 * 24;
				long diffDays = (datetime2 - datetime1) / one_date_time;
				// 合計金額
				int price = (int) (bean.getPlanPrice() * quantity * diffDays);

				int count = dao.getAllReservedCount(planId, checkInDate, checkOutDate);
				if (count > 0) {
					// 各日程で予約された部屋数分の空きがあるか確認
					int reservedCount = 0;
					Date stayDate = null;
					
					// 各日程の同プランの予約済み合計部屋数を取得
					for (int i = 0; i < diffDays; i++) {

						// 初回のみ宿泊日にチェックイン日を設定
						if (i == 0) {
							stayDate = checkInDate;				
						}
						reservedCount = dao.getReservedSum(planId, stayDate);
						
						// 予約部屋数が空き部屋数(宿の部屋数 - 予約済み数)より大きかったらエラー 
						if (roomQuantity - reservedCount < roomQuantity) {
							request.setAttribute("message", "部屋に空きがないため予約を行えません");
							gotoPage(request, response, "/error.jsp");
							return;
						}
					}
					
				}

				rbBean.setHotelId(bean.getHotelId());
				rbBean.setPlanId(bean.getPlanId());
				rbBean.setReservedCount(quantity);
				rbBean.setHotelName(bean.getHotelName());
				rbBean.setPlanName(bean.getPlanName());
				rbBean.setCheckInDate(checkInDate);
				rbBean.setCheckOutDate(checkOutDate);
				rbBean.setPrice(price);
				rbBean.setLodgmentDays(diffDays);
				
				session.setAttribute("detail", rbBean);
				gotoPage(request, response, "/confirm.jsp");
						
			} else if (action.equals("order")) {
				// 予約するボタン押下時
				ReservationBean detail = (ReservationBean) session.getAttribute("detail");
				if (detail == null) { // 予約内容がない
					request.setAttribute("message", "正しく操作してください。");
					gotoPage(request, response, "/error.jsp");
					return;
				}
				
				// アカウントID取得
				int accountId = (int) session.getAttribute("account");
				detail.setAccountId(accountId);
				ReservationDao dao = new ReservationDao();
				// 予約台帳テーブルにデータ登録
				dao.setReservationLedger(detail);
				// 予約済み管理テーブルにデータ登録
				dao.setReservedCount(detail);
				
				gotoPage(request, response, "/order.jsp");
				
			}
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/error.jsp");
		}
	}

	private void gotoPage(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
