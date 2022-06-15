package com.poseidon.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardDTO {
	private int b_no, b_count, commentCount;
	private String b_title, b_content, b_date, u_id;

}
