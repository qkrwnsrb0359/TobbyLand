package kr.ac.kpu.ebiz.spring.tobbyproject;

import java.util.List;
import java.util.Map;

public interface LectureRepository {

	Map select(Integer lectureId);

	List<Map> selectAll();

	boolean delete(int lectureId);

	boolean insert(Map lecture);

	boolean update(Map lecture);
}
