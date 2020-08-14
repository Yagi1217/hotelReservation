package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.PlanBean;
import dao.DAOException;
import dao.PlanSearchDAO;

/**
 * Servlet implementation class PlanSearchServlet
 */
@WebServlet("/PlanSearchServlet")
public class PlanSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlanSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

		try {
			if (action == null || action.length() == 0 || action.equals("top")) {
			HttpSession session = request.getSession(true);

			// パラメータの解析
			String planNamePartial = request.getParameter("planNamePartial");
			Date checkIn = Date.valueOf(request.getParameter("checkIn"));
			Date checkOut = Date.valueOf(request.getParameter("checkOut"));
			String roomType = request.getParameter("roomType");

			//入力されたフォームの情報
			request.setAttribute("planNamePartial", planNamePartial);
			request.setAttribute("checkIn", checkIn);
			request.setAttribute("checkOut", checkOut);
			request.setAttribute("roomType", roomType);
		
			//Servletの実行日
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date sqlNow = new Date(System.currentTimeMillis());
			java.util.Date now = sqlNow;			
			String strNow = sdf.format(now);

			//実行日より前であるかの比較用にint型に変換する。
		    int diffCheckIn = strNow.compareTo(request.getParameter("checkIn"));
		    int diffNow = now.compareTo(now);

			//チェックイン日が実行日より先か
			//チェックイン日がチェックアウト日より先か
	       if (diffCheckIn > diffNow || checkIn.compareTo(checkOut) > 0 || checkIn == null) {
				request.setAttribute("message", "入力された有効なチェックイン 又は チェックアウト日を確認し<br>再度を指定し直してしてください。");
				gotoPage(request, response, "/error.jsp");
				return;
	       }

			//findAllメソッドへの受け渡し
	       PlanSearchDAO dao = new PlanSearchDAO();
	       List<PlanBean> list = dao.findAll(planNamePartial, checkIn, checkOut, roomType);
			
	       //findAllメソッドからの受け取り
			if (list.size() == 0) {
				request.setAttribute("message", "申し訳ありません。<br>ご希望の条件に合うプランは見つかりませんでした。");
				gotoPage(request, response, "/error.jsp");				
				return;
			}

			request.setAttribute("Plan", list);
			gotoPage(request, response, "/planSearch.jsp");

			} else	if (action.equals("entry")) {
				HttpSession session = request.getSession(true);
				
				//選択されたプラン内容をsessionに追加する。
				PlanBean bean = new PlanBean(
						request.getParameter("hotelName"),
						request.getParameter("hotelAddress"), // 1
						Date.valueOf(request.getParameter("checkInDate")), // 2
						Date.valueOf(request.getParameter("checkOutDate")), // 3
						Integer.parseInt(request.getParameter("planId")), // 4
						Integer.parseInt(request.getParameter("hotelId")), // 5
						request.getParameter("planName"), // 6
						Integer.parseInt(request.getParameter("planPrice")), // 7
						Integer.parseInt(request.getParameter("roomQuantity")), // 8
						request.getParameter("roomType") // 9
					);
				session.setAttribute("plan", bean);

				//現在、ログイン状態か
				if (session.getAttribute("account") == null) {

					request.setAttribute("checkIn", request.getParameter("checkInDate"));
					request.setAttribute("checkOut", request.getParameter("checkOutDate"));
					
					request.setAttribute("message", "ご予約のまえに<a href=\"/hotelReservation/login.jsp\">ログイン</a>してください。");
					gotoPage(request, response, "/error.jsp");				

				} 
				int account = (int) session.getAttribute("account");
				
				//予約ページへ
				gotoPage(request, response, "/entry.jsp");

			} /*else	if (action.equals("entry")) {
			//現在、ログイン状態か
			
			//予約ページへ
			List<PlanBean> list = new ArrayList<PlanBean>();
			PlanBean bean = new PlanBean(
				request.getParameter("hotelName"),
				request.getParameter("hotelAddress"), // 1
				Date.valueOf(request.getParameter("checkInDate")), // 2
				Date.valueOf(request.getParameter("checkOutDate")), // 3
				Integer.parseInt(request.getParameter("planId")), // 4
				Integer.parseInt(request.getParameter("hotelId")), // 5
				request.getParameter("planName"), // 6
				Integer.parseInt(request.getParameter("planPrice")), // 7
				Integer.parseInt(request.getParameter("roomQuantity")), // 8
				request.getParameter("roomType") // 9
			);
			list.add(bean);
			request.setAttribute("plan", list);
			gotoPage(request, response, "/entry.jsp");

		}
*/		
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/error.jsp");

		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void gotoPage(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}
}
