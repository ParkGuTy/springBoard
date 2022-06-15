<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실패</title>
<link href="./css/menu.css" rel="stylesheet">
</head>
<body>

	<div id="container">
		<div id="menubar">
			<c:import url="menu.jsp" />
		</div>
		<div id="main">
			<h1>실패했습니다.</h1>
			<h2>다시 시도해 주세요</h2>

			<button onclick="location.href='./board'">보드로 이동</button>
			<button onclick="location.href='./login'">로그인 하기</button>
		</div>
	</div>

</body>
</html>