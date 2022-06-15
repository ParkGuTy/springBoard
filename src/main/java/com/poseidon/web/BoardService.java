package com.poseidon.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;

	public List<BoardDTO> boardList(PageDTO page) {
		return boardDAO.boardList(page);
		
	}

	public BoardDTO detail(int b_no) {
		return boardDAO.detail(b_no);
	}

	public int write(BoardDTO write) {
		return boardDAO.write(write);
	}

	public int totalCount() {
		return boardDAO.totalCount();
	}

	public List<CommentDTO> commentList(int b_no) {
		return boardDAO.commentList(b_no);
	}

	public void commentWrite(CommentDTO dto) {
		boardDAO.commentWrite(dto);
	}

	public void commentDelete(CommentDTO dto) {
		boardDAO.commentDelete(dto);
	}

	public void commentUpdate(CommentDTO dto) {
		boardDAO.commentUpdate(dto);
	}

	
}
