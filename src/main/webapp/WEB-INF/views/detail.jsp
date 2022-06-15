<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<link href="./css/menu.css" rel="stylesheet">
<link href="./css/comment.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js">
</script>

<style type="text/css">
#detail {
	margin: 0 auto;
	width: 90%;
	height: 100%;
	display: block;
}

table {
	margin: 0 auto;
	width: 100%;
	max-height: 600px;
	border-collapse: collapse;
	margin-bottom: 10px;
}

th {
	background-color: purple;
	width: 100px;
}

td, th {
	height: 30px;
	border-bottom: 1px white solid;
	padding: 10px;
}

#hcontent {
	height: 200px;
	vertical-align: top;
}
</style>
<script type="text/javascript">
	function check(){
		var comment = document.getElementsByName("comment")[0];
		if(comment.value.length < 5){
			alert("댓글을 입력하세요.");
			comment.focus();
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
			<div id="detail">
		<table>
			<tr id="he">
				<th>번호</th>
				<td>${detail.b_no }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${detail.b_title }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${detail.u_id}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${detail.b_date }</td>
			</tr>
			<tr>
				<td colspan="2">${detail.b_content }</td>
			</tr>
		</table>
		<button onclick="location.href='./board'">게시판 ㄱㄱ</button>
		<hr>
		<!-- 댓글을 출력할 부분 -->
		<!-- 	댓글이 있으면 댓글 내용 출력
		댓글이 없으면 댓글이 없다고 출력 -->
	</div>
		<div id="comments">
      <c:if test="${fn:length(cList) gt 0 }">
         <c:forEach items="${cList }" var="c">
            <div id="comment">
               <div id="commenthead">
                  <div id="cid">
                     <div id="c_no" style="width: 0px; height: 0px; visibility: hidden;">${c.c_no}</div>
                     
                     <c:if test="${c.u_id  eq sessionScope.id}">
                     	<img alt="repair" src="./img/repair.ico" class="repair" style="height: 18px; vertical-align: middle;">
                     	<img alt="delete" src="./img/delete.ico" class="delete" style="height: 18px; vertical-align: middle;">
                     </c:if>
                  </div>
                  <div id="cdate">${c.c_date }</div>
               </div>
               <div id="commentbody"><pre>${c.c_content }</pre></div>
            </div>
         </c:forEach>
      </c:if>
      </div>
      <!-- //댓글쓰기 로그인한 사용자만 봅니다. -->
      <c:if test="${sessionScope.id ne null }">
      <div id="cwriteform">
         <form action="./comment" method="post" onsubmit="return check()">
            <textarea name="comment" id="commentText"></textarea>
            <!-- 빠진게 있습니다. b_no도 보내야 해요  -->
            <input type="hidden" name="b_no" value="${detail.b_no }">
            <button type="submit">댓글<br><br>쓰기</button>
            <div id="textCount">(0/280)</div>
         </form>
      </div>
      </c:if>
      <!-- 댓글 끝 -->
</div>
</div>
		}
	}
	<script type="text/javascript">
	/* 예) #(선택자).명령();  */
	//제이쿼리 씁니다.
	$(document).ready(function(){ //문서 로딩이 완료되었다면
		$(".delete").click(function(){
			//alert("삭제를 클릭했습니다.");
			//alert(     $(this).parents("#cid").text()    );
			var c_no = $(this).parents("#cid").children("#c_no").text();
			//alert(c_no);
			// $(위치).childern("#cid");
			if(confirm("해당 댓글을 삭제하시겠습니까?")){
				location.href="./commentDelete?b_no=${detail.b_no}&c_no="+c_no;			
			}
		});
		
		$(".repair").click(function(){
			//alert("수정하기를 클릭했습니다.");
			var c_no = $(this).parents("#cid").children("#c_no").text();
			var oldComment = $(this).parents("#comment").children("#commentbody").text();
			$("#cwriteform").remove();//기존에 댓글쓰기 창도 없애기.
			
			//alert(c_no + " : " + oldComment);
			//$(this).parents("#comment").children("#commentbody").remove(); //요소 삭제
			//$(this).parents("#comment").children("#commentbody").hide();
			//$(this).parents("#comment").children("#commenthead").hide();
			//$(this).parents("#comment *").remove(); //전체 삭제
			var v  = "<div id='cwriteform'><form action='./commentRepair' method='post' onsubmit='return check()'>";
				v += "<textarea name='comment' id='commentText'>"+oldComment+"</textarea>";
				v += "<input type='hidden' name='c_no' value='"+c_no+"'>";
				v += "<input type='hidden' name='b_no' value='${detail.b_no}'>";
				v += "<button type='submit'>수정<br><br>하기</button>";
				v += "<div id='textCount'>(0/280)</div>";	
				v += "</form></div>";
			//$(this).parents("#comment").append(v);//화면보이기
			
			//$(this).parents("#comment").children("#commentbody").remove();//나머지 필요없는 구성요소 삭제
			//$(this).parents("#comment").children("#commenthead").remove();//나머지 필요없는 구성요소 삭제
			//empty / html도 확인하세요
			//$(this).parents("#comment").empty().html(v);
			$(this).parents("#comment").html(v);
			$(".repair").remove();//화면에 있는 모든 수정하기 삭제하기 버튼 지우기
			$(".delete").remove();	
		});
		
		//댓글 글자수 제한
		$(document).on("keyup", "#commentText", function(){
			$("#textCount").html("(" + $(this).val().length + "/280)");
			if($(this).val().length > 280){
				$(this).val(  $(this).val().substring(0, 280)  );//넘어가는 글자 삭제하기
				$("#textCount").html("(280/280)")
			}
		});
		
	});
	</script>
	</body>
	</html>
	<%-- <c:choose>
			<c:when test="${fn:length(cList) gt 0  }">
				댓글 있어요.			
			</c:when>
			<c:otherwise>
				댓글 없어요.
			</c:otherwise>
		</c:choose> --%>
