package com.poseidon.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	private int c_no, b_no, u_no ;
	private String c_date, c_content, u_id;
}
