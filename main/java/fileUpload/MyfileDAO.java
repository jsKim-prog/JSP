package fileUpload;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import common.DBConnPool;

public class MyfileDAO extends DBConnPool { // 1,2,5단계 상속하여 활용
	// jdbc 1~5단계

	// 입력 메서드
	public int insertFile(MyfileDTO myfileDTO) {
		int result = 0;

		String query = "insert into myfile (idx, name, title, cate, ofile,sfile) "
				+ "values (seq_board_num.nextval, ?, ?, ?, ?,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, myfileDTO.getName());
			pstmt.setString(2, myfileDTO.getTitle());
			pstmt.setString(3, myfileDTO.getCate());
			pstmt.setString(4, myfileDTO.getOfile());
			pstmt.setString(5, myfileDTO.getSfile()); // 3단계 완성

			result = pstmt.executeUpdate(); // 4단계 정수로 리턴
		} catch (SQLException e) {
			System.out.println("MyfileDAO.insertFile() 메서드 예외발생");
			System.out.println("insert 쿼리문 확인");
			e.printStackTrace();
		}
		return result;
	}

	// 출력 메서드
	public List<MyfileDTO> myFileList(){
		List<MyfileDTO> fileList = new Vector<MyfileDTO>(); //멀티스레드용
		
		String query = "select * from myfile order by idx desc"; //모든 데이터 찾아오기(내림차순)
		
		try {
			pstmt = con.prepareStatement(query);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyfileDTO dto = new MyfileDTO();
				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setCate(rs.getString("cate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setPostdate(rs.getString("postdate"));
				
				fileList.add(dto); //리스트에 객체 삽입
			}						
		} catch (SQLException e) {
			System.out.println("MyfileDAO.myFileList() 메서드 예외발생");
			System.out.println("select 쿼리문 확인");
			e.printStackTrace();
		}	
		return fileList; // 테이블의 모든 값이 리스트의 객체로 리턴된다.
	}//--myFileList()

	// 수정 메서드

	// 삭제 메서드

}
