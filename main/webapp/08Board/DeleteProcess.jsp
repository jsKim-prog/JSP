<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>
<%
String num = request.getParameter("num");//삭제할 번호
BoardDTO boardDTO = new BoardDTO();
BoardDAO boardDAO = new BoardDAO(application);
boardDTO = boardDAO.selectView(num); //번호로 객체를 가져와 dto에 넣음

String seessionId = session.getAttribute("UserId").toString(); //로그인 이름 가져오기
int delResult = 0;// 삭제성공여부 1: 성공/0 : 실패
if (seessionId.equals(boardDTO.getId())) { //로그인한 사용자이름 일치여부
	//작성자 본인이면 삭제
	boardDTO.setNum(num);
	delResult = boardDAO.deletePost(boardDTO); //객체를 넣어 삭제하고 int 받음(3,4단계)
	boardDAO.close();//5단계
	
	if(delResult==1){
		//삭제성공
		JSFunction.alertLocation("삭제되었습니다.", "List.jsp", out);
	}else{
		//삭제실패
		JSFunction.alertBack("삭제실패, 뒤로갑니다.", out);
		return;
	}
} else {
	//본인이 아니면 삭제안됨
	JSFunction.alertBack("본인만 삭제 가능합니다. 뒤로 갑니다.", out);
	return;
}
%>
