package efuture.controller;

import efuture.domain.Page;
import efuture.domain.member.MemberListVO;
import efuture.domain.member.MemberVO;
import efuture.service.CommonService;
import efuture.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private MemberService service;

    @ModelAttribute("schInfo")
    public MemberListVO searchInfo(MemberListVO vo) {
        return vo;
    }

    @RequestMapping("")
    public String list(@ModelAttribute("schInfo") MemberListVO info, Model model) {
        Page page = new Page(info.getPerPageNum(), 10, info.getCurrent(), service.count(info));
        info.setPage(page);
        model.addAttribute("deptList", commonService.list("DP")); // SEARCH DEPT
        model.addAttribute("list", service.list(info));
        model.addAttribute("page", page);
        return "member/list";
    }
    /**
     * 등록, 수정 폼
     * @param type (  add, update )
     * @return
     */
    @RequestMapping("/form/{type}")
    public String form(@PathVariable String type,
                       @RequestParam(value = "seq", required = false, defaultValue = "0") int seq,
                       Model model) {
        String path = null;
        ArrayList<HashMap<String, Object>> deptList = null;
        ArrayList<HashMap<String, Object>> gradeList = null;
        MemberVO vo = null;
        try {
            deptList = commonService.list("DP");
            gradeList = commonService.list("GD");
            if (StringUtils.equals(type, "add")) {
                path = "member/add";
            } else {
                vo = service.content(seq);
                path = "member/update";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("vo", vo);
        model.addAttribute("deptList", deptList);
        model.addAttribute("gradeList", gradeList);
        return path;
    }
    /**
     * 등록
     * @return
     */
    @RequestMapping("/add")
    public String add(MemberVO vo) {
        try {
            service.add(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/member";
    }
    /**
     * 회원 수정
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public int update(MemberVO vo) {
        int result = 0;
        try {
            result = service.update(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 비밀번호 변경
     * @param : seq, pwd
     * @return
     */
    @RequestMapping("/update/pwd")
    @ResponseBody
    public HashMap<String, Object> updatePwd(@RequestParam HashMap<String, Object> hmap) {
        int result = 0;
        HashMap<String, Object> resultMap = new HashMap<>();
        result = service.updatePwd(hmap);
        if (result > 0) {
            resultMap.put("RESULT_CODE", "SUCCESS");
            resultMap.put("RESULT_MSG", "비밀번호가 변경되었습니다.");
        } else {
            resultMap.put("RESULT_CODE", "ERROR");
            resultMap.put("RESULT_MSG", "비밀번호 변경실패");
        }
        return resultMap;
    }
    /**
     * 회원 삭제 ( 여러명 )
     * @param seq : JSON OBJECT ( Array 객체 )
     * @return
     */
    @RequestMapping("/delete/selected")
    @ResponseBody
    public HashMap<String,Object> delete(@RequestBody String seq) {
        HashMap<String,Object> result = new HashMap<>();
        ArrayList<Object> failCnt = new ArrayList<>();  // 실패한 회원 SEQ
        JSONParser jsonParser = new JSONParser();
        try {
             JSONArray seqList = (JSONArray) jsonParser.parse(seq);
            for(Object num : seqList){
                if(service.delete(Integer.parseInt(num.toString())) < 0) failCnt.add(num);
            }
            // 전체 실패
            if(seqList.size() == failCnt.size()){
                result.put("RESULT_CODE", "ALL_FAIL");
                result.put("RESULT_MSG", "회원정보 삭제에 실패하였습니다.(전체)");
            }else if(failCnt.size() > 0){
                // 일부 실패 : 실패한 회원 리스트 SEQ ( failCnt )
                result.put("RESULT_CODE", "FAIL");
                result.put("RESULT_MSG", "회원정보 삭제 일부 실패하였습니다.");
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

    /**
     * 회원정보 삭제 ( 1인 )
     * @param seq
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public HashMap<String,Object> delete(@RequestParam int seq){
        HashMap<String,Object> resultMap = new HashMap<>();
        int result = 0;
        result = service.delete(seq);
        if(result < 1){
            // 일부 실패 : 실패한 회원 리스트 SEQ ( failCnt )
            resultMap.put("RESULT_CODE", "FAIL");
            resultMap.put("RESULT_MSG", "회원정보 삭제 실패하였습니다.");
        }else{
            resultMap.put("RESULT_CODE","SUCCESS");
            resultMap.put("RESULT_MSG","삭제되었습니다.");
        }
        return resultMap;
    }
}
