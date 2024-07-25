<%@page import="common.DBConnPool"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ExeUpdate.jsp:회원추가 테스트</title>
<!--입력받은 값을 sql로 작성하여db로 넘기고 결과를 받아 되돌려준다.  -->
</head>
<body>
	<%
	//1, 2단계 : 먼저 pool에서 연결정보 받는다.
	DBConnPool pool = new DBConnPool(); 
	//3 단계 : 입력받은 값 넘기기(쿼리작성)
	String sql = "insert into member (id, pass, name) values (?, ?, ?)";
	pool.pstmt = pool.con.prepareStatement(sql); 
	//연결 pool의 preparestatement 객체에 sql을 넣어 연결 pool의 연결객체를 통해 보낸다.
	pool.pstmt.setString(1, request.getParameter("newId"));
	pool.pstmt.setString(2, request.getParameter("newPw"));
	pool.pstmt.setString(3, request.getParameter("newName"));
	
	//4단계 : 결과 받기
	int result = pool.pstmt.executeUpdate(); //pool의 preparestatement 객체가 쿼리실행한 결과(정수)를 result에 넣는다.
	//pool.rs = pool.pstmt.executeQuery(); //pool의 resultset에 pool의 preparestatement 객체가 쿼리실행한 결과를 넣는다.
	
	if(result>0){
		System.out.println(result+"행 입력 성공");
		out.println(request.getParameter("newName")+"님 환영합니다."); //알럿창으로 수정
	}else{
		out.println("회원가입에 실패했습니다."); 
	}
	//5단계
	pool.close();	
	%>
</body>
</html>