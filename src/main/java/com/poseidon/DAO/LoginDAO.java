package com.poseidon.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.poseidon.DTO.LoginDTO;

@Repository
public class LoginDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//로그인 처리하는 메소드
	public LoginDTO login(LoginDTO dto) {
		return sqlSession.selectOne("login.login", dto); 
	}

	public int join(LoginDTO dto) {
		//if(sqlSession.selectOne("login.detail", dto.getU_id()) == null) {
			return sqlSession.insert("login.join", dto);
		//} else {
		//	return 0;
		//}
	}

	public int checkID(String parameter) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("login.checkID", parameter);
	}

	
}
