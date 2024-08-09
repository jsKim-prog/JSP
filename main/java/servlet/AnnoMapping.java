package servlet;

import java.io.IOException;
import javax.servlet.ServletConfig; //환경설정
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; //14줄의 url 담당
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AnnoMapping
 */
@WebServlet("/AnnoMapping") //http://localhost:포트/컨텍스트루트/AnnoMapping 이라는 주소가 생김
//위 주소로 들어오면 AnnoMapping 이라는 클래스가 응답을 한다.
public class AnnoMapping extends HttpServlet { //extends HttpServlet 서블릿을 상속받아 활용
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnoMapping() { 
        super();
      //기본생성자로 부모인 HttpServlet 클래스를 생성한다. super();
      //생략시 자동으로 만들어짐  
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// 초기화를 담당하는 메서드
		//jdbc 경우에는 1, 2단계를 적용하면 좋다.
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// 톰캣 종료시 담당
		// jdbc인 경우에는 5단계를 적용하면 좋다.
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청이 들어온 모든 메서드의 응답을 담당
		// jdbc인 경우에는 3, 4단계를 적용하면 좋다.
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청이 get 메서드에 대한 응답을 담당한다.
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청이 post 메서드에 대한 응답을 담당한다.
		doGet(request, response);
	}

}
