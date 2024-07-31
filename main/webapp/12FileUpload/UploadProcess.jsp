<%@page import="fileUpload.MyfileDAO"%>
<%@page import="fileUpload.MyfileDTO"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.File"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<!--cos.jar  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
/* method="post" enctype="multipart/form-data"  */
/* 필요요소 4가지 cos.jar : request, 저장경로, 단일파일크기, 인코딩방식  */

// 1단계 : cos.jar 연결
String saveDirectory = application.getRealPath("/Uploads");
//저장할 디렉토리 http://192.168.111.101:80/board-jsp/Uploads
int maxPostSize = 1024 * 1024 * 100; //파일 최대크기 = 100메가바이트(kb*mb*gb)
String encoding = "UTF-8";

try {
	MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);

	System.out.println("saveDirectory : " + saveDirectory);
	System.out.println("maxPostSize : " + maxPostSize);

	// 2단계 : ofile, sfile 결정
	// 2. 새로운 파일명 생성
	String fileName = mr.getFilesystemName("attachedFile"); // 현재 파일 이름
	String ext = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
	String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
	String newFileName = now + ext; // 새로운 파일 이름("업로드일시.확장자")

	System.out.println("fileName : " + fileName);
	System.out.println("ext : " + ext);
	System.out.println("now : " + now);
	System.out.println("newFileName : " + newFileName);

	// 3. 파일명 변경
	File oldFile = new File(saveDirectory + File.separator + fileName);
	File newFile = new File(saveDirectory + File.separator + newFileName);
	oldFile.renameTo(newFile);

	System.out.println("oldFile : " + oldFile);
	System.out.println("newFile : " + newFile);

	// 4. FileUploadMain에서 넘어온 폼값처리
	String name = mr.getParameter("name");
	String title = mr.getParameter("title");
	String[] cateArray = mr.getParameterValues("cate");
	StringBuffer cateBuf = new StringBuffer();
	if (cateArray == null) {
		cateBuf.append("선택없음");
	} else {
		for (String s : cateArray) {
	cateBuf.append(s + ", ");
		} //사진, 과제, 워드, 음원,
	}

	//5. dto에 4번 값 넣기
	MyfileDTO dto = new MyfileDTO();
	dto.setName(name);
	dto.setTitle(title);
	dto.setCate(cateBuf.toString());
	dto.setOfile(fileName);
	dto.setSfile(newFileName);

	// 6. dao를 통해 데이터베이스에 반영
	MyfileDAO dao = new MyfileDAO();
	dao.insertFile(dto);
	dao.close();

	// 7. 파일목록 jsp로 리디랙션
	response.sendRedirect("FileList.jsp"); //성공시 되돌아감

} catch (Exception e) {
	System.out.println("UloadProcess.jsp 예외발생");
	e.printStackTrace();
	request.setAttribute("errorMessage", "파일 업로드 오류");
	request.getRequestDispatcher("FileUploadMain.jsp").forward(request, response);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UploadProcess.jsp : cos.jaf를 활용하여 파일처리하고 dao로 연결</title>
</head>
<body>

</body>
</html>