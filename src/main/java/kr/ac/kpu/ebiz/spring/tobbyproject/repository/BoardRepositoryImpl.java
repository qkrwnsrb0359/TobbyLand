package kr.ac.kpu.ebiz.spring.tobbyproject.repository;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

public class BoardRepositoryImpl extends SqlSessionDaoSupport implements BoardRepository {

	/*select*/
	
	public int selectBoardCount(int department_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardCount", department_id);
	}

	public List<Map> selectBoardAll(Map board) {
		return getSqlSession().selectList("BoardRepository.selectBoardAll", board);
	}

	public int selectBoardMaxRnum(int department_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardMaxRnum", department_id);
	}

	public Map selectBoard(int board_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoard", board_id);
	}

	public List<Map> selectBoardReplyAll(int board_id) {
		return getSqlSession().selectList("BoardRepository.selectBoardReplyAll", board_id);
	}

	public int selectBoardMember_id(int board_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardMember_id", board_id);
	}

	public Map selectBoardConfirm(int board_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardConfirm", board_id);
	}

	public int selectBoardLike(int board_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardLike", board_id);
	}

	public int selectBoardDislike(int board_id) {
		return getSqlSession().selectOne("BoardRepository.selectBoardDislike", board_id);
	}

	public Map selectBoardSub(Map boardSub) {
		return getSqlSession().selectOne("BoardRepository.selectBoardSub", boardSub);
	}

	public int selectBoardSearchCount(Map board) {
		return getSqlSession().selectOne("BoardRepository.selectBoardSearchCount", board);
	}

	public List<Map> selectBoardSearch(Map board) {
		return getSqlSession().selectList("BoardRepository.selectBoardSearch", board);
	}

	public List<Map> selectSiteNoticeAll() {
		return getSqlSession().selectList("BoardRepository.selectSiteNoticeAll");
	}

	public List<Map> selectKpuNoticeAll() {
		return getSqlSession().selectList("BoardRepository.selectKpuNoticeAll");
	}

	public List<Map> selectLatestAll() {
		return getSqlSession().selectList("BoardRepository.selectLatestAll");
	}

	public List<Map> selectHotAll() {
		return getSqlSession().selectList("BoardRepository.selectHotAll");
	}



	/*insert*/
	
	public boolean insertBoard(Map board) {
		return getSqlSession().insert("BoardRepository.insertBoard", board) > 0;
	}

	public boolean insertReply(Map board) {
		return getSqlSession().insert("BoardRepository.insertReply", board) > 0;
	}

	public boolean insertBoardSub(Map boardSub) {
		return getSqlSession().insert("BoardRepository.insertBoardSub", boardSub) > 0;
	}



	/*update*/

	public boolean updateBoardHit(int board_id) {
		return getSqlSession().update("BoardRepository.updateBoardHit", board_id) > 0;
	}

	public boolean updateBoard(Map boardSub) {
		return getSqlSession().update("BoardRepository.updateBoard", boardSub) > 0;
	}

	public boolean updateBoardLike(int board_id) {
		return getSqlSession().update("BoardRepository.updateBoardLike", board_id) > 0;
	}

	public boolean updateBoardDislike(int board_id) {
		return getSqlSession().update("BoardRepository.updateBoardDislike", board_id) > 0;
	}

	public boolean updateBoardReport(int board_id) {
		return getSqlSession().update("BoardRepository.updateBoardReport", board_id) > 0;
	}

	public boolean updateBoardIsDelete(int board_id) {
		return getSqlSession().update("BoardRepository.updateBoardIsDelete", board_id) > 0;
	}


	/*delete*/

	public boolean delete(int board_id) {
		return getSqlSession().delete("BoardRepository.delete", board_id) > 0;
	}

}
