package efuture.controller;

import efuture.domain.Page;
import efuture.domain.project.ProjectListVO;
import efuture.domain.project.ProjectVO;
import efuture.service.CommonService;
import efuture.service.ProjectService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class TymeleafController {

    @Autowired
    ProjectService service;
    @Autowired
    CommonService commonService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TymeleafController.class);

    @RequestMapping(value = "/first")
    public String login(Model model, String auth) throws Exception {
        logger.info("::::::/first");
        ProjectVO vo = new ProjectVO();
        vo.setSeq(123);
        vo.setProject_name("test project name");
        model.addAttribute("auth", auth);
        model.addAttribute("project", vo);
        return "first";
    }

    @RequestMapping("/param")
    public String param() {
        logger.info("::::::/param");
        return "param";
    }

    @RequestMapping("/list")
    public String list(ProjectListVO info, Model model) {
        logger.info("::::::/test/list");
        Page page = new Page(info.getPerPageNum(), 10, info.getCurrent(), service.count(info));
        info.setPage(page);
        model.addAttribute("stateList", commonService.list("ST"));   // SEARCH STATE LIST
        model.addAttribute("page", page);
        model.addAttribute("list", service.list(info));
        return "list";
    }
}


