<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logout.jsp : form에서 넘어온 세션 삭제</title>
</head>
<body>
	<%
	/* //방법1 : 회원인증정보 속성으로 삭제
	session.removeAttribute("UserId");
	session.removeAttribute("UserName");
	session.removeAttribute("LastLoginTime"); */
	
	//방법2 : 전체 한꺼번에 삭제
	session.invalidate();
	
	//돌아가기
	response.sendRedirect("LoginForm.jsp");
	%>

</body>
</html>