package kr.ac.kpu.ebiz.spring.tobbyproject.lecture;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by happy on 2015-07-01.
 */
public interface LectureService {

    void listService(ModelAndView mav);

    void regFormService(ModelAndView mav);

    void regService(Map lecture, ModelAndView mav);

    int confirmService(int lecture_id);

    void viewService(int lecture_id, ModelAndView mav);

    void modService(Map lecture, ModelAndView mav);

    void searchService(Map search, ModelAndView mav);

    int likesService(int lecture_id);

    int isDeleteService(int lecture_id);

}