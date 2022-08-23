<%@page import="service.GoodsService"%>
<%@page import="vo.GoodsImg"%>
<%@page import="vo.Goods"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 사진 용량
	int max = 10*1024*1024;

	// 업로드 폴더 지정
	String dir = request.getServletContext().getRealPath("/upload");
	System.out.println("addGoodsAction.jsp dir : " + dir);
	// 원래 request에 래핑 (request, 경로, 파일사이즈, 인코딩, 이름재설정)
	MultipartRequest multiRequest = new MultipartRequest(request, dir, max, "utf-8", new DefaultFileRenamePolicy());
	
	// updateGoodsForm에서 값 불러오기
	int goodsNo = Integer.parseInt(multiRequest.getParameter("goodsNo"));
	String goodsName = multiRequest.getParameter("goodsName");
	int goodsPrice = Integer.parseInt(multiRequest.getParameter("goodsPrice"));
	String soldOut = multiRequest.getParameter("soldOut");
	
	// 디버깅
	System.out.println("goodsNo : " + goodsNo);
	System.out.println("goodsName : " + goodsName);
	System.out.println("goodsPrice : " + goodsPrice);
	System.out.println("soldOut : " + soldOut);
	System.out.println("dir : " + dir);
	
	// goodsImg값 받기
	
	String originFileName = multiRequest.getOriginalFileName("imgFileName");
	String fileName  = multiRequest.getFilesystemName("imgFileName");
	String contentType = multiRequest.getContentType("imgFileName");
	
	// 디버깅
	System.out.println("originFileName : "+originFileName);
	System.out.println("fileName : "+fileName);
	System.out.println("contentType : "+contentType);
	
	// 이미지 파일이 아닐 경우
	if(!(contentType.equals("image/gif") || contentType.equals("image/png") || contentType.equals("image/jpeg"))){
		
		// 이미 업로드 된 파일 삭제
		File f = new File(dir + "\\" + fileName);
		if(f.exists()){f.delete();}
		
		String errorMsg = URLEncoder.encode("이미지 파일만 업로드 가능","utf-8");
		response.sendRedirect(request.getContextPath() + "/admin/adminGoodsOne.jsp?errorMsg=Image file available");
		return;
	}
	
	// goods객체 만들기
	Goods goods = new Goods();
	
	// 변경값 입력
	goods.setGoodsNo(goodsNo);
	goods.setGoodsName(goodsName);
	goods.setGoodsPrice(goodsPrice);
	goods.setSoldOut(soldOut);
	
	
	// goodsImg 객체 만들기
	GoodsImg goodsImg = new GoodsImg();
	
	goodsImg.setImgContentType(contentType);
	goodsImg.setImgFileName(fileName);
	goodsImg.setImgOriginFileName(originFileName);
	
	// 메소드 호출
	GoodsService goodsService = new GoodsService();
	int row = goodsService.modifyGoods(goods, goodsImg);
			
	if(row == 0){
		System.out.println("상품수정실패");
		// db insert 실패 시 파일 삭제
		File f = new File(dir + "/" + fileName);
		
		if(f.exists()){
			f.delete(); // return boolean
		}
	}else{
		System.out.println("상품수정성공");
		response.sendRedirect(request.getContextPath() + "/admin/adminGoodList.jsp");
		return;
	}
%>