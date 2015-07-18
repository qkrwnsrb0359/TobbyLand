package kr.ac.kpu.ebiz.spring.tobbyproject.repository;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

public class MemberRepositoryImpl extends SqlSessionDaoSupport implements MemberRepository {

	public Map select(String memberId) {
		return getSqlSession().selectOne("MemberRepository.select", memberId);
	}

	public int selectMember(String memberId) {
		return getSqlSession().selectOne("MemberRepository.selectMember", memberId);
	}

	public int selectEmail(String email) {
		return getSqlSession().selectOne("MemberRepository.selectEmail", email);
	}

	public int selectNick(String nickname) {
		return getSqlSession().selectOne("MemberRepository.selectNick", nickname);
	}

	public int selectModNick(Map member) {
		return getSqlSession().selectOne("MemberRepository.selectModNick", member);
	}

	public String selectPw(String memberId) {
		return getSqlSession().selectOne("MemberRepository.selectPw", memberId);
	}

	public int selectEn(String memberId) {
		return getSqlSession().selectOne("MemberRepository.selectEn", memberId);
	}

	public List<Map> selectAll() {
		return getSqlSession().selectList("MemberRepository.selectAll");
	}

	public boolean delete(String memberId) { return getSqlSession().delete("MemberRepository.delete", memberId) > 0;
	}

	public boolean deleteEnabled(String memberId) {
		return getSqlSession().update("MemberRepository.deleteEnabled", memberId) > 0;
	}

	public boolean enabled(String memberId) {
		return getSqlSession().update("MemberRepository.enabled", memberId) > 0;
	}

	public boolean insert(Map member) {
		return getSqlSession().insert("MemberRepository.insert", member) > 0;
	}

	public boolean insert_role(String memberId) {
		return getSqlSession().insert("MemberRepository.insert_role", memberId) > 0;
	}

	public boolean update(Map member) {
		return getSqlSession().update("MemberRepository.update", member) > 0;
	}

	public boolean updatePw(Map member) {
		return getSqlSession().update("MemberRepository.updatePw", member) > 0;
	}

}