package com.poseidon.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	private int c_no, b_no, u_no ;
	private String  c_content, u_id;
	private Date c_date;
}
