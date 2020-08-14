package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import bean.AccountBean;
import dao.DAOException;
import dao.AccountDAO;


@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			String action = request.getParameter("action");
			if (action == null || action.length() == 0 || action.equals("menu")) {
				gotoPage(request, response, "/menu.jsp");
			} else if(action.equals("login")) {
				//login ログイン
				
				int accountId = Integer.parseInt(request.getParameter("accountId"));
				String password = request.getParameter("password");
				//loginが選択されているか
//				System.out.println("loginが選択");
				
				AccountDAO dao = new AccountDAO();
				dao.loginAccount (accountId, password);
				boolean result = dao.loginAccount (accountId, password);
				
				if (result == true) {
					HttpSession session = request.getSession();
					session.setAttribute("account",accountId);
					request.setAttribute("message", "ログインが完了しました。");
					request.setAttribute("link", "<a href=\"/hotelReservation/planSearch.jsp\">プラン検索</a>");
					RequestDispatcher rd = request.getRequestDispatcher("/done.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("message", "会員番号またはパスワードが一致しません。");
					request.setAttribute("link", "<a href=\"/hotelReservation/login.jsp\">戻る</a>");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request, response);   
				}
				
				
			} else if(action.contentEquals("join")) {
				//join 会員登録
				
				String familyName = request.getParameter("familyName");
				String firstName = request.getParameter("firstName");
				String postal = request.getParameter("postal");
				String address = request.getParameter("address");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");
				String password = request.getParameter("password"); 
				
				//System.out.println("familyName:" + familyName + " familyName.length():" + familyName.length());
				if (familyName == "" || familyName.length() > 10) {
					request.setAttribute("message", "「姓」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (firstName == "" || firstName.length() > 10) {
					request.setAttribute("message", "「名」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (postal == "" || postal.length() > 7) {
					request.setAttribute("message", "「郵便番号」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (address == "" || address.length() > 100) {
					request.setAttribute("message", "「住所」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (tel == "" || tel.length() > 11) {
					request.setAttribute("message", "「電話番号」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (email.length() > 100) {
					request.setAttribute("message", "「メールアドレス」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else if (password == "" || password.length() > 12 || password.length() < 7) {
					request.setAttribute("message", "「パスワード」の入力内容を確認してください。");
					RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
					rd.forward(request,response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("familyName", familyName);
					session.setAttribute("firstName", firstName);
					session.setAttribute("postal", postal);
					session.setAttribute("address", address);
					session.setAttribute("tel", tel);
					session.setAttribute("email", email);
					session.setAttribute("password", password);
					RequestDispatcher rd = request.getRequestDispatcher("/accountConfirm.jsp");
					rd.forward(request,response);
					
					//joinが選択されているか
//					System.out.println("joinが選択");
				}
		

				
				
				
			} else if(action.equals("register")) {
				//register 登録確定
				
				HttpSession session = request.getSession();
				
				String familyName = (String)session.getAttribute("familyName");
				String firstName = (String)session.getAttribute("firstName");
				String postal = (String)session.getAttribute("postal");
				String address = (String)session.getAttribute("address");
				String tel = (String)session.getAttribute("tel");
				String email = (String)session.getAttribute("email");
				String password = (String)session.getAttribute("password"); 
				
				AccountBean bean = new AccountBean();
				bean.setFamilyName(familyName);
				bean.setFirstName(firstName);
				bean.setPostal(postal);
				bean.setAddress(address);
				bean.setTel(tel);
				bean.setEmail(email);
				bean.setPassword(password);
				
//				System.out.println("beanへ格納");
				
				AccountDAO dao = new AccountDAO();
				int accountId = dao.joinAccount (bean);
						
				int accountNumber = accountId;
				
//					System.out.println("accountId:" + accountId);
				request.setAttribute("message", "登録が完了しました。");
				request.setAttribute("accountId", "お客様の会員番号は「" + accountNumber + "」番です。");
				request.setAttribute("link", "<a href=\"/hotelReservation/login.jsp\">ログイン</a>");
				RequestDispatcher rd = request.getRequestDispatcher("/done.jsp");
				rd.forward(request, response);
				

				
			}
			
			
			
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request,response);
			
			
			
		} finally {
			
		}
		
	}
		
		private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
				throws ServletException, IOException {
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
		
	
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		doGet(request, response);
	}
	

}
