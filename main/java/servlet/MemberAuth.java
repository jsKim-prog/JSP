package servlet;

import java.io.IOException;

//import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memberShip.MemberDAO;
import memberShip.MemberDTO;

//@WebServlet("/login.mbc") // http://localhost:board-jsp/login.mbc
//->web.xml에 내용 있으면 없어도 됨
public class MemberAuth extends HttpServlet {
	// 서블릿을 사용한 로그인 처리방법
	// DB에 있는 회원이면 방가방가 처리
	// DB에 회원이 없으면 귀하는 회원이 아님 처리
	// DB에 없지만 xml에 adminID가 있으면 최고 관리자 임을 출력

	// 필드
	MemberDAO dao; // 회원용 jdbc

	@Override
	public void init() throws ServletException {
		// jdbc 1, 2단계
		ServletContext application = this.getServletContext(); // 톰캣 web.xml 을 활용

		String driver = application.getInitParameter("OracleDriver");
		String url = application.getInitParameter("OracleUrl");
		String id = application.getInitParameter("OracleId");
		String pw = application.getInitParameter("OraclePw");

		dao = new MemberDAO(driver, url, id, pw);
	}

	@Override
	public void destroy() {
		// jdbc 5단계 담당
		dao.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// jsp에서 넘어온 id, pw를 처리
		
		// DB에 있는 회원이면 방가방가 처리
		// DB에 회원이 없으면 귀하는 회원이 아님 처리
		// DB에 없지만 xml에 adminID가 있으면 최고 관리자 임을 출력
		
		String admin_id = this.getInitParameter("admin_id");
		//web.xml에 있는 id를 가져옴 -> kkw
		
		String id = req.getParameter("id"); //jsp 폼에서 넘어온 값
		String pass = req.getParameter("pass");
		
		MemberDTO memberDTO = dao.getMemberDTO(id, pass); //dao 메서드 결과를 dto로 받음
		String memberID = memberDTO.getId(); //찾아온 객체의 id를 넣음
		
		if(memberID != null) {
			//있으면
			req.setAttribute("authMessage", memberDTO.getName()+" 님 방가방가");
			
		}else {
			//db에 없으면
			if(admin_id.equals(id)) {
				req.setAttribute("authMessage", admin_id+" 관리자님 로그인 하셨습니다.");
			}else {
				req.setAttribute("authMessage", "귀하는 회원이 아닙니다. 가입하세요.");
			}
		}//--if()
	req.getRequestDispatcher("/13Servlet/MemberAuth.jsp").forward(req, resp);
	//처리후 응답을 보낼 주소와 객체를 전달함
	}//--service()
}
