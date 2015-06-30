package kr.ac.kpu.ebiz.spring.tobbyproject.member;

import java.util.List;
import java.util.Map;

public interface MemberRepository {

	Map select(String memberId);

	int selectCount(String memberId);

	List<Map> selectAll();

	boolean delete(String memberId);

	boolean deleteEnabled(Map member);

	boolean enabled(String memberId);

	boolean insert(Map member);

	boolean insert_role(String memberId);

	boolean update(Map member);
}

