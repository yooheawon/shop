package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.INoticeService;
import service.NoticeService;
import vo.Notice;

/**
 * Servlet implementation class NoticeList
 */
@WebServlet("/NoticeList")
public class NoticeListController extends HttpServlet {
	private INoticeService noticeService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션검사
		HttpSession session = request.getSession();
		if(session.getAttribute("Id") == null){
	  		response.sendRedirect(request.getContextPath() + "../loginForm.jsp?errorMsg=Not logged in");
	  		return;
	  	} else if(session.getAttribute("Id") != null && "customer".equals((String)session.getAttribute("user"))) {
	  		// 관리자가 아닌경우 막기
	  		response.sendRedirect(request.getContextPath() + "../loginForm.jsp?errorMsg=No permission");
	  	}
		
		// 컨트롤러
		//1. 요청받아 분석
		final int rowPerPage = 10;
		
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// 2) 서비스 레이어를 요청(메서드 호출) -> 모델값(자료구조) 구하기 위함 
		// new
		noticeService  = new NoticeService();
		Map<String, Object> map = null;
		try {
			map = noticeService.getNoticeList(rowPerPage, currentPage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("lastPage", map.get("lastPage"));
		request.setAttribute("list", map.get("list"));
		request.setAttribute("currentPage",currentPage);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/notice/noticeList.jsp").forward(request, response);
	}
}
