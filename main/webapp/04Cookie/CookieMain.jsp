<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CookieMain.jsp : 쿠키 생성 테스트</title>
</head>
<body>
	<h2>1. 쿠키 생성</h2>
	<%
	Cookie cookie = new Cookie("myCookie", "쿠키는치토스가짱입니다.");
	//                         setName,    setValue
	cookie.setPath("/"); // http://192.168.111.101.80/board-jsp/  ->마지막/
	//cookie.setPath(request.getContextPath());//컨텍스트 루트(/board-jsp/)
	cookie.setMaxAge(3600); //60초*60분 = 1시간
	response.addCookie(cookie);
	System.out.println(cookie.getPath()); //console에 경로 찍기
	out.print("쿠키생성 완료");

	Cookie cookie2 = new Cookie("yourCookie", "쿠키는초코가짱입니다.");
	//                         setName,    setValue
	//cookie2.setPath("/"); // http://192.168.111.101.80/board-jsp/  ->마지막/
	cookie2.setPath(request.getContextPath());//컨텍스트 루트(/board-jsp/)
	cookie2.setMaxAge(3600); //60초*60분 = 1시간
	response.addCookie(cookie2);
	System.out.println(cookie2.getPath()); //console에 경로 찍기
	out.print("쿠키2생성 완료");
	%>

	<h2>2. 쿠키 생성 후 결과확인용 테스트</h2>
	<%
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {//쿠키가 있으면
		for (Cookie c : cookies) {//배열에 있는 모든값을 Cookie 타입의 c변수로 가져옴
			String cookieName = c.getName();
			String cookieValue = c.getValue();
			int cookieAge = c.getMaxAge();
			out.print("쿠키의 이름 : "+cookieName+":");
			out.print("쿠키의 값 : "+cookieValue+":");
			out.print("쿠키의 유효시간(초) : "+cookieAge+"<br>");
		}
	} else {//쿠키가 없으면
		out.print("현재 쿠키가 없습니다.");
	}
	%>
	
	<h2>3. 페이지 이동 후 쿠키값 확인</h2>
	<a href = "CookieResult.jsp">클릭시 이동합니다.</a>
</body>
</html>