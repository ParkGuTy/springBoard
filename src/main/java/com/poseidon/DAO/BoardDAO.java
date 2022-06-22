package com.poseidon.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.DTO.BoardDTO;
import com.poseidon.DTO.CommentDTO;
import com.poseidon.DTO.FileDTO;
import com.poseidon.DTO.PageDTO;

@Repository
public class BoardDAO {
	//연결
	@Autowired
	private SqlSession sqlSession;

	public List<BoardDTO> boardList(PageDTO page) {
		return sqlSession.selectList("board.boardList", page); //board.xml 네임스페이스.id 호출
	}

	public BoardDTO detail(BoardDTO repairPost) {
		//countUp 조회수 올리기
		return sqlSession.selectOne("board.detail",repairPost);
	}
	
	public void countUp(BoardDTO dto) {
		sqlSession.update("board.countUp", dto);
	}

	public int write(BoardDTO write) {
		return sqlSession.insert("board.write", write);
	}

	public int totalCount(int b_cate) {
		return sqlSession.selectOne("board.totalCount", b_cate);
	}

	public List<CommentDTO> commentList(int b_no) {
		return sqlSession.selectList("board.commentList", b_no);
	}

	public void commentWrite(CommentDTO dto) {
		System.out.println(dto);
		sqlSession.insert("board.commentWrite", dto);
	}

	public void commentDelete(CommentDTO dto) {
		sqlSession.delete("board.commentDelete", dto);
	}

	public void commentUpdate(CommentDTO dto) {
		sqlSession.update("board.commentUpdate", dto);
	}

	public int deletePost(BoardDTO dto) {
		return sqlSession.delete("board.deletePost", dto);
	}

	public int repairPost(BoardDTO dto) {
		return sqlSession.update("board.repairPost", dto);
	}

	public void fileWrite(FileDTO fileDTO) {
		 sqlSession.insert("board.fileWrite", fileDTO);
	}

	public List<FileDTO> fileList(int b_no) {
		return sqlSession.selectList("board.fileList", b_no);
	}

	public void deleteFile(int b_no) {
		sqlSession.delete("board.deleteFile", b_no);
	}

}




/*
 * 클래스 생성하기
 * @Contrioller : 객체 생성, Controller 역할을 합니다.
 * @Service 	: 객체 생성, Service 역할	 
 * @Repository 	: 객체 생성, DAO 역할
 * @Component 	: 객체 생성, 컨트롤,서비스,DAO 역할 그외의 역할을 함  
 * 
 * 
 * 객체 연결하기
 * @Autowired : 타입으로 검색해서 넣어주기 = 스프링에서 제공
 * @Inject    :	타입으로 검색해서 = 자바가 제공합니다.
 * @Resource(name="")  : name으로 검색해서 = 스프링에서 제공
 * 
 * 
 */




