<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="directVar" value="100"/> 
	<c:set var="elVar" value="${directVar mod 5 }"/>
	<c:set var="expVar" value="<%= new Date() %>"/>
	<c:set var="betweenVar">변수 설정!</c:set>
</body>
</html>