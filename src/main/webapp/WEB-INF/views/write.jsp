<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글</title>
<link href="./css/menu.css" rel="stylesheet">
<link href="./css/write.css" rel="stylesheet">
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<!-- include summernote css/js -->

<script type="text/javascript">
function check(){
	var title1 = $('input[name=title]').val();
	if (title1.length < 5 ) {
		alert("제목은 5자 이상이어야 합니다.");
		$('input[name=title]').focus();
		return false;
	}
	
	var content = $('textarea[name=content]').val();
	if (content.length < 20 ) {
		alert("내용은 20자 이상 이어야 합니다.");
		$('textarea[name=content]').focus();
		return false;
	} 
}
</script>
</head>
<body>
	<div id="container">
		<div id="menubar">
			<c:import url="menu.jsp" />
		</div>
		<div id="main">
		<h1>글쓰기</h1>
		<div id="writeform">
			<form action="./write" method="post"  onsubmit="return check()" >
				<input type="text" name="title" ><br>
				<textarea name="content" id="summernote"  ></textarea><br>
				<button type="submit" style="margin-left: 92%;">글쓰기</button>
			</form>
		</div>
		<!-- <button type="button" onclick="show()">다이얼로그</button> -->
	</div>
	</div>
	
	<script>
		$(document).ready(function(){
			$("#summernote").summernote({
				height:400				
			});
		});
	</script>

<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- <dialog id="dialog"> // 다이얼 로그 방법 
	<div id="writeform">
		<form action="./write" method="post">
			<input type="text" name="title" ><br>
			<textarea name="content" id="summernote"  ></textarea><br>
			<button type="submit" style="margin-left: 92%;">글쓰기</button>
			<button type="button" onclick="hide()">닫기</button>
		</form>
	</div>
</dialog>
<script>
	var dialog = document.getElementById('dialog');
	function show(){
		dialog.showModal(); 
		dialog.show(); 
	}
	function hide(){
		dialog.close();
	}
</script> -->
</body>
</html>