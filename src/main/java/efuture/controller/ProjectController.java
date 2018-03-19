package efuture.controller;

import efuture.domain.Page;
import efuture.domain.project.ProjectListVO;
import efuture.domain.project.ProjectVO;
import efuture.service.CommonService;
import efuture.service.ProjectService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired  ProjectService service;
    @Autowired  CommonService commonService;

    /*  리스트 검색  */
    @ModelAttribute("schInfo")
    public ProjectListVO searchInfo(ProjectListVO vo){
        return vo;
    }

    /**
     * 프로젝트 리스트
     * @param info
     * @param model
     * @return
     */
    @RequestMapping("")
    public String list(@ModelAttribute("schInfo") ProjectListVO info, Model model){
        Page page = new Page(info.getPerPageNum(), 10, info.getCurrent(), service.count(info));
        info.setPage(page);
        model.addAttribute("stateList",commonService.list("ST"));   // SEARCH STATE LIST
        model.addAttribute("page", page);
        model.addAttribute("list", service.list(info));
        return "project/list";
    }
    /**
     * 프로젝트 폼 (등록, 수정)
     * @param type add, update 택 1
     * @param seq content seq
     * @param model search Infomation
     * @return
     */
    @RequestMapping("/form/{type}")
    public String form(@PathVariable String type, @RequestParam(value = "seq", defaultValue = "0") int seq, Model model){
        String path;
        ProjectVO vo = null;
        if(StringUtils.equals(type,"add")){
            path = "project/add";
        }else{
            vo = service.content(seq);
            vo.setDept((ArrayList<String>) service.selectDept(seq));
            path ="project/update";
        }
        model.addAttribute("vo", vo);       // content INFO
        model.addAttribute("deptList",commonService.list("DP"));    // 부서 LIST
        model.addAttribute("stateList",commonService.list("ST"));   // 개발상황 LIST
        model.addAttribute("optionList",commonService.list("PJ"));  // 프로젝트 옵션 LIST
        return path;
    }

    /**
     * 프로젝트 등록
     * @param vo
     * @return
     */
    @RequestMapping("/add")
    public String add(ProjectVO vo){
        int seq = 0;
        ArrayList<String> deptList = null;
        try{
            // 개발상황 ( 개발완료 :: 개발 완료일 저장 )
            if(vo.getState().equals("ST002")) vo.setComplete_date(new Date());
            else vo.setComplete_date(null);
            seq = service.add(vo);
            // 각 부서별 INSERT
            deptList = vo.getDept();
            service.addDept(seq, deptList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/project";
    }

    /**
     * 프로젝트 수정
     * @param vo ( deptList )
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public int update(ProjectVO vo) {
        int result = 0;
        ArrayList<String> deptList = null;
        try {
            // 개발상황 ( 개발완료 :: 개발 완료일 저장 )
            if(vo.getState().equals("ST002")) vo.setComplete_date(new Date());
            else vo.setComplete_date(null);
            result = service.update(vo);
            // 부서 (삭제 후 모든 데이터 INSERT )
            deptList = vo.getDept();
            service.deleteDept(vo.getSeq());
            service.addDept(vo.getSeq(), deptList);
        } catch (Exception e) {
            result = -1;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 프로젝트삭제 (AJAX)
     * @param seqStr 삭제할 프로젝트 SEQ LIST ( String )
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public HashMap<String,Object> delete(@RequestBody String seqStr) {
        HashMap<String,Object> result = new HashMap<>();
        ArrayList<Object> failCnt = new ArrayList<>();  // 실패한 프로젝트 seq
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray seqList = (JSONArray) jsonParser.parse(seqStr);
            int seq = 0;
            for(Object num : seqList){
                seq = Integer.parseInt(num.toString());
                if(service.isUseBySeq(seq) > 0){
                    failCnt.add(seq);
                }else{
                    if(service.delete(seq) < 0 || service.deleteDept(seq) < 0) {
                       failCnt.add(seq);
                    }
                }
            }
            // 전체 실패
            if(seqList.size() == failCnt.size()){
                result.put("RESULT_CODE", "ALL_FAIL");
                result.put("RESULT_MSG", "프로젝트 삭제에 실패하였습니다.\n사용중인 프로젝트입니다.");
            }else if(failCnt.size() > 0){
                // 일부 실패 : 실패한 프로젝트 리스트 SEQ ( fail Cnt )
                result.put("RESULT_CODE", "FAIL");
                result.put("RESULT_MSG", "일부 프로젝트 삭제에 실패하였습니다.\n사용중인 프로젝트 리스트 입니다.\n");
                result.put("FAIL_LIST",failCnt);
            }else{
                result.put("RESULT_CODE","SUCCESS");
                result.put("RESULT_MSG","삭제되었습니다.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
