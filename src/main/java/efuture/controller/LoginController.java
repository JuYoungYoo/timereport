package efuture.controller;

import efuture.domain.UserVO;
import efuture.service.MainService;
import efuture.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * Created by user on 2017-03-28.
 */
@Controller
@SessionAttributes("loginVO")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MainService service;

    @RequestMapping("")
    public String index() {
        return "login";
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam HashMap<String, Object> hmap, ModelAndView mav) {
        UserVO vo = null;
        vo = service.loginCheck(hmap);
        if (null == vo) {
            mav.addObject("result", false);
            mav.setViewName("login");
        } else {
            hmap.put("user_seq", vo.getSeq());          // 사원코드
            hmap.put("ipaddr", Utils.getClientIP());    // IP주소
            service.loginHistory(hmap);
            Utils.setSession("login", vo);
            mav.addObject("login", vo);
            logger.info("::::::::::::::LOGIN::::::::" + Utils.getSession("login"));
            switch (vo.getGrade()) {
                case "GD003": mav.setViewName("redirect:/report"); break;
                default: mav.setViewName("redirect:/member"); break;
            }
        }
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav) {
        Utils.removeSession("login");
        mav.setViewName("redirect:/");
        return mav;
    }
}
