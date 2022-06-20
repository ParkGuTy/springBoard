package com.poseidon.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.DTO.BoardDTO;
import com.poseidon.DTO.CommentDTO;
import com.poseidon.DTO.FileDTO;
import com.poseidon.DTO.PageDTO;
import com.poseidon.Service.BoardService;
import com.poseidon.utill.FileSave;
import com.poseidon.utill.Util;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private Util util;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private FileSave filesave;

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	// 글 수정 저장 repairPost POST
	@PostMapping(value = "repairPost")
	public String repairPost2(HttpServletRequest request) throws UnsupportedEncodingException {
		// 필요한 값 : 세션 , title , content, b_no
		HttpSession session = request.getSession(); // 로그인 저장
		if (session.getAttribute("id") != null && request.getParameter("title") != null
				&& request.getParameter("content") != null) { // 값들이 없다면 null 표시

			// 값이 다 있따면 DTO에 담기
			BoardDTO dto = new BoardDTO();
			dto.setB_content(request.getParameter("content"));
			dto.setB_title(request.getParameter("title"));
			dto.setB_no(util.str2int(request.getParameter("b_no")));
			dto.setU_id((String) session.getAttribute("id"));
			// System.out.println(dto);

			// 저장하기
			int result = boardService.repairPost(dto);
			if (result == 1) {
				return "redirect:/success";
			} else {
				return "redirect:/failure";
			}
		} else {
			return "redirect:/failure";
		}
	}

	// 글수정 repairPost?_b_no=12
	@GetMapping(value = "/repairPost")
	public ModelAndView repairPost(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("repairPost");
		// b_no session
		HttpSession session = request.getSession();
		if (request.getParameter("b_no") != null && session.getAttribute("id") != null) {
			// dto에 담아서 mv에 붙이기 = /detail
			// 변경필 -> 글번호만 맞으면 다른 사람 글도 가져옵니다. 수정해야 합니다.
			BoardDTO repairPost = new BoardDTO();
			repairPost.setB_no(util.str2int(request.getParameter("b_no")));
			repairPost.setU_id((String) session.getAttribute("id"));
			// System.out.println(repairPost);
			BoardDTO dto = boardService.detail(repairPost);
			if (dto != null) {
				mv.addObject("dto", dto);
			}
		} else {
			// 다른 페이지로 이동시키기
			mv.setViewName("redirect:/failure");
		}
		return mv;
	}

	// ./deletePost?b_no=${detail.b_no}";
	// 해당 메소드를 실행할 때 들어오는 필수값 잡기 @RequestParam(value="b_no")
	// 없을 경우 에러가 납니다. required=false, defaultValue="0" 기본값
	@GetMapping(value = "/deletePost")
	public String deletePost(@RequestParam(value = "b_no", required = false, defaultValue = "0") int b_no,
			HttpSession session) {
		int result = 0;
		// System.out.println("b_no : " + b_no);
		// System.out.println(("id : " + session.getAttribute("id")));
		if (b_no > 0 && session.getAttribute("id") != null) {
			// 모든 값이 들어온다면 데이터베이스로 값 보내기
			BoardDTO dto = new BoardDTO();
			dto.setB_no(b_no);
			dto.setU_id((String) session.getAttribute("id"));

			// System.out.println("처리결과 : " + result);

			// 파일삭제?
			String path = servletContext.getRealPath("resources/upload/");

			// 현재 게시판에 존재하는 파일객체를 만듬
			List<FileDTO> fileList = boardService.fileList(b_no);
			for (int i = 0; i < fileList.size(); i++) {
				File file = new File(path + fileList.get(i).getF_filename());

				if (file.exists()) { // 파일이 존재하면
					file.delete(); // 파일 삭제
					System.out.println("파일이 삭제 되었습니다.");
				} else {
					System.out.println("실패 했습니다.");
				}
				// 테이블에서 삭제
				boardService.deleteFile(b_no);
			}
			result = boardService.deletePost(dto);
		}
		if (result == 1) {
			return "redirect:/success"; // 글쓰기 성공
		} else {
			return "redirect:/failure"; // 실패했습니다. 다시 시도하세요.
		}
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
	public ModelAndView detail(@RequestParam("b_no") int b_no, HttpServletRequest request) { // HttpServletRequest ,
																								// BoardDTO dto , 총 세가지
																								// 방법이 있음
		ModelAndView mv = null; // jsp
//		//System.out.println("들어오는 b_no : " + b_no );
		// DB로 보내서 값이 있는지 확인하기
		BoardDTO detail = new BoardDTO();
		detail.setB_no(b_no);

		BoardDTO dto = boardService.detail(detail);
//		//System.out.println(dto);

		// 댓글출력 2022-06-014
		// 능력단위명 : 네트워크 프로그래밍 구현
		// 능력단위요소 : 기능 구현하기
		// b_no에 쓴 댓글이 있는지 질의
		if (request.getSession().getAttribute("id") != null) {
			mv = new ModelAndView("detail"); // jsp

			List<CommentDTO> cList = boardService.commentList(b_no);
			mv.addObject("detail", dto);
			mv.addObject("cList", cList);

			// 파일업로드 여부 확인
			if (dto.getFileCount() > 0) {
				// 파일이 있다면 해당 파일 이름도 보내주기
				List<FileDTO> fileList = boardService.fileList(b_no);
				mv.addObject("fileList", fileList);

			}

			return mv;
		} else {
			mv = new ModelAndView("login");

			mv.addObject("error", "로그인을 이용해주세요");
			return mv;
		}
		/*
		 * for(int i=0;i<cList.size();i++) {
		 * cList.get(i).setC_content(cList.get(i).getC_content().replaceAll(" ",
		 * "&nbsp;"));
		 * 
		 * }
		 */

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
	public String write(HttpServletRequest request, MultipartFile[] files) throws IllegalStateException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// 파일 업로드 적용한 후 오는 값 확인하기
		System.out.println(request.getParameter("title"));
		System.out.println(request.getParameter("content"));
		System.out.println(request.getParameter("id"));

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
		System.out.println("처리결과? " + result);
		System.out.println("지금 저장된 글의 b_no를 가져올 수 있는 방법은?");
		System.out.println("방금 저장된 pk : " + write.getB_no());

		// 파일 업로드는 위 작업 끝나고...
		// 파일이 있다면 수행

		// 배열처리
		for (MultipartFile file : files) {
			if (!(file.getOriginalFilename().isEmpty())) {
				// 사용자가 파일을 선택했다면 -> 파일저장하기
				System.out.println("업로드한 파일 이름 : " + file.getOriginalFilename());
				System.out.println(" 사이즈 : " + file.getSize());
				System.out.println(" 유무 : " + file.isEmpty());

				// 파일을 저장할 실제 경로 얻어오기 톰켓가상경로
				String realPath = servletContext.getRealPath("/resources/upload/");
				System.out.println("파일이 저장되는 경로 : " + realPath);

				// 호출
				String realFileName = filesave.save(realPath, file);
				System.out.println("저장한 파일 이름 : " + realFileName);
				// 파일이름을 데이터 베이스에 저장하는 작업
				// b_no, realFilename 을 저장합니다. ->fileDTO
				FileDTO fileDTO = new FileDTO();
				fileDTO.setB_no(write.getB_no());
				fileDTO.setF_filename(realFileName);

				boardService.fileWrite(fileDTO);

				System.out.println("업로드 끝. 경로로 들어가서 확인하세요");
			}
		}

		if (result == 1) {
			// return "redirect:/success"; // 글쓰기 성공
			return "redirect:/detail?b_no=" + write.getB_no();
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

		// 카테고리 잡기
		int b_cate = 1;
		if (request.getParameter("b_cate") != null) {
			b_cate = util.str2int(request.getParameter("b_cate"));
		}

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
		int totalCount = boardService.totalCount(b_cate);

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
		page.setB_cate(b_cate);


		List<BoardDTO> boardList = boardService.boardList(page);
		mv.addObject("boardList", boardList);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("b_cate", b_cate);

		return mv;
	}

}
