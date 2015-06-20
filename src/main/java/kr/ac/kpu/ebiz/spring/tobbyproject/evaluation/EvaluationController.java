package kr.ac.kpu.ebiz.spring.tobbyproject.evaluation;

import kr.ac.kpu.ebiz.spring.tobbyproject.lecture.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

	@Autowired
	EvaluationRepository evaluationRepository;

	@Autowired
	LectureRepository lectureRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam("lecture_id") int lecture_id) {

		ModelAndView mav = new ModelAndView("/evaluation/list");
		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/reg_form", method = RequestMethod.GET)
	public ModelAndView reg(@RequestParam int lecture_id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		HashMap check = new HashMap();
		check.put("lecture_id", lecture_id);
		check.put("member_id", member_id);

		int count = evaluationRepository.selectCount(check);

		if(count != 0) {
			ModelAndView mav1 = new ModelAndView("/evaluation/list");
			mav1.addObject("error", "이미 이 강의에 대해 강의평가를 작성하셨습니다.");
			mav1.addObject("lecture", lectureRepository.selectIAN(lecture_id));
			mav1.addObject("evaluations", evaluationRepository.selectL(lecture_id));
			return mav1;
		}

		ModelAndView mav2 = new ModelAndView("/evaluation/register");

		HashMap evaluation = new HashMap();
		evaluation.put("lecture_id", lecture_id);

		mav2.addObject("evaluation", evaluation);

		return mav2;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ModelAndView insert(@RequestParam("lecture_id") int lecture_id,@RequestParam("method") String method,
							   @RequestParam("task") String task, @RequestParam("exam") String exam,
							   @RequestParam("comment") String comment, @RequestParam("score") int score) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		HashMap check = new HashMap();
		check.put("lecture_id", lecture_id);
		check.put("member_id", member_id);

		int count = evaluationRepository.selectCount(check);

		System.out.println(count + "+" + "카운트 확인");

		ModelAndView mav = new ModelAndView("/evaluation/list");
		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));

		if(count != 0) {
			mav.addObject("error", "이미 이 강의에 대해 강의평가를 작성하셨습니다.");
		}else {

			HashMap<String, java.io.Serializable> evaluation = new HashMap<String, java.io.Serializable>();
			evaluation.put("lecture_id", lecture_id);
			evaluation.put("member_id", member_id);
			evaluation.put("method", method);
			evaluation.put("task", task);
			evaluation.put("exam", exam);
			evaluation.put("comment", comment);
			evaluation.put("score", score);

			evaluationRepository.insert(evaluation);
		}

		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int evaluation_id, @RequestParam int lecture_id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		String writer = evaluationRepository.selectMember(evaluation_id);

		System.out.println(member_id + "+" + "로그인아이디");
		System.out.println(writer + "+" + "작성자아이디");

		if(member_id.equals(writer) == false){
			ModelAndView mav = new ModelAndView("/evaluation/list");
			mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
			mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));
			mav.addObject("error", "본인이 작성하신 강의평가가 아닙니다.");
			return mav;
		}

		ModelAndView mav = new ModelAndView("/evaluation/modify");
		Map evaluation = evaluationRepository.select(evaluation_id);
		mav.addObject("evaluation", evaluation);

		return mav;
	}

	@RequestMapping(value = "/mod", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam("evaluation_id") int evaluation_id, @RequestParam("method") String method, @RequestParam("task") String task,
							   @RequestParam("exam") String exam, @RequestParam("comment") String comment, @RequestParam("score") int score,
							   @RequestParam("lecture_id") int lecture_id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		String writer = evaluationRepository.selectMember(evaluation_id);

		ModelAndView mav = new ModelAndView("/evaluation/list");

		if(member_id.equals(writer) == false){

			mav.addObject("error", "본인이 작성하신 강의평가가 아닙니다.");

		}

		HashMap<String, java.io.Serializable> evaluation = new HashMap<String, java.io.Serializable>();
		evaluation.put("evaluation_id", evaluation_id);
		evaluation.put("method", method);
		evaluation.put("task", task);
		evaluation.put("exam", exam);
		evaluation.put("comment", comment);
		evaluation.put("score", score);
		evaluationRepository.update(evaluation);

		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/likes", method = RequestMethod.GET)
	public ModelAndView likes(@RequestParam("evaluation_id")int evaluation_id, @RequestParam("lecture_id") int lecture_id,
							  @RequestParam(value = "error", required = false) String error) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		HashMap evaluationSub = new HashMap();
		evaluationSub.put("evaluation_id", evaluation_id);
		evaluationSub.put("member_id", member_id);

		int count = evaluationRepository.selectSub(evaluationSub);

		ModelAndView mav = new ModelAndView("/evaluation/list");

		System.out.println(count);

		if(count != 0) {
			mav.addObject("error", "이미 추천/비공감/신고 중에 하나를 하셨습니다.");
		} else {
			evaluationRepository.insertSub(evaluationSub);
			evaluationRepository.updateLike(evaluation_id);
		}

		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView dislike(@RequestParam("evaluation_id")int evaluation_id, @RequestParam("lecture_id") int lecture_id,
							  @RequestParam(value = "error", required = false) String error) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		HashMap evaluationSub = new HashMap();
		evaluationSub.put("evaluation_id", evaluation_id);
		evaluationSub.put("member_id", member_id);

		int count = evaluationRepository.selectSub(evaluationSub);

		ModelAndView mav = new ModelAndView("/evaluation/list");

		System.out.println(count);

		if(count != 0) {
			mav.addObject("error", "이미 추천/비공감/신고 중에 하나를 하셨습니다.");

		} else {
			evaluationRepository.insertSub(evaluationSub);
			evaluationRepository.updateDislike(evaluation_id);
		}

		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView report(@RequestParam("evaluation_id")int evaluation_id, @RequestParam("lecture_id") int lecture_id,
							  @RequestParam(value = "error", required = false) String error) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		HashMap evaluationSub = new HashMap();
		evaluationSub.put("evaluation_id", evaluation_id);
		evaluationSub.put("member_id", member_id);

		int count = evaluationRepository.selectSub(evaluationSub);

		ModelAndView mav = new ModelAndView("/evaluation/list");

		System.out.println(count);

		if(count != 0) {
			mav.addObject("error", "이미 추천/비공감/신고 중에 하나를 하셨습니다.");
		} else {
			evaluationRepository.insertSub(evaluationSub);
			evaluationRepository.updateReport(evaluation_id);
		}

		mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

	@RequestMapping(value = "/isDelete", method = RequestMethod.GET)
	public ModelAndView isDelete(@RequestParam("evaluation_id")int evaluation_id, @RequestParam("lecture_id") int lecture_id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String member_id = user.getUsername();

		String writer = evaluationRepository.selectMember(evaluation_id);

		ModelAndView mav = new ModelAndView("/evaluation/list");

		if(member_id.equals(writer) == true){
			evaluationRepository.isDelete(evaluation_id);
		} else {
			mav.addObject("error", "본인이 작성한 것이 아닙니다.");
		}

		mav.addObject("lecture", lectureRepository.select(lecture_id));
		mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

		return mav;
	}

}