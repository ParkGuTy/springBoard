<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<div class="wrap">
	<a href="./"><img alt="logo" src="./img/rainbow.png"></a>
	<c:choose>
		<c:when test="${sessionScope.id eq null}">
		<div id="login2" style="width: 100%">로그인하기</div>
			<div id="loginform">
				<form action="./login" method="post">
					<input type="text" name="id" placeholder="아이디를 입력 하세요" required="required"> 
					<input type="password" name="pw" placeholder="비밀번호를 입력하세요" required="required"> 
					<a href="./join">가입하기</a>
					<button type="submit">로그인하기</button>
				</form>
			</div>
			
			<script type="text/javascript">
			$(document).ready(function(){
				$("#loginform").hide();
				$("#login2").click(function(){
					$("#login2").hide();
					$("#loginform").fadeIn("slow");
				});
			});	
			</script>
			
		</c:when>
		<c:otherwise>
			${sessionScope.id }님 반갑습니다. 
			<img alt="logout" src="./img/logout.png" style="width: 20px;"  onclick="logout();" >
		</c:otherwise>
	</c:choose>
<div class="wrap">
	<script type="text/javascript">
		function logout() {
			if (confirm("로그아웃 하시겠습니까?")) {
				location.href="./logout";
			}
		}
	</script>
</div>

	<!--다섯번째 메뉴와 내용-->
	<!--버튼 요소-->
	<label for="menu1" class="label e"> <span class="rotate">
			<p>게시판</p>
	</span>
	</label>
	<!--아코디언 기능구현 핵심 요소 : 실제기능을 하는 input, CSS로 가려놓았음-->
	<input type="radio" name="navigation" id="menu1" class="button">
	<!--콘텐츠가 보여질 요소 : 위의 input이 체크 되었을 때 .contents를 선택하여 높이가 높아지도록 조정함 (선택자 : input[radio]:checked + .contents) -->
	<div class="contents">
		<div class="inner">
			<div class="description">
				<p onclick="location.href='./board?b_cate=1'">게시판</p>
				<p onclick="location.href='./board?b_cate=2'">게시판</p>
				<p onclick="location.href='./board?b_cate=3'">게시판</p>
				<p onclick="location.href='./board?b_cate=4'">게시판</p>
			</div>
		</div>
	</div>

	<!--이하의 메뉴와 내용 역시 위와 구조 동일함-->
	<!--네번째 메뉴와 내용-->
	<label for="menu2" class="label d"> <span class="rotate">
			<p>MENU 4</p>
	</span>
	</label> <input type="radio" name="navigation" id="menu2" class="button">
	<div class="contents">
		<div class="inner">
			<div class="description">
				<h3>TITLE 4</h3>
				<p>다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파.</p>
			</div>
		</div>
	</div>

	<!--세번째 메뉴와 내용-->
	<label for="menu3" class="label c"> <span class="rotate">
			<p>MENU 3</p>
	</span>
	</label> <input type="radio" name="navigation" id="menu3" class="button">
	<div class="contents">
		<div class="inner">
			<div class="description">
				<h3>TITLE 3</h3>
				<p>다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에
					타고파.</p>
			</div>
		</div>
	</div>


	<!--두번째 메뉴와 내용-->
	<label for="menu4" class="label b"> <span class="rotate">
			<p>MENU 2</p>
	</span>
	</label> <input type="radio" name="navigation" id="menu4" class="button">
	<div class="contents">
		<div class="inner">
			<div class="description">
				<h3>TITLE 2</h3>
				<p>다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에
					타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파.</p>
			</div>
		</div>
	</div>

	<!--첫번째 메뉴와 내용-->
	<label for="menu5" class="label a"> <span class="rotate">
			<p>MENU 1</p>
	</span>
	</label> <input type="radio" name="navigation" id="menu5" class="button">
	<div class="contents">
		<div class="inner">
			<div class="description">
				<h3>TITLE 1</h3>
				<p>다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에
					타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에 타고파. 다람쥐 헌 챗바퀴에
					타고파.</p>
			</div>
		</div>
	</div>


	<!--닫기 버튼-->
	<input type="radio" name="navigation" id="menu6" class="button close"
		checked> <label for="menu6" class="closer"> X </label>
</div>