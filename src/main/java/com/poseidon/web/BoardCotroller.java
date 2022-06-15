package com.poseidon.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.web.util.Util;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class BoardCotroller {

	@Autowired
	private BoardService boardService;

	@Autowired
	private Util util;

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	// commentRepair
	@PostMapping(value = "/commentRepair")
	public String commentRepair(HttpServletRequest request) {

		HttpSession session = request.getSession();
		//// System.out.println("comment : " + request.getParameter("comment"));
		//// System.out.println("b_no : " + request.getParameter("b_no"));
		//// System.out.println("c_no : " + request.getParameter("c_no"));
		// 데이터베이스에 반영하기 -> service -> dao -> sqlSeeion -> xml
		CommentDTO dto = new CommentDTO();
		dto.setB_no(util.str2int(request.getParameter("b_no")));
		dto.setC_no(util.str2int(request.getParameter("c_no")));
		dto.setC_content(util.html(request.getParameter("comment")));
		dto.setU_id((String) session.getAttribute("id")); // 세션 저장

		boardService.commentUpdate(dto);

		return "redirect:/detail?b_no=" + request.getParameter("b_no");
	}

	// ./comment 글쓰기 post
	@PostMapping(value = "/comment")
	public String comment(HttpServletRequest request) throws UnsupportedEncodingException {

		// 로그인 했는지
		HttpSession session = request.getSession();
		// b_no, comment 오는지 ?
		if (session.getAttribute("id") != null && request.getParameter("b_no") != null
				&& request.getParameter("comment") != null) {
			// 다 있다면 ? - > DB에 저장하기 - > commentDTO에 저장해서 보내기
			CommentDTO dto = new CommentDTO();
			dto.setB_no(Integer.parseInt(request.getParameter("b_no")));

			// html tag막기 , 줄바꿈 설정하기
			String comment = request.getParameter("comment");
			comment = util.html(comment);
			dto.setC_content(comment);
//			dto.setC_content( util.html(request.getParameter("commnet")));
			dto.setU_id((String) session.getAttribute("id"));

			// DB로 보내기 -> Service -> DAO -> sqlSession - >
			boardService.commentWrite(dto);
		}
		return "redirect:/detail?b_no=" + request.getParameter("b_no"); // 다시 되돌아가기
	}

	// 댓글 삭제./commentDelete?c_no="+c_no
	@GetMapping(value = "/commentDelete")
	public String commentDelete(HttpServletRequest request) {

		// c_no, b_no, u_id
		HttpSession session = request.getSession();

//		//System.out.println("c_no : " + request.getParameter("c_no"));
//		//System.out.println("b_no : " + request.getParameter("b_no"));
//		//System.out.println("u_id : " + Integer.parseInt((String) session.getAttribute("id")));

		if (session.getAttribute("id") != null && request.getParameter("b_no") != null
				&& request.getParameter("c_no") != null) {
			CommentDTO dto = new CommentDTO();
			dto.setB_no(util.str2int(request.getParameter("b_no")));
			dto.setC_no(util.str2int(request.getParameter("c_no")));
			dto.setU_id((String) session.getAttribute("id"));
			boardService.commentDelete(dto);

		} else {

		}

		return "redirect:/detail?b_no=" + request.getParameter("b_no");
	}

	// b_no, comment 오는지 ?

	// 상세보기 화면 /detail?b_no=154
	@GetMapping(value = "/detail")
	public ModelAndView detail(@RequestParam("b_no") int b_no) { // HttpServletRequest , BoardDTO dto , 총 세가지 방법이 있음
		ModelAndView mv = new ModelAndView("detail"); // jsp
//		//System.out.println("들어오는 b_no : " + b_no );
		// DB로 보내서 값이 있는지 확인하기
		BoardDTO dto = boardService.detail(b_no);
		mv.addObject("detail", dto); // 붙이기
//		//System.out.println(dto);

		// 댓글출력 2022-06-014
		// 능력단위명 : 네트워크 프로그래밍 구현
		// 능력단위요소 : 기능 구현하기
		// b_no에 쓴 댓글이 있는지 질의
		List<CommentDTO> cList = boardService.commentList(b_no);
		/*
		 * for(int i=0;i<cList.size();i++) {
		 * cList.get(i).setC_content(cList.get(i).getC_content().replaceAll(" ",
		 * "&nbsp;"));
		 * 
		 * }
		 */
		mv.addObject("cList", cList);

		return mv;
	}

	// 글쓰기 화면 나오게 하기
	// 화면만 이동하려면 String으로 하시면 편합니다.

	/*
	 * @RequestMapping(value = "/write") //값 없이 붙혀 넣을땐 이렇게 해도 됩니다.
	 */
	@GetMapping(value = "/write")
	public String write(HttpSession session) {
		if (session.getAttribute("id") != null) {
			return "write";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping(value = "write")
	public String write(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

//			//System.out.println(title);
//			//System.out.println(content);

		BoardDTO write = new BoardDTO();
		write.setB_title(request.getParameter("title"));
		write.setB_content(request.getParameter("content"));
		write.setU_id((String) session.getAttribute("id"));

		// 세션은?
		write.setU_id((String) session.getAttribute("id"));
		// 각 데이터가 들어오지 않는다면 모두 failure로 보내기 작업만들어 주세요.
		if (session.getAttribute("id") != null && request.getParameter("title").equals("")
				&& request.getParameter("content") != null) {

		}
		// 데이터 베이스로 보내기
		int result = boardService.write(write);
//			//System.out.println("처리결과? " + result);
		if (result == 1) {
			return "redirect:/success"; // 글쓰기 성공
		} else {
			return "redirect:/failure"; // 실패했습니다. 다시 시도하세요.
		}
	}

	@GetMapping(value = "/success")
	public String success() {
		return "success";
	}

	@GetMapping(value = "/failure")
	public String faulure() {
		return "failure";
	}

	@RequestMapping(value = "/board")
	public ModelAndView board(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board"); // jsp 이동
		// 값 보내기
		// mv.addObject("test", "테스트"); // 이름, 값 (키 , 밸류)
		// service에게 일 시키기

		// 전자정부페이징 사용하기
		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		// recordCountPageNo 한 페이지당 게시되는 게시물 수 yes
		int listScale = 10;
		// pageSize = 페이지 리스트에 게시되는 페이지 수 yes
		int pageScale = 10;
		// totalRecordCount 전체 게시물 건수
		int totalCount = boardService.totalCount();

		//// System.out.println("totalCount : " + totalCount);

		// 전자정부페이징 호출
		PaginationInfo paginationInfo = new PaginationInfo();
		// 값 대입
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		paginationInfo.setTotalRecordCount(totalCount);
		// 전자정부 계산하기
		int startPage = paginationInfo.getFirstRecordIndex();
		int lastpage = paginationInfo.getRecordCountPerPage();

		// 서버로 보내기
		PageDTO page = new PageDTO();
		page.setStartPage(startPage);
		page.setLastPage(lastpage);

		List<BoardDTO> boardList = boardService.boardList(page);
		mv.addObject("boardList", boardList);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		return mv;
	}

}
