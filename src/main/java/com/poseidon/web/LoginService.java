package com.poseidon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private LoginDAO loginDAO;
	
	public LoginDTO login(LoginDTO dto) {
		return loginDAO.login(dto);
	}
}
