package model1.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect {

	// 생성자를 이용해서 1단계, 2단계를 처리
	public BoardDAO(ServletContext application) {
		super(application); // 3번째 개선한 jdbc 연결
	}

	// board테이블의 게시물 개수를 알아와야 함!
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0; // 리턴값
		String query = "select count(*) from board ";
		if (map.get("searchWord") != null) {
			// 검색어가 있으면
			query += "where " + map.get("seachField") + " like '%" + map.get("searchWord") + "%'";
			// seachField : 제목, 내용, 작성자
			// searchWord : input text로 넘어온 글자
			// select count(*) from board where 제목 like '%딸기%';
		} // 검색어가 있으면 조건이 추가된다.
		try {
			statement = connection.createStatement(); // 쿼리문 연결
			resultSet = statement.executeQuery(query);// 쿼리문 실행해서 결과를 표로 받음
			resultSet.next();
			totalCount = resultSet.getInt(1); // 첫 번째 컬럼 값을 가져옴
			System.out.println("totalCount : " + totalCount);
		} catch (SQLException e) {
			System.out.println("BoardDAO.selectCount() 메서드 오류");
			System.out.println("게시물 개수를 구하는 오류 발생");
			e.printStackTrace();
		}
		return totalCount;
	}

	// 게시물의 리스트를 출력
	public List<BoardDTO> selectList(Map<String, Object> map) {
		//map -> seachField, searchWord, start, end가 전달된다.
		List<BoardDTO> listBoardDTO = new Vector<BoardDTO>();

		// 3단계
		String query = "select * from (select Tb.*, rownum rNum from (select * from board ";
		// 조건 추가
		if (map.get("searchWord") != null) {
			// 검색어가 있으면
			query += "where " + map.get("seachField") + " like '%" + map.get("searchWord") + "%'";
			// seachField : 제목, 내용, 작성자
			// searchWord : input text로 넘어온 글자
			// select count(*) from board where 제목 like '%딸기%';
		} // 검색어가 있으면 조건이 추가된다.

		query += " order by num desc ) Tb ) where rNum between ? and ?"; // 정렬기준 추가
		// 3단계 쿼리문 완성

		try {
			preparedStatement = connection.prepareStatement(query);// 3단계
			preparedStatement.setString(1, map.get("start").toString()); // 시작번호
			preparedStatement.setString(2, map.get("end").toString()); // 끝번호

			resultSet = preparedStatement.executeQuery(); //4단계

			while (resultSet.next()) {
				BoardDTO boardDTO = new BoardDTO(); // 빈객체 생성
				boardDTO.setNum(resultSet.getString("num"));
				boardDTO.setTitle(resultSet.getString("title"));
				boardDTO.setContent(resultSet.getString("content"));
				boardDTO.setId(resultSet.getString("id"));
				boardDTO.setPostdate(resultSet.getDate("postdate"));
				boardDTO.setVisitcount(resultSet.getString("visitcount")); // 객체에 값 삽입 완료
				// name 필드 null
				listBoardDTO.add(boardDTO); // 위에서 만든 객체를 리스트에 넣음
			} // --while()
		} catch (SQLException e) {
			System.out.println("BoardDAO.selectList() 메서드 오류");
			System.out.println("board 테이블 모든 리스트 출력 오류");
			e.printStackTrace();
		}

		return listBoardDTO;
	}

	// 게시글 등록용 메서드
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		// 3단계
		String query = "insert into board (num, title, content, id, visitcount) values (seq_board_num.nextval, ?, ?, ?, 0)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, dto.getTitle());
			preparedStatement.setString(2, dto.getContent());
			preparedStatement.setString(3, dto.getId()); // 쿼리문 완성

			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("BoardDAO.insertWrite() 메서드 예외발생");
			System.out.println("쿼리문을 확인하세요.");
			e.printStackTrace();
		}

		return result;
	}

	// 게시글의 제목을 클릭했을 때 상세보기 페이지
	public BoardDTO selectView(String num) {
		// 메서드 호출 시 입력은 num(board의 pk) 받고 가져온 데이터를 board DTO 객체에 넣어 리턴
		BoardDTO viewDTO = new BoardDTO();
		// 3단계 (쿼리문 생성) member pk -> board fk
		// String query = "select * from board where num =?"; //작성자임?

		// member에 있는 작성자를 가져올 수 있도록 join 처리용
		String query = "select B.*, M.name from member M inner join board B on M.id = B.id where num = ?";
		// member 테이블의 별칭은 M, board 테이블의 별칭은 B로 선언
		// 부모 테이블은 M에 이너조인 B를 이용하고 id가 같은 자료를 찾음
		// 조건은 파라미터로 받은 bno 이용
		// 찾아온 값은 board의 모든것 + member의 name을 가져옴->dto에 name 필드 추가

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, num);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				viewDTO.setNum(resultSet.getString("num"));
				viewDTO.setTitle(resultSet.getString("title"));
				viewDTO.setContent(resultSet.getString("content"));
				viewDTO.setId(resultSet.getString("id"));
				viewDTO.setPostdate(resultSet.getDate("postdate"));
				viewDTO.setVisitcount(resultSet.getString("visitcount"));
				viewDTO.setName(resultSet.getString("name"));// dto객체에 값 저장
				System.out.println(viewDTO.toString());
			} // --if()
		} catch (SQLException e) {
			System.out.println("BoardDAO.selectView() 메서드 예외발생");
			System.out.println("쿼리문을 확인하세요.");
			e.printStackTrace();
		}
		return viewDTO;
	}// --selectView()

	// 리스트에서 제목을 클릭했을 때 조회수 증가용 코드
	public void updateVisitCount(String num) {
		String query = "update board set visitcount = visitcount+1 where num =?";
		// 조건이 num값에 대한 visitcount를 1씩 증가
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, num);
			preparedStatement.executeQuery(); // 실행만 하고 결과는 안본다.
		} catch (SQLException e) {
			System.out.println("BoardDAO.updateVisitCount() 메서드 예외발생");
			System.out.println("쿼리문을 확인하세요.");
			e.printStackTrace();
		}
	}// --updateVisitCount()

	// 수정할 객체를 받아서 성공시 1개의 값을 수정했다는 리턴
	public int updateEdit(BoardDTO boardDTO) {
		int result = 0;

		String query = "update board set title = ?, content =? where num =? ";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, boardDTO.getTitle());
			preparedStatement.setString(2, boardDTO.getContent());
			preparedStatement.setString(3, boardDTO.getNum());

			result = preparedStatement.executeUpdate(); // 4단계
			System.out.println("수정개수 : " + result);

		} catch (SQLException e) {
			System.out.println("BoardDAO.updateEdit() 메서드 예외발생");
			System.out.println("쿼리문을 확인하세요.");
			e.printStackTrace();
		}
		return result;
	}// --updateEdit()

	// 삭제 메서드 dto를 받아서 삭제 후에 int로 리턴
	public int deletePost(BoardDTO boardDTO) {
		int result = 0;

		String query = "delete from board where num = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, boardDTO.getNum());

			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("BoardDAO.deletePost() 메서드 예외발생");
			System.out.println("쿼리문을 확인하세요.");
			e.printStackTrace();
		}

		return result;
	}// --deletePost()

}
