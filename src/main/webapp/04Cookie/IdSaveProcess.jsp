<%@page import="utils.JSFunction"%>
<%@page import="utils.CookieManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//전송된 폼 값을 받는다.
String user_id = request.getParameter("user_id");
String user_pw = request.getParameter("user_pw");
/*checkbox의 경우 둘 이상의 값이라면 getParameterValues()를 통해
받아야하지만 항목이 한개라면 getParameter()로 받아도 된다*/
String save_check = request.getParameter("save_check");

//아이디와 패스워드가 일치하면 로그인 성공 처리
if("must".equals(user_id) && "1234".equals(user_pw)){
	//로그인 성공 시 아이디 저장을 했다면.. 
	if(save_check != null && save_check.equals("Y")){
		//쿠키를 저장한다. 쿠키값은 로그인 아이디, 유효기간은 1일로 설정.
		CookieManager.makeCookie(response, "loginId", user_id, 86400);
	}
	else{
		//로그인에 성공하고, 체크하지 않았다면 쿠키를 삭제한다.
		CookieManager.deleteCookie(response, "loginId");
	}
	
	//JSFunction.alertLocation("로그인에 성공했습니다", "IDSavemain.jsp", out);
%>
	<script>
		alert("로그인에 성공햇다");
		location.href="IdSaveMain.jsp";
	</script>
<%
}
else{
	//로그인 실패
	//JSFunction.alertBack("로그인에 실패했습니다", out);
%>
	<script>
		alert("로그인에 실패햇다");
		history.back();
	</script>
<%
}
%>