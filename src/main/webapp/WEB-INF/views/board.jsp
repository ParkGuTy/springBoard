<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="./css/menu.css" rel="stylesheet">
<link href="./css/board.css" rel="stylesheet">
<style type="text/css">
#detail{
   width: 90%;
   height: 100%;
   margin: 0 auto;
   display: block;
}
table {
   margin: 0 auto;
   width: 100%;
   max-height: 600px;
   border-collapse: collapse;
   margin-bottom: 10px;
}
th{
   background-color: aqua;
   width: 100px;
}
td, th{
   height: 30px;
   border-bottom: 1px white solid;
   padding: 10px;
}
#hcontent{
   height: 200px;
   vertical-align: top;
}
#hcontenttop{
   border-top: 1px red solid;
}
</style>
</head>
<body>
		<div id="menubar">
			<c:import url="menu.jsp"/>
			</div>
			<div id="main">
		
	<table calss="table">
	<thead>
		<tr>
			<th scope="col">번호</th>
			<th scope="col">제목</th>
			<th scope="col">날짜</th>
			<th scope="col">쓴사람</th>
			<th scope="col">조회수</th>
		</tr>
		</thead>
		<c:forEach items="${boardList}" var="b">
		<tr onclick="location.href='./detail?b_no=${b.b_no}'">
			<td id="r1">${b.b_no }</td>
			<td id="r3">${b.b_title }
			<c:if test="${b.commentCount  gt 0}"><small>${b.commentCount }</small></c:if></td>
			<td id="r2">${b.b_date }</td>
			<td id="r2">${b.u_id }</td>
			<td id="r1">${b.b_count }</td>
		</tr></c:forEach>
	</table>
	<hr>
	<div>
		<ui:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="linkPage"/>
	</div><hr><c:if test="${sessionScope.id ne null }">
	<button onclick="location.href='./write'" style="margin-left: 90%; margin-top: 5px;">글쓰기</button></c:if>
	</div>
	
	<!-- Controller -> Service -> DAO -> DB (Model)
	<!-- Controller : 흐름을 제어 --> 
	<!-- Service : 필요한 로직
	DAO : mybatis 에게 일 시키기 
	DB : 데이터 가져오기
	
	jsp(View) -> 사용자 -->
	<script type="text/javascript">
		function linkPage(pageNo){
			location.href = "./board?pageNo=" + pageNo;
		}
	</script>
</body>
</html>