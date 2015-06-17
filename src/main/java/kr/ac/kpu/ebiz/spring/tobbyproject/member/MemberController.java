package kr.ac.kpu.ebiz.spring.tobbyproject.member;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Locale;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@RequestMapping(value = "/reg_form", method = RequestMethod.GET)
	public String reg() {
		return "/member/register";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ModelAndView insert(@RequestParam("memberId")String memberId, @RequestParam ("password")String password ,
							   @RequestParam("nickname")String nickname , @RequestParam ("email")String email, @RequestParam("method")String method,
							   @RequestParam ("task")String task, @RequestParam ("exam")String exam)
	{
		ModelAndView mav = new ModelAndView("/login");
		HashMap<String, String> member = new HashMap<String, String>();
		member.put("memberId",memberId);
		member.put("password",password);
		member.put("nickname",nickname);
		member.put("email",email);
		member.put("method",method);
		member.put("task",task);
		member.put("exam",exam);
		memberRepository.insert(member);

		HashMap member_role = new HashMap();
		member_role.put("memberId", memberId);
		member_role.put("role", "ROLE_USER");
		memberRepository.insert_role(member_role);

		return mav;
	}

/*	@RequestMapping(value = "/loginForm")
	public String loginForm(Locale locale, Model model){
		return "/member/loginForm";
	}

	@RequestMapping("/login")
	public String login(Locale locale, Model model){

		return "/member/login";
	}*/

}