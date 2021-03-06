<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="common.Person"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL - set2</title>
</head>
<body>
	<h4>List컬렉션 이용하기</h4>
	<%
	//Person객체를 저장할 수 있는 List계열의 컬렉션 생성
	ArrayList<Person> pList = new ArrayList<Person>();
	//List에 객체 추가
	pList.add(new Person("성삼문", 55));
	pList.add(new Person("박팽년", 60));
	%>
	<!-- request영역에 컬렉션 저장 -->
	<c:set var="personList" value="<%=pList %>" scope="request"/>
	<ul><!-- '성삼문'객체를 출력 -->
		<li>이름: ${requestScope.personList[0].name }</li><!-- List컬렉션이므로 인덱스로 접근 가능함 -->
		<li>나이: ${personList[0].age }</li>
	</ul>
	
	<h4>Map컬렉션 이용하기</h4>
	<%
	//Key는 String, value는 Person타입인 Map계열의 컬렉션 생성
	Map<String, Person> pMap = new HashMap<String, Person>();
	//컬렉션의 2개의 객체 저장
	pMap.put("personArgs1", new Person("하위지", 65));
	pMap.put("personArgs2", new Person("이개", 65));
	%>
	<!-- request영역에 속성 저장 -->
	<c:set var="personMap" value="<%=pMap %>" scope="request"/>
	<ul><!-- Map컬렉션이므로 Key값을 통해 접근하여 출력 -->
		<li>아이디: ${requstScope.personMap.personArgs2.name }</li>
		<li>비번: ${personMap.personArgs2.age }</li>
	</ul>
</body>
</html>