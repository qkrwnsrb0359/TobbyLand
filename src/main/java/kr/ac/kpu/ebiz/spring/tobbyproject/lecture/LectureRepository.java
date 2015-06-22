package kr.ac.kpu.ebiz.spring.tobbyproject.lecture;

import java.util.List;
import java.util.Map;

public interface LectureRepository {

	Map select(Integer lectureId);

	Map selectIAN(Integer lectureId);

	String selectMember(Integer lectureId);

	List<Map> selectAll();

	List<Map> selectName(Map search);

	List<Map> selectDept(String lectureDept);

	List<Map> selectProf(String lectureProf);

	boolean delete(int lectureId);

	boolean isDelete(int lectureId);

	boolean insert(Map lecture);

	boolean update(Map lecture);

	boolean updateLike(int lectureId);

	boolean insertSub(Map lecture);

	int selectSub(Map lecture);
}

