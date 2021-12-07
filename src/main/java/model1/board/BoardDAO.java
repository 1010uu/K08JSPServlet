package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.ServletContext;
import common.JDBConnect; //DB연결을 위한 클래스 패키지가 다르므로 import

public class BoardDAO extends JDBConnect{
	
	//부모의 인자 생성자를 호출한다. 이때 application내장 객체를 매개변수로 전달.
	public BoardDAO(ServletContext application) {
		//내장 객체를 통해 web.xml에 작성된 컨텍스트 초기화 파라미터를 얻어온다.
		super(application);
	}
	
	/*
	 selectCount();
	 	: board테이블에 저장된 게시물의 갯수를 카운트하기 위한 메서드.
	 	카운트 한 결과값을 통해 목록에서 게시물의 순번을 출력한다.
	 */
	public int selectCount(Map<String, Object> map){
		int totalCount=0;//카운트 변수
		
		//쿼리문 작성
		String query = " SELECT COUNT(*) FROM board ";
		//검색어가 있는 경우 where절을 동적으로 추가한다.
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") +" "
					+" LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		try {
			//정적쿼리문(?가 없는 쿼리문) 실행을 위한 Statement객체 생성
			stmt = con.createStatement();
			//select쿼리문을 실행 후 ResultSet객체를 반환받음
			rs = stmt.executeQuery(query);
			//커서를 이동시켜 결과데이터를 읽음.
			rs.next();
			//결과값을 변수에 저장
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/*
	 selectList();
	 	: 목록에 출력할 게시물을 오라클로부터 추출하기 위한 쿼리문 실행
	 	(페이지 처리 없음)
	 */
	public List<BoardDTO> selectList(Map<String, Object> map){
		/*
		 board테이블에서 select한 결과데이터를 저장하기 위한 리스트 컬렉션.
		 여러가지의 List컬렉션 중 동기화가 보장되는 Vector를 사용.
		 */
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		/*
		 목록에 출력할 게시물을 추출하기 위한 쿼리문으로 항상 일련번호의
		 역순(내림차순)으로 정렬해야 한다. 게시판의 목록은 최근 게시물이
		 제일 앞에 노출되게 때문이다. 
		 */
		String query = " SELECT * FROM board ";
		//검색어가 있는 경우 where절을 추가함
		if(map.get("searchWord")!=null) {
			query += " WHERE " + map.get("searchField")+ " "
					+ " LIKE '%" + map.get("searchWord") + "%' "; 
		}
		query += " ORDER BY num DESC ";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			//추출된 결과에 따라 반복한다.
			while(rs.next()) {
				//하나의 레코드를 읽어서 DTO객체에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				//리스트 컬렉션에 DTO객체들을 추가한다.
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
	//사용자가 입력한 내용을 board테이블에 insert처리하는 메서드
	public int insertWrite(BoardDTO dto) {
		//입력 결과 확인용 변수
		int result=0;
		
		try {
			//인파라미터가 있는 쿼리문 작성(동적쿼리문)
			String query = "INSERT INTO board ( "
					+ " num, title, content, id, visitcount) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL, ?, ?, ?, 0)";
			
			//동적쿼리문 실행을 위한 prepared객체 생성
			psmt = con.prepareStatement(query);
			//순서대로 인파라미터 설정
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			//쿼리문 실행 : 실행 성공한다면 1이 반환된다. 실패하면 0
			result =psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 에외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//상세보기를 위해 특정 일련번호에 해당하는 게시물을 인출.
	public BoardDTO selectView(String num) {
		
		BoardDTO dto = new BoardDTO();
		
		//join을 이용해서 member테이블의 name컬럼까지 가져온다.
		String query = "SELECT B.*, M.name " 
			    + " FROM board B INNER JOIN member M " 
			    + " ON B.id=M.id "
			    + " WHERE num=?";
			    
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			//일련번호는 중복되지 않으므로 if문에서 처리하면 된다.
			if(rs.next()) {//ResultSet에서 커서를 이동시켜 레코드를 읽은후
				//DTO객체에 레코드의 내용을 저장한다.
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate")); //날짜 타입이므로 getDate()사용
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	//게시물의 조회수를 1씩 증가시키는 메서드
	public void updateVisitCount(String num) {
		//visitcount컬럼은 number타입이므로 덧셈이 가능하다.
		String query = "UPDATE BOARD SET "
				+ " visitcount = visitcount+1 "
				+ " WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
}