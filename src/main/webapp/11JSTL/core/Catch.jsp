<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL - catch</title>
</head>
<body>
	<h4>자바 코드에서의 예외</h4>
	<%
	int num1 = 100;
	%>
	<c:catch var="eMessage">
		<%
		int result = num1/0;
		%>
	</c:catch>
	예외 내용 : ${eMessage }
	<!-- 0으로는 나눌 수 없으므로 예외가 발생한다. -->
	
	<h4>EL에서의 예외</h4>
	<c:set var="num2" value="200"/>
	<c:catch var="eMessage">
		${"일" + num2 }
	</c:catch>
	예외 내용 : ${eMessage }
	<!-- EL에서는 문자는 +기호를 통해 연결할 수 없으므로 예외 발생 -->
		
</body>
</html>