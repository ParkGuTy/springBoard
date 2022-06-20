package com.poseidon.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.DAO.LoginDAO;
import com.poseidon.DTO.LoginDTO;

@Service
public class LoginService {
	@Autowired
	private LoginDAO loginDAO;
	
	public LoginDTO login(LoginDTO dto) {
		return loginDAO.login(dto);
	}

	public int join(LoginDTO dto) {
		return loginDAO.join(dto);
		//방금한 동적
	}

	public int checkID(String parameter) {
		return loginDAO.checkID(parameter);
	}


}
