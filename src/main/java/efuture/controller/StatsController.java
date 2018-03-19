package efuture.controller;

import efuture.domain.UserVO;
import efuture.domain.member.MemberSchDTO;
import efuture.domain.project.ProjectSchDTO;
import efuture.domain.stats.OpenCheckVO;
import efuture.domain.stats.ProjectVO;
import efuture.domain.stats.StatsListVO;
import efuture.service.CommonService;
import efuture.service.MemberService;
import efuture.service.ProjectService;
import efuture.service.StatsService;
import efuture.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Controller
@RequestMapping("/stats")
public class StatsController {

    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    public CommonService commonService;
    @Autowired
    public ProjectService projectService;
    @Autowired
    public MemberService memberService;
    @Autowired
    public StatsService service;

    @RequestMapping("")
    public String main(Model model) {
        model.addAttribute("deptList", commonService.list("DP"));
        model.addAttribute("optionList", commonService.list("PJ"));
        return "stats/list";
    }

    /**
     * DATA LIst
     * @param
     * @return
     */
    @RequestMapping("/list")
    public String list(StatsListVO vo, Model model) {
        OpenCheckVO checkVO = null;
        ArrayList<HashMap<String, Object>> pjList = null;     // project data
        ArrayList<HashMap<String, Object>> memberList = null; // member data
        ArrayList<String> userid = new ArrayList<>();         // data list parameter
        ArrayList<ProjectVO> dataList = new ArrayList<>();    // 한 프로젝트 당 데이터 리스트
        HashMap<String,Object> total = null;                  // 각 사원의 총 업무시간
        try {
            checkVO = service.getOpenCheck(vo.getYear(),vo.getMonth());             // 해당 월 수정권한
            pjList = projectService.schList(new ProjectSchDTO(vo.getDept()));      // 프로젝트 리스트
            memberList = memberService.schList(new MemberSchDTO(vo.getDept()));   // 사원 리스트

            /**** 해당 부서에 해당하는 회원 리스트 ****/
            for(HashMap<String,Object> member : memberList){
                userid.add(member.get("userid").toString());
            }
            vo.setMemberList(userid);
            /************ 프로젝트 당 데이터 리스트 ***********/
            ArrayList<HashMap<String,Object>> data;
            ProjectVO project;
            for(HashMap<String,Object> pj : pjList){
                project = new ProjectVO();
                vo.setProject_seq(pj.get("seq"));
                data = service.list(vo);
                project.setProject_name(pj.get("project_name").toString());
                project.setOptionSize(data.size());
                project.setOptionList(data);
                dataList.add(project);
            }
            /************각 사원의 총 업무시간 ***********/
            total = service.total(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("check", checkVO);
        model.addAttribute("memberList", memberList);
        model.addAttribute("dataList", dataList);
        model.addAttribute("total", total);
        return "stats/list_ajax";
    }

    @RequestMapping("/excel")
    public ModelAndView excelDown(StatsListVO vo, ModelAndView mav) {
        String fileName = "업무시간통계(" + vo.getDate() + ")";
        Utils.getRequest().setAttribute("fileName", fileName);
        ArrayList<HashMap<String, Object>> pjList = null;     // project data
        ArrayList<HashMap<String, Object>> memberList = null; // member data
        ArrayList<String> userid = new ArrayList<>();         // data list parameter
        ArrayList<ProjectVO> dataList = new ArrayList<>();    // 한 프로젝트 당 데이터 리스트
        HashMap<String,Object> total = null;             // 각 사원의 총 업무시간
        try {
            pjList = projectService.schList(new ProjectSchDTO(vo.getDept()));      // 프로젝트 리스트
            memberList = memberService.schList(new MemberSchDTO(vo.getDept()));   // 사원 리스트

            /**** 해당 부서에 해당하는 회원 리스트 ****/
            for(HashMap<String,Object> member : memberList){
                userid.add(member.get("userid").toString());
            }
            vo.setMemberList(userid);
            /************ 프로젝트 당 데이터 리스트 ***********/
            ArrayList<HashMap<String,Object>> data;
            ProjectVO project;
            for(HashMap<String,Object> pj : pjList){
                project = new ProjectVO();
                vo.setProject_seq(pj.get("seq"));
                data = service.list(vo);
                project.setProject_name(pj.get("project_name").toString());
                project.setOptionSize(data.size());
                project.setOptionList(data);
                dataList.add(project);
            }
            /************각 사원의 총 업무시간 ***********/
            total = service.total(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("param", vo);
        mav.addObject("memberList", memberList);
        mav.addObject("dataList", dataList);
        mav.addObject("total", total);
        mav.setViewName("/excel/stats");
        return mav;
    }

    /**
     * 해당 월/별 수정 권한 변경
     * @param vo seq : seq, isopen
     * @return
     */
    @RequestMapping("/changeCheck")
    @ResponseBody
    public HashMap<String,Object> changeOpenCheck(OpenCheckVO vo){
        HashMap<String,Object> resultMap = new HashMap<>();
        UserVO user = (UserVO) Utils.getSession("login");
        logger.info(":::::::change calender month check::::[ SEQ = " + vo.getSeq() + " ] [ CHECK = " +  vo.getIsopen() + " ]" + "[ USERID = " + user.getUserid() + "]");
        int result = service.changeOpenCheck(vo);
        if( result > 0 ){
            resultMap.put("RESULT_CODE","SUCCESS");
            resultMap.put("RESULT_MSG","변경되었습니다.");
        }else{
            resultMap.put("RESULT_CODE","FAIL");
            resultMap.put("RESULT_MSG","변경 실패");
        }
        return resultMap;
    }
}
