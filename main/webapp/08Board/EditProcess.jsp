<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %><!--로그인여부 확인  -->
<%
	String num = request.getParameter("num");
	String title = request.getParameter("title");
	String content = request.getParameter("content"); //form값 저장
	BoardDTO boardDTO = new BoardDTO();
	boardDTO.setNum(num);
	boardDTO.setTitle(title);
	boardDTO.setContent(content);//빈객체 생성후 값 저장
	
	BoardDAO boardDAO = new BoardDAO(application); //1,2단계
	int affected = boardDAO.updateEdit(boardDTO); //3,4단계
	boardDAO.close(); //5단계
	
	//성공/실패 처리
	if(affected==1){
		//성공
		response.sendRedirect("View.jsp?num="+boardDTO.getNum()); //View.jsp?num=3
	}else{
		//실패
		JSFunction.alertBack("수정실패", out); //메시지 출력 후 뒤로가기
	}
%>    
    
