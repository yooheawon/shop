package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.CustomerService;
import service.SignService;

@WebServlet("/idckController")
public class IdCkController extends HttpServlet {
	private SignService signService;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 클라이언트에 전송할 데이터
		response.setContentType("application/json");
		// 요청값 처리
		String idck = request.getParameter("idck");
		// 디버깅
		System.out.println(idck + " <-- idck");
		// SignService 개체 생성 후 id에 signService 리턴값 넣기
		this.signService = new SignService();
	    String id = request.getParameter(idck);
		// id -> null -> idck사용가능 -> yes
		// id -> select값 -> idck사용불가 -> no
		
		Gson gson = new Gson();
		String jsonStr = "";
		if(id == null) {
			jsonStr = gson.toJson("y");
		} else {
			jsonStr = gson.toJson("n");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
	}
}
