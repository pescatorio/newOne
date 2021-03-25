package com.jweb.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {
	private static String driverClass = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String username = "jweb";    //사용자(DB)
	private static String password = "4321";  //비밀번호
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//DB 연결 메서드
	private void connDB() {
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//DB 연결 종료 - pstmt, conn 종료
	private void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//DB 연결 종료 - rs. pstmt, conn 종료
		private void disconnectRS() {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		//새 글 추가
		public void insertBoard(Board board) {
			connDB();
			String sql = "INSERT INTO t_board (bnum, title, content, memberId)"
					   + " VALUES (bSeq.NEXTVAL, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getMemberId());
				//실행
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		
		//게시글 전체 목록 출력 메서드
		public ArrayList<Board> getListAll(){
			connDB();
			ArrayList<Board> boardList = new ArrayList<>();
			String sql = "SELECT * FROM t_board ORDER BY bnum DESC";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Board board = new Board();
					board.setBnum(rs.getInt("bnum"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setRegDate(rs.getDate("regDate"));
					board.setHit(rs.getInt("hit"));
					board.setMemberId(rs.getString("memberId"));
					
					//ArrayList에 board 담기
					boardList.add(board);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnectRS();}
			return boardList;
		}
		
		//회원 1명 상세 보기 메서드
		public Board getOneBoard(int bnum) {
			connDB();
			String sql = "SELECT * FROM t_board WHERE bnum = ?";
			Board board = new Board();
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum);
				//쿼리 실행 
				rs = pstmt.executeQuery();
				rs.next();    //1명이 있음
				
				board.setBnum(rs.getInt("bnum"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getDate("regDate"));
				board.setMemberId(rs.getString("memberId"));
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnectRS();}
			return board;
		}//메서드 닫기
				
		//회원 삭제
		public void deleteBoard(int bnum) {
			connDB();
			String sql = "DELETE FROM t_board WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum);
				//쿼리 실행
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnect();}
		}//메서드 닫기
		
		//게시글 수정 - 수정 -> 가입한다고 생각(insert와 비슷함)
		public void updateBoard(Board board) {
			connDB();
			String sql = "UPDATE t_board SET title=?, content=? WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getBnum());
				//쿼리 실행
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}//update 닫기
		
		//조회수 1증가
		public void updateHit(int bnum) {
			connDB();
			String sql = "SELECT hit FROM t_board WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum); //글번호 바인딩(세팅)
				//쿼리 실행
				rs = pstmt.executeQuery();
				int hit = 0;
				if(rs.next()) { //조회수 1증가
					hit = rs.getInt("hit") + 1;
				}
				
				//update
				sql = "UPDATE t_board SET hit = ? WHERE bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, hit);
				pstmt.setInt(2, bnum);
				//쿼리 실행
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnectRS();
			}
		}//updatHit()
		
}//class 닫기
