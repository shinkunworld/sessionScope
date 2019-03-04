package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.RegisterUserLogic;
import model.User;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//forward先
		String forwardPath = null;
		//survletクラスの動作を決定する　”action”の値を
		//request parameterから取得
		String action = request.getParameter("action");
		//登録の開始をrequestされた時の処理
		if (action == null) {
			//forward 先を設定
			forwardPath = "/WEB-INF/jsp/registerForm.jsp";
			//登録確認画面から登録実行をrequestされた時の処理
		} else if (action.equals("done")) {

			//session scopeに保存された登録userを取得
			HttpSession session = request.getSession();
			User registerUser = (User) session.getAttribute("registerUser");
			//登録処理の呼び出し
			RegisterUserLogic logic = new RegisterUserLogic();
			logic.execute(registerUser);
			//不要となったsession scope内のインスタンスを除去
			session.removeAttribute("registerUser");
			//登録後forward先を設定
			forwardPath = "/WEB-INF/jsp/registerDone.jsp";

		}
		//設定されたforward先にforward
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//request parameter の取得
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		//登録するuserの情報を設定
		User registerUser = new User(id, name, pass);
		//session scopeに登録userを保存
		HttpSession session = request.getSession();
		session.setAttribute("registerUser", registerUser);
		//forward
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registerConfirm.jsp");
		dispatcher.forward(request, response);

	}

}
