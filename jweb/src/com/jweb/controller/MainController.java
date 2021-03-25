package com.jweb.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jweb.board.Board;
import com.jweb.board.BoardDAO;
import com.jweb.member.Member;
import com.jweb.member.MemberDAO;

@WebServlet("*.do")  //do Ȯ������ ��� ���Ͽ� ����
public class MainController extends HttpServlet{

	private static final long serialVersionUID = 1004;
	MemberDAO memberDAO;
	BoardDAO boardDAO;
	
	@Override
	public void init() throws ServletException { //�ʱ�ȭ
		memberDAO = new MemberDAO(); //memberDAO ��ü ����
		boardDAO = new BoardDAO();   //boardDAO ��ü ����
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);  // ��. ?memberId=river(get���)
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//Ŭ���̾�Ʈ�� ��û path ���� ����
		String uri = request.getRequestURI();
		System.out.println(uri);
		String command = uri.substring(uri.lastIndexOf("/"));  //path ���
		
		System.out.println(command);
		String nextPage = null;
		
		if(command.equals("/memberForm.do")) {		//ȸ������ ��������û
			nextPage = "/memberForm.jsp";
		}else if(command.equals("/memberAdd.do")) {
			//�� �Է� �ڷ� ����
			String memberId = request.getParameter("memberId");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//Member ��ü�� set()
			Member member = new Member();
			member.setMemberId(memberId);
			member.setPasswd(passwd);
			member.setName(name);
			member.setGender(gender);
			
			//dao - ȸ�� �߰� ó��
			memberDAO.addMember(member);
			
			//model�� member ����
			request.setAttribute("member", member);
			request.setAttribute("msg", "join");
			
			//view
			nextPage = "/memberResult.jsp";
		}else if(command.equals("/loginForm.do")) {
			nextPage = "/loginForm.jsp";
		}else if(command.equals("/loginProcess.do")) {
			//���� �Էµ� �ڷ� ����
			String id = request.getParameter("memberId");
			String pwd = request.getParameter("passwd");
			
			//dao - login() ȣ��
			int loginResult = memberDAO.login(id, pwd);
			String name = memberDAO.getLoginNameById(id);
			
			//���̵�, ��� ��ġ�ϸ� ���� �߱�
			if(loginResult==1) {
				HttpSession session = request.getSession();
				session.setAttribute("sessionId", id);
				session.setAttribute("name",name);
				request.setAttribute("msg", "login");
			}
			
			//model and view
			request.setAttribute("loginResult", loginResult);
			request.setAttribute("name", name);
			nextPage = "/memberResult.jsp";
		}else if(command.equals("/logout.do")) {
			//���� ����
			HttpSession session = request.getSession();
			session.invalidate();
			
			nextPage = "main.jsp";
		}else if(command.equals("/memberView.do")) { //ȸ�� ���� ��û
			// �α��ε� 1�� ȸ�� ��������
			HttpSession session = request.getSession();
			String sessionId = (String)session.getAttribute("sessionId");
			
			//dao - getOneDB()
			Member member = memberDAO.getOneMember(sessionId);
			
			//model and view
			request.setAttribute("member", member);
			nextPage = "/memberView.jsp";
		}else if(command.equals("/memberDelete.do")) {
			//���� ���ڿ�(memberId)�ޱ�
			String memberId = request.getParameter("memberId");
			//dao - deleteMember()ȣ��
			memberDAO.deleteMember(memberId);
			//model and view
			request.setAttribute("msg","delete");	//�����Ǽ� �������� ����.
			nextPage = "/memberResult.jsp";
		}else if(command.equals("/memberUpdate.do")) {
			String memberId = request.getParameter("memberId");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String gender=request.getParameter("gender");
			
			//member��ü ����
			Member member = new Member();
			member.setMemberId(memberId);
			member.setPasswd(passwd);
			member.setName(name);
			member.setGender(gender);
			//dao -updateMember
			memberDAO.updateMember(member);
			//model and view
			request.setAttribute("member", member);
			request.setAttribute("msg","update");
			nextPage="/memberResult.jsp";
		}else if(command.equals("/boardList.do")) {
			//
			ArrayList<Board> boardList = boardDAO.getListAll();
			
			//model and view
			request.setAttribute("boardList",boardList);
			nextPage = "/boardList.jsp";
		}else if(command.equals("/boardWriteForm.do")) {	//�۾��� ��������û
			nextPage="/boardWriteForm.jsp";
		}else if(command.equals("/boardWriteAdd.do")) {	//�۾��� ó����û
			//�� �Է��ڷ� ����
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			//���� id �ޱ�
			HttpSession session = request.getSession();
			String sessionId = (String) session.getAttribute("sessionId");
			
			//Board ��ü�� set
			Board board= new Board();
			board.setTitle(title);
			board.setContent(content);
			board.setMemberId(sessionId);
			
			//dao insert
			boardDAO.insertBoard(board);
			
			//model and view
			request.setAttribute("board", "board");
			nextPage="/boardList.do";
		}else if(command.equals("/boardView.do")) {
			//bnum �޾ƿ���
		int bnum = Integer.parseInt(request.getParameter("bnum"));
		
		//dao
		Board board = boardDAO.getOneBoard(bnum);
		
		//model and view
		request.setAttribute("board",board);
		nextPage = "/boardView.jsp";
	}
		
		//������ ->view�� ������
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}//doPost() �ݱ�
	
	
	
}//class �ݱ�
