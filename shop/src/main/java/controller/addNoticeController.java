package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.NoticeService;
import vo.Notice;

/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/addNotice")
public class addNoticeController extends HttpServlet {
	// addNotice Form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사
		HttpSession session = request.getSession();
		 //로그인 안되어 있거나 user가 Employee가 아니면 로그인 폼으로...
		if(session.getAttribute("Id") == null || (!(session.getAttribute("user").equals("employee"))) ){
			response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
			return;
	}
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/notice/addNoticeForm.jsp");
		rd.forward(request, response);
	}

	// addNotice Action
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 검사
		HttpSession session = request.getSession();
		 // 로그인 안되어 있거나 user가 Employee가 아니면 로그인 폼으로...
		if(session.getAttribute("Id") == null || (!(session.getAttribute("user").equals("employee"))) ){
			response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
			return;
	}
		
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 값 받아오기
		String noticeTitle=request.getParameter("noticeTitle");
		String noticeContent= request.getParameter("noticeContent");
		String updateDate = request.getParameter("updateDate");
		String createDate = request.getParameter("createDate");
		
		//디버깅
		System.out.println("noticeTitle : " + noticeTitle);
		System.out.println("noticeContent : " + noticeContent);
		System.out.println("updateDate : " + updateDate);
		System.out.println("createDate : " + createDate);
		
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setUpdateDate(updateDate);
		notice.setCreateDate(createDate);
		// 객체생성
		int addNotice = 0;
		NoticeService noticeService = new NoticeService();
		
		try {
			addNotice = noticeService.addNotice(notice);
			if (addNotice == 1) {
				System.out.println("등록성공");
				response.sendRedirect(request.getContextPath() + "/NoticeList");
			}else {
				System.out.println("등록실패");
				response.sendRedirect(request.getContextPath() + "/NoticeList");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
