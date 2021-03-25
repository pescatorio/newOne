package com.jweb.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO {
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
	
	//회원 추가
	public void addMember(Member member) {
		connDB();
		String sql = "INSERT INTO t_member(memberId, passwd, name, gender) "
				   + " VALUES (?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getGender());
			pstmt.executeUpdate(); //sql 처리 완료 메서드	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnect();}
	}
	
	//회원 목록
	public ArrayList<Member> getListAll(){
		connDB();
		ArrayList<Member> memberList = new ArrayList<>();
		String sql = "SELECT * FROM t_member";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {  //db에 자료가 있다면
				Member member = new Member();  //member객체에 넣어서
				member.setMemberId(rs.getString("memberId"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setJoinDate(rs.getDate("joinDate"));
				
				memberList.add(member);  //어레이 리스트에 member 넣기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return memberList;	//호출하는 곳에 반환됨.
	}
	
	//회원 1명 출력하기 메서드
	public Member getOneMember(String memId) {
		//DB에 연결
		connDB();
		//sql 처리 : select일 경우 executeQuery()
		String sql = "SELECT * FROM t_member WHERE memberId = ?";
		Member member = new Member();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery(); //db에 있는 객체
			rs.next();  //1명이 있으면
			
			member.setMemberId(rs.getString("memberId"));
			member.setPasswd(rs.getString("passwd"));
			member.setName(rs.getString("name"));
			member.setGender(rs.getString("gender"));
			member.setJoinDate(rs.getDate("joinDate"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return member;   //1명의 멤버 객체 반환
	}
	
	//회원 삭제 메서드
	public void deleteMember(String memId) {
		connDB();
		String sql = "DELETE FROM t_member WHERE memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			//실행 : insert, update, delete - executeUpdate()
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	//회원 수정 메서드
	public void updateMember(Member member) {
		connDB();
		String sql = "UPDATE t_member"
				   + " SET passwd = ?, name = ?, gender = ?"
				   + " WHERE memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPasswd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setString(4, member.getMemberId());
			//실행
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnect();}
	}
	
	//회원 로그인 체크 - 아이디, 비밀번호 일치: 1, 아이디 불일치: 0, 비밀번호 불일치 : -1
	public int login(String memberId, String passwd) {
		connDB();
		String sql = "SELECT memberId, passwd FROM t_member WHERE memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if(rs.next()) {  //아이디 일치
				String dbPw = rs.getString("passwd");
				if(dbPw.equals(passwd)) { //매개로 전달받은 비빌번호 변수 비교
					return 1;  //비밀번호 일치
				}else {
					return -1;  //비밀번호 불일치
				}
			}else {
				return 0;  //아이디 불일치
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectRS();
		}
		return -2;   //데이터베이스 오류
	}//로그인 체크
	
	//인증된 memberId의 이름 가져오는 메서드
	public String getLoginNameById(String memberId) {
		connDB();
		String sql ="SELECT * FROM t_member WHERE memberId = ?";
		String name = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			//쿼리 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return name;  //이름 반환
	}//메서드 닫기
	
	
	
	
	
}//dao 클래스 닫기













