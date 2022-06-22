package com.poseidon.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poseidon.DTO.LoginDTO;
import com.poseidon.Service.LoginService;

@Controller
public class LoginController { 
	// 로그인 페이지 만들때 첫번째 생성 1.(컨트롤러) 2. jsp생성 3. LoginDTO 4. LoginDAO 5.LoginService 6.login.xml 7. 서블렛 콘텍스트 들어가서 이미지 설정
	// <session-config <session-timeout>15</session-timeout> </session-config> 로그인 시간 15분 지나면 자동 로그아웃 

	@Autowired
	private LoginService loginService;

	// 2022-06-10
	// 능력단위명 : 네트워크 프로그래밍 구현
	// 능력단위요소 : 개발환경분석하기
	
	// 2022-06-17
		// 능력단위명 : 네트워크 프로그래밍 구현
		// 능력단위요소 : 기능 구현하기
	@PostMapping(value = "/checkID")
	public @ResponseBody String checkID(HttpServletRequest request) { //결과값이 노출만 되면 될땐  public String 
		String result = "1";
		//System.out.println("ajsx 통신중입니다. : " + request.getParameter("id"));
		
		//해야할 일 : 데이터베이스에게 물어보기 count(*)
		int count = loginService.checkID(request.getParameter("id"));
		result = String.valueOf(count);
		
		return result;
	}
	
	
	
	
	//가입하기 join
	@GetMapping(value = "/join")
	public String join() {
		return "join";
	}
	
	
	@PostMapping(value = "/join")
	public String join(HttpServletRequest request) {
		//System.out.println(request.getParameter("id"));
		//System.out.println(request.getParameter("pw1"));
		//System.out.println(request.getParameter("pw2"));
		//System.out.println(request.getParameter("name"));
		//System.out.println(request.getParameter("email"));
		//DTO수정 , 테이블 수정
		LoginDTO dto = new LoginDTO();
		
	         dto.setU_id(request.getParameter("id"));
	         dto.setU_pw(request.getParameter("pw1"));
	         dto.setU_name(request.getParameter("u_name"));
	         dto.setU_email(request.getParameter("u_email"));
	         
	         int result = loginService.join(dto);
		
	         //System.out.println("가입결과 : " + result);
		
		return "redirect:/join";
	 }	
	

	//로그아웃 만들기
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null) {
			session.removeAttribute("id");
		}
		return "redirect:/login";
	}
	
	// login화면 불러오기
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	// 로그인 로직 처리하기
	
	@PostMapping(value = "/login")
	public String login(HttpServletRequest request) {
	      Long start = System.currentTimeMillis();
	      
	      try {         
	         String id = request.getParameter("id");
	         String pw = request.getParameter("pw");
	         //System.out.println(id);
	        // System.out.println(pw);
	         //dto에 담아서 데이터베이스로 보내고 싶어요 
	         LoginDTO dto = new LoginDTO();
	         dto.setU_id(id);
	         dto.setU_pw(pw);
	         
	         //Service DAO도 만들어줄게욤
	         dto = loginService.login(dto);
	         //System.out.println("dto : " + dto);
	         if(dto != null) {         
	            //System.out.println(dto.getU_no());
	            //System.out.println(dto.getU_date());
	            
	            //정상 로그인 = 세션만들고
	            HttpSession session = request.getSession();
	            session.setMaxInactiveInterval(15 * 60);//초단위로 세션 유지시간을 지정
	            session.setAttribute("id", id);
	            //System.out.println("생성된 세션 확인 : " + session.getAttribute("id"));
	            
	            return "redirect:/board";
	         }else {
	            //비정상 로그인 = 다시 로그인하세요.
	            return "redirect:/login?error=1254";
	         }
	         
	      } finally {
	         Long end = System.currentTimeMillis();
	         Long time = end - start ;
	         System.out.println(time + "ms");
	      }
	      
	   }
}
