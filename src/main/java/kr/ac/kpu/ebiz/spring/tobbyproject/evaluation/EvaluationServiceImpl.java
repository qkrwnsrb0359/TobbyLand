package kr.ac.kpu.ebiz.spring.tobbyproject.evaluation;

import kr.ac.kpu.ebiz.spring.tobbyproject.lecture.LectureRepository;
import kr.ac.kpu.ebiz.spring.tobbyproject.semester.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    SemesterRepository semesterRepository;

    public void listService(int lecture_id, ModelAndView mav) {

        mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
        mav.addObject("evaluations", evaluationRepository.selectL(lecture_id));

    }

    public void listBestService(int lecture_id, ModelAndView mav) {

        mav.addObject("best", evaluationRepository.selectBest(lecture_id));

    }

    public void regFormService(int lecture_id, ModelAndView mav) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        HashMap check = new HashMap();
        check.put("lecture_id", lecture_id);
        check.put("member_id", member_id);

        int count = evaluationRepository.selectCount(check);

        if(count != 0) {

            mav.setViewName("redirect:/evaluation/list?lecture_id="+lecture_id);
            mav.addObject("error", "이미 이 강의에 대해 강의평가를 작성하셨습니다.");

        } else {

            Calendar cal = Calendar.getInstance();

            int year = cal.get(Calendar.YEAR) - 2000;
            int month = cal.get(Calendar.MONTH);
            int half;
            if(month>=6){half = 2;}
            else{half = 1;}

            StringBuilder current = new StringBuilder(String.valueOf(year));
            StringBuilder current2 = new StringBuilder("-");
            StringBuilder current3 = new StringBuilder(String.valueOf(half));
            StringBuilder current4 = new StringBuilder("학기");

            current.append(current2).append(current3).append(current4);

            mav.setViewName("/evaluation/register");

            HashMap evaluation = new HashMap();
            evaluation.put("lecture_id", lecture_id);

            mav.addObject("evaluation", evaluation);
            mav.addObject("semesters", semesterRepository.selectAll());
            mav.addObject("current", current);
        }

    }

    public void regService(Map evaluation, ModelAndView mav) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        evaluation.put("member_id", member_id);

        int lecture_id =  Integer.parseInt(evaluation.get("lecture_id").toString());

        evaluationRepository.insert(evaluation);

        mav.setViewName("redirect:/evaluation/list?lecture_id="+lecture_id);
    }

    public void viewService(int lecture_id, int evaluation_id, ModelAndView mav) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        String writer = evaluationRepository.selectMember(evaluation_id);

        Collection authorities = user.getAuthorities();

        if(member_id.equals(writer) == true || authorities.toString().contains("ROLE_ADMIN")){

            mav.setViewName("/evaluation/modify");
            Map evaluation = evaluationRepository.select(evaluation_id);
            mav.addObject("evaluation", evaluation);
            mav.addObject("semesters", semesterRepository.selectAll());

        } else {

            mav.setViewName("redirect:/evaluation/list?lecture_id=" + lecture_id);
            mav.addObject("error", "본인이 작성하신 강의평가가 아닙니다.");

        }

    }

    public void modService(Map evaluation, ModelAndView mav) {

        int lecture_id =  Integer.parseInt(evaluation.get("lecture_id").toString());

        evaluationRepository.update(evaluation);

        mav.setViewName("redirect:/evaluation/list?lecture_id=" + lecture_id);

    }

    public int likesService(int evaluation_id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        HashMap evaluationSub = new HashMap();
        evaluationSub.put("evaluation_id", evaluation_id);
        evaluationSub.put("member_id", member_id);

        int count = evaluationRepository.selectSubCount(evaluationSub);

        int result;

        if(count !=0) {
            int kind = evaluationRepository.selectSubType(evaluationSub);
            result = kind;
        } else {
            evaluationSub.put("kind", 1);
            evaluationRepository.insertSub(evaluationSub);
            evaluationRepository.updateLike(evaluation_id);
            result = 0;
        }

        return result;
    }

    public int dislikeService(int evaluation_id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        HashMap evaluationSub = new HashMap();
        evaluationSub.put("evaluation_id", evaluation_id);
        evaluationSub.put("member_id", member_id);

        int count = evaluationRepository.selectSubCount(evaluationSub);

        int result;

        if(count !=0) {
            int kind = evaluationRepository.selectSubType(evaluationSub);
            result = kind;
        } else {
            evaluationSub.put("kind", 2);
            evaluationRepository.insertSub(evaluationSub);
            evaluationRepository.updateDislike(evaluation_id);
            result = 0;
        }

        return result;

    }

    public int reportService(int evaluation_id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        HashMap evaluationSub = new HashMap();
        evaluationSub.put("evaluation_id", evaluation_id);
        evaluationSub.put("member_id", member_id);

        int count = evaluationRepository.selectSubCount(evaluationSub);

        int result;

        if(count !=0) {
            int kind = evaluationRepository.selectSubType(evaluationSub);
            result = kind;
        } else {
            evaluationSub.put("kind", 3);
            evaluationRepository.insertSub(evaluationSub);
            evaluationRepository.updateReport(evaluation_id);
            result = 0;
        }

        return result;

    }

    public void isDeleteService(int lecture_id, int evaluation_id, ModelAndView mav) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        String writer = evaluationRepository.selectMember(evaluation_id);

        Collection authorities = user.getAuthorities();

        if(member_id.equals(writer) == true || authorities.toString().contains("ROLE_ADMIN")){
            evaluationRepository.isDelete(evaluation_id);
        } else {
            mav.addObject("error", "본인이 작성한 것이 아닙니다.");
        }

        mav.setViewName("redirect:/evaluation/list?lecture_id="+lecture_id);


    }

    public void searchPreferService(Map search, ModelAndView mav) {

        List<Map> result = evaluationRepository.SearchPrefer(search);

/*        int lecture_id =  Integer.parseInt(search.get("lecture_id").toString());*/

        mav.addObject("evaluations", result);
/*        mav.addObject("lecture", lectureRepository.selectIAN(lecture_id));
        mav.addObject("best", evaluationRepository.selectBest(lecture_id));*/

        if(result.isEmpty() == true){
            mav.addObject("error", "검색 결과가 없습니다.");
        }


    }

    public void replyService(int evaluation_id, ModelAndView mav) {

        mav.addObject("replys", evaluationRepository.selectRe(evaluation_id));
        mav.addObject("evaluation_id", evaluation_id);

    }

    public void replyRegService(Map evaluationSub, ModelAndView mav) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String member_id = user.getUsername();

        evaluationSub.put("member_id", member_id);

        evaluationRepository.insertSub(evaluationSub);

        int evaluation_id = Integer.parseInt(evaluationSub.get("evaluation_id").toString());

        StringBuilder page = new StringBuilder("redirect:/evaluation/replyList?evaluation_id=");
        StringBuilder id = new StringBuilder(String.valueOf(evaluation_id));
        page.append(id);

        mav.setViewName(page.toString());

    }
}