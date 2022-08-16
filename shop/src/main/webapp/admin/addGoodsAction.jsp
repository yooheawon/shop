<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.oreilly.servlet.*" %>
<%@ page import = "com.oreilly.servlet.multipart.*" %>
<%@ page import = "service.GoodsService" %>
<%@ page import = "vo.Goods" %>
<%@ page import = "vo.GoodsImg" %>

<%
	// 사진 저장 경로 설정
	String dir = request.getServletContext().getRealPath("/upload");
	System.out.println("addGoodsAction.jsp dir : " + dir);
	// 사진 크기 설정 - 10mb
	int max = 10*1024*1024;
	// 원래 request에 래핑 (request, 경로, 파일사이즈, 인코딩, 이름재설정)
	MultipartRequest mRequest = new MultipartRequest(request, dir, max, "UTF-8", new DefaultFileRenamePolicy());
	// 파라미터
	// goods입력값 받아오기
	String goodsName = mRequest.getParameter("goodsName");
	int goodPrice = Integer.parseInt(mRequest.getParameter("goodsPrice"));
	String soldOut = mRequest.getParameter("soldOut");
	// 디버깅
	System.out.println("goodsName : "+goodsName);
	System.out.println("goodPrice : "+goodPrice);
	System.out.println("soldOut : "+soldOut);
	
	// goodsImg 값 받기
	String imgFile = mRequest.getParameter("imgFile");
	String originFileName = mRequest.getOriginalFileName("imgFile");
	String fileName = mRequest.getFilesystemName("imgFile");
	String contentType = mRequest.getContentType("imgFile");
	// 디버깅
	System.out.println("originFileName : "+originFileName);
	System.out.println("fileName : "+fileName);
	System.out.println("contentType : "+contentType);
	//이미지인지 검사 후 파일 삭제
	if(!(contentType.equals("image/gif") || contentType.equals("image/png") || contentType.equals("image/jpeg"))){
		// 이미지가 아닌 파일 삭제
		File f = new File(dir + "/" + fileName);
		
		if(f.exists()){
			f.delete(); // return boolean
		}
		
		response.sendRedirect(request.getContextPath() + "/fileUploadForm.jsp?errorMsg=img file only");
		return;
	}
	
	
	// setter 
	Goods goods = new Goods();
	goods.setGoodsName(goodsName);
	goods.setGoodsPrice(goodPrice);
	goods.setSoldOut(soldOut);
	System.out.println(goods);

	GoodsImg goodsImg = new GoodsImg();
	goodsImg.setImgContentType(contentType);
	goodsImg.setImgFileName(fileName);
	goodsImg.setImgOriginFileName(originFileName);
	System.out.println(goodsImg);

	// 메소드 호출
	GoodsService goodsService = new GoodsService();
	int goodsNo = goodsService.addGoods(goods, goodsImg);

	if(goodsNo == 0){
		// db insert 실패 시 파일 삭제
		File f = new File(dir + "/" + fileName);
		
		if(f.exists()){
			f.delete(); // return boolean
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/addGoodsForm.jsp?errorMsg=insert error");
		return;
	}
%>
