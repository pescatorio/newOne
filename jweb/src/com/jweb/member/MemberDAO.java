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
	
	//ȸ�� �߰�
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
			pstmt.executeUpdate(); //sql ó�� �Ϸ� �޼���	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnect();}
	}
	
	//ȸ�� ���
	public ArrayList<Member> getListAll(){
		connDB();
		ArrayList<Member> memberList = new ArrayList<>();
		String sql = "SELECT * FROM t_member";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {  //db�� �ڷᰡ �ִٸ�
				Member member = new Member();  //member��ü�� �־
				member.setMemberId(rs.getString("memberId"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setJoinDate(rs.getDate("joinDate"));
				
				memberList.add(member);  //��� ����Ʈ�� member �ֱ�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return memberList;	//ȣ���ϴ� ���� ��ȯ��.
	}
	
	//ȸ�� 1�� ����ϱ� �޼���
	public Member getOneMember(String memId) {
		//DB�� ����
		connDB();
		//sql ó�� : select�� ��� executeQuery()
		String sql = "SELECT * FROM t_member WHERE memberId = ?";
		Member member = new Member();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery(); //db�� �ִ� ��ü
			rs.next();  //1���� ������
			
			member.setMemberId(rs.getString("memberId"));
			member.setPasswd(rs.getString("passwd"));
			member.setName(rs.getString("name"));
			member.setGender(rs.getString("gender"));
			member.setJoinDate(rs.getDate("joinDate"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return member;   //1���� ��� ��ü ��ȯ
	}
	
	//ȸ�� ���� �޼���
	public void deleteMember(String memId) {
		connDB();
		String sql = "DELETE FROM t_member WHERE memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			//���� : insert, update, delete - executeUpdate()
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	//ȸ�� ���� �޼���
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
			//����
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnect();}
	}
	
	//ȸ�� �α��� üũ - ���̵�, ��й�ȣ ��ġ: 1, ���̵� ����ġ: 0, ��й�ȣ ����ġ : -1
	public int login(String memberId, String passwd) {
		connDB();
		String sql = "SELECT memberId, passwd FROM t_member WHERE memberId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			if(rs.next()) {  //���̵� ��ġ
				String dbPw = rs.getString("passwd");
				if(dbPw.equals(passwd)) { //�Ű��� ���޹��� �����ȣ ���� ��
					return 1;  //��й�ȣ ��ġ
				}else {
					return -1;  //��й�ȣ ����ġ
				}
			}else {
				return 0;  //���̵� ����ġ
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectRS();
		}
		return -2;   //�����ͺ��̽� ����
	}//�α��� üũ
	
	//������ memberId�� �̸� �������� �޼���
	public String getLoginNameById(String memberId) {
		connDB();
		String sql ="SELECT * FROM t_member WHERE memberId = ?";
		String name = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			//���� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {disconnectRS();}
		return name;  //�̸� ��ȯ
	}//�޼��� �ݱ�
	
	
	
	
	
}//dao Ŭ���� �ݱ�













