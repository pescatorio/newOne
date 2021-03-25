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

@WebServlet("*.do")  //do 확장자인 모든 파일에 매핑
public class MainController extends HttpServlet{

	private static final long serialVersionUID = 1004;
	MemberDAO memberDAO;
	BoardDAO boardDAO;
	
	@Override
	public void init() throws ServletException { //초기화
		memberDAO = new MemberDAO(); //memberDAO 객체 생성
		boardDAO = new BoardDAO();   //boardDAO 객체 생성
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);  // 예. ?memberId=river(get방식)
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글 인코딩
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//클랑이언트의 요청 path 정보 추출
		String uri = request.getRequestURI();
		System.out.println(uri);
		String command = uri.substring(uri.lastIndexOf("/"));  //path 경로
		
		System.out.println(command);
		String nextPage = null;
		
		if(command.equals("/memberForm.do")) {		//회원가입 페이지요청
			nextPage = "/memberForm.jsp";
		}else if(command.equals("/memberAdd.do")) {
			//폼 입력 자료 수집
			String memberId = request.getParameter("memberId");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//Member 객체에 set()
			Member member = new Member();
			member.setMemberId(memberId);
			member.setPasswd(passwd);
			member.setName(name);
			member.setGender(gender);
			
			//dao - 회원 추가 처리
			memberDAO.addMember(member);
			
			//model인 member 저장
			request.setAttribute("member", member);
			request.setAttribute("msg", "join");
			
			//view
			nextPage = "/memberResult.jsp";
		}else if(command.equals("/loginForm.do")) {
			nextPage = "/loginForm.jsp";
		}else if(command.equals("/loginProcess.do")) {
			//폼에 입력된 자료 수집
			String id = request.getParameter("memberId");
			String pwd = request.getParameter("passwd");
			
			//dao - login() 호출
			int loginResult = memberDAO.login(id, pwd);
			String name = memberDAO.getLoginNameById(id);
			
			//아이디, 비번 일치하면 세션 발급
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
			//세션 해제
			HttpSession session = request.getSession();
			session.invalidate();
			
			nextPage = "main.jsp";
		}else if(command.equals("/memberView.do")) { //회원 정보 요청
			// 로그인된 1명 회원 가져오기
			HttpSession session = request.getSession();
			String sessionId = (String)session.getAttribute("sessionId");
			
			//dao - getOneDB()
			Member member = memberDAO.getOneMember(sessionId);
			
			//model and view
			request.setAttribute("member", member);
			nextPage = "/memberView.jsp";
		}else if(command.equals("/memberDelete.do")) {
			//쿼리 문자열(memberId)받기
			String memberId = request.getParameter("memberId");
			//dao - deleteMember()호출
			memberDAO.deleteMember(memberId);
			//model and view
			request.setAttribute("msg","delete");	//삭제되서 보낼것은 없다.
			nextPage = "/memberResult.jsp";
		}else if(command.equals("/memberUpdate.do")) {
			String memberId = request.getParameter("memberId");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String gender=request.getParameter("gender");
			
			//member객체 생성
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
		}else if(command.equals("/boardWriteForm.do")) {	//글쓰기 페이지요청
			nextPage="/boardWriteForm.jsp";
		}else if(command.equals("/boardWriteAdd.do")) {	//글쓰기 처리요청
			//폼 입력자료 수집
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			//세션 id 받기
			HttpSession session = request.getSession();
			String sessionId = (String) session.getAttribute("sessionId");
			
			//Board 객체에 set
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
			//bnum 받아오기
		int bnum = Integer.parseInt(request.getParameter("bnum"));
		
		//dao
		Board board = boardDAO.getOneBoard(bnum);
		
		//model and view
		request.setAttribute("board",board);
		nextPage = "/boardView.jsp";
	}
		
		//포워딩 ->view로 보내기
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}//doPost() 닫기
	
	
	
}//class 닫기
