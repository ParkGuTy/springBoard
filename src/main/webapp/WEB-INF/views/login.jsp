<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="./css/menu.css" rel="stylesheet">
</head>
<body>
	<c:if test="${error ne null}">
		<script type="text/javascript">
			alert("${error}");
		</script>
	</c:if>
	<div id="menubar">
		<c:import url="menu.jsp" />
		<c:if test="${param.error ne null}">
			<script type="text/javascript">
				alert("로그인 정보가 올바르지 않습니다.\n 올바른 아이디와 비밀번호를 입력하세요.")
			</script>
		</c:if>
	</div>

</body>
</html>