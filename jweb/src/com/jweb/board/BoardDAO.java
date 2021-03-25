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
	private static String username = "jweb";    //�����(DB)
	private static String password = "4321";  //��й�ȣ
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//DB ���� �޼���
	private void connDB() {
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//DB ���� ���� - pstmt, conn ����
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
	
	//DB ���� ���� - rs. pstmt, conn ����
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
		
		//�� �� �߰�
		public void insertBoard(Board board) {
			connDB();
			String sql = "INSERT INTO t_board (bnum, title, content, memberId)"
					   + " VALUES (bSeq.NEXTVAL, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getMemberId());
				//����
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		
		//�Խñ� ��ü ��� ��� �޼���
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
					
					//ArrayList�� board ���
					boardList.add(board);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnectRS();}
			return boardList;
		}
		
		//ȸ�� 1�� �� ���� �޼���
		public Board getOneBoard(int bnum) {
			connDB();
			String sql = "SELECT * FROM t_board WHERE bnum = ?";
			Board board = new Board();
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum);
				//���� ���� 
				rs = pstmt.executeQuery();
				rs.next();    //1���� ����
				
				board.setBnum(rs.getInt("bnum"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getDate("regDate"));
				board.setMemberId(rs.getString("memberId"));
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnectRS();}
			return board;
		}//�޼��� �ݱ�
				
		//ȸ�� ����
		public void deleteBoard(int bnum) {
			connDB();
			String sql = "DELETE FROM t_board WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum);
				//���� ����
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {disconnect();}
		}//�޼��� �ݱ�
		
		//�Խñ� ���� - ���� -> �����Ѵٰ� ����(insert�� �����)
		public void updateBoard(Board board) {
			connDB();
			String sql = "UPDATE t_board SET title=?, content=? WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getBnum());
				//���� ����
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}//update �ݱ�
		
		//��ȸ�� 1����
		public void updateHit(int bnum) {
			connDB();
			String sql = "SELECT hit FROM t_board WHERE bnum = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bnum); //�۹�ȣ ���ε�(����)
				//���� ����
				rs = pstmt.executeQuery();
				int hit = 0;
				if(rs.next()) { //��ȸ�� 1����
					hit = rs.getInt("hit") + 1;
				}
				
				//update
				sql = "UPDATE t_board SET hit = ? WHERE bnum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, hit);
				pstmt.setInt(2, bnum);
				//���� ����
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnectRS();
			}
		}//updatHit()
		
}//class �ݱ�
