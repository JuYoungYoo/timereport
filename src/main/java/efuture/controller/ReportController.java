package efuture.controller;

import efuture.domain.UserVO;
import efuture.domain.member.MemberSchDTO;
import efuture.domain.member.MemberVO;
import efuture.domain.project.ProjectSchDTO;
import efuture.domain.report.TimeReportListVO;
import efuture.domain.report.TimeReportSaveVO;
import efuture.domain.report.TimeReportVO;
import efuture.domain.report.excel.ExcelListVO;
import efuture.domain.report.excel.ExcelOptionDataVO;
import efuture.domain.report.excel.ExcelOptionVO;
import efuture.domain.report.excel.ExcelProjectVO;
import efuture.domain.stats.OpenCheckVO;
import efuture.service.*;
import efuture.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by user on 2017-03-29.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService service;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private MemberService memberService;

    /**
     * 타임리포트
     * @param model
     * @return
     */
    @RequestMapping("")
    public String list(TimeReportListVO dto, Model model) {
        UserVO user = null;
        ArrayList<HashMap<String, Object>> pjList = null;
        if(null != Utils.getCookie("start_date")) {
            dto.setStart_date(Utils.getCookie("start_date"));
        }else{
            dto.setStart_date(Utils.convertCalToStr(Calendar.getInstance(),"yyyy-MM-dd"));
        }
        user = (UserVO) Utils.getSession("login");
        pjList = projectService.schList(new ProjectSchDTO("ST001",user.getDept()));
        model.addAttribute("projectList", pjList); // 해당 부서의 PROJECT LIST
        /*  PROJECT OPTION LIST (COMMON 공통 ::::: 추후 프로젝트 당 OPTION 변경 시 별도 테이블 생성 필요. 공통테이블의 OPTION은 DEFAULT로 활용) */
        model.addAttribute("optionList", commonService.list("PJ", "code_val"));
        model.addAttribute("schInfo", dto);
        return "report/list";
    }

    /**
     * [관리자] 타임리포트
     * @param model
     * @return
     */
    @RequestMapping("/read")
    public String adminList(TimeReportListVO dto, Model model)  {
        if(null != Utils.getCookie("start_date")) {
            dto.setStart_date(Utils.getCookie("start_date"));
        }else{
            dto.setStart_date(Utils.convertCalToStr(Calendar.getInstance(),"yyyy-MM-dd"));
        }
        ArrayList deptList = new ArrayList<>(Arrays.asList("DP001", "DP002", "DP003"));
        model.addAttribute("memberList", memberService.schList(new MemberSchDTO(deptList)));
        model.addAttribute("schInfo", dto);
        return "report/admin_list";
    }

    /**
     * 퍼센트 차트
     * @return
     */
    @RequestMapping("/chart")
    public String chartByMM(TimeReportListVO dto, Model model) {
        ArrayList<HashMap<String, Object>> dataList;        // 차트 데이터
        ArrayList<String> bgColorList = new ArrayList<>();  // 차트 배경색 리스트
        if(dto.getUser_seq() == 0){
            UserVO user = (UserVO) Utils.getSession("login");
            dto.setUser_seq(user.getSeq());
        }
        dataList = service.chartByMM(dto);  // SUMMARY DATA ( by month )
        for (HashMap<String, Object> hmap : dataList) {
            bgColorList.add(hmap.get("bgcolor").toString());
        }
        model.addAttribute("dataList", dataList);
        model.addAttribute("bgColorList", bgColorList);
        return "/report/chart";
    }

    /**
     * TimeReport List
     * @param dto 캘린더, 차트 데이터 추출하기 위한 파라미터
     * @return 캘린더 데이터
     */
    @RequestMapping("/datalist")
    @ResponseBody
    private List<TimeReportVO> dataList(TimeReportListVO dto) {
        List<TimeReportVO> reportList;
        if(dto.getUser_seq() == 0){
            UserVO user = (UserVO) Utils.getSession("login");
            dto.setUser_seq(user.getSeq());
        }
        reportList = service.list(dto);
        return reportList;
    }

    /**
     * 데이터 삭제
     * @param seq
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public HashMap<String, Object> delete(@RequestParam(value = "seq") int seq) {
        HashMap<String, Object> resultMap = new HashMap<>();
        int result = 0;
        try {
            result = service.delete(seq);
            if (result > 0) {
                resultMap.put("RESULT_CODE", "SUCCESS");
                resultMap.put("RESULT_MSG", "삭제되었습니다.");
            } else {
                resultMap.put("RESULT_CODE", "FAIL");
                resultMap.put("RESULT_MSG", "삭제 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 등록
     * @param dataList
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    public HashMap<String, Object> save(@RequestBody ArrayList<TimeReportSaveVO> dataList) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        UserVO user = (UserVO) Utils.getSession("login");
        String[] seq;    // project, option_seq
        int cnt = 0;
        int diffDay = 0;
        for (TimeReportSaveVO vo : dataList) {
            vo.setUser_seq(user.getSeq()); // TimeReport 등록 아이디
            seq = vo.getSeq().split("\\|");
            vo.setProject_seq(Integer.parseInt(seq[0]));
            vo.setOption_seq(seq[1]);
            // ALL DAY
            if (StringUtils.equals(vo.getAllday(), "true")) {
                diffDay = (Utils.getDateDiff(vo.getStart_date(), vo.getEnd_date()) == 1) ? 1 : Utils.getDateDiff(vo.getStart_date(), vo.getEnd_date()) - 1;
                vo.setHour(diffDay * 8);
            } else {
                vo.setHour(Utils.getHour(vo.getEnd_date()) - Utils.getHour(vo.getStart_date()));
            }
            if (service.add(vo) > 0) cnt++;
        }
        // 전체 실패
        if (cnt == 0) {
            resultMap.put("RESULT_CODE", "FAIL[ALL]");
            resultMap.put("RESULT_MSG", "저장 실패");
        } else if (cnt < dataList.size()) {    // 일부 실패
            resultMap.put("RESULT_CODE", "FAIL");
            resultMap.put("RESULT_MSG", "일부 실패");
        } else {
            resultMap.put("RESULT_CODE", "SUCCESS");
            resultMap.put("RESULT_MSG", "저장되었습니다.");
        }
        return resultMap;
    }

    /**
     * 월별 수정 권한 확인
     * @param year  년
     * @param month 월
     * @return string : false, true
     */
    @RequestMapping("/check")
    @ResponseBody
    public String check(int year, int month) {
        String result;
        OpenCheckVO check = statsService.getOpenCheck(year, month);
        if (check == null) result = "false";
        else result = check.getIsopen();
        return result;
    }

    @RequestMapping("/excel")
    public ModelAndView excel(ExcelListVO dto, ModelAndView mav) {
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<ExcelProjectVO> dataList = new ArrayList<>();         // 각 프로젝트를 기준으로 하여 데이터 담음
        HashMap<Integer, Object> dateTotal = new HashMap<>();           // 날짜별 총 업무시간 ( footer )
        MemberVO memberVO = memberService.content(dto.getUser_seq());   /*  사원 정보   */
        ArrayList<HashMap<String, Object>> prjList = projectService.schList(new ProjectSchDTO("ST001", memberVO.getDept())); /* 해당 사원의 프로젝트 리스트 */

        int FIRST_DAY_OF_WEEK = 0;
        Calendar startCal = Utils.setCalendar(dto.getYear(),dto.getMonth(),1);
        Calendar nextCal = Utils.setCalendar(dto.getYear(), dto.getMonth()+1, 1);
        dto.setStart_date(Utils.convertCalToStr(startCal,"yyyy-MM-dd"));
        dto.setNext_date(Utils.convertCalToStr(nextCal,"yyyy-MM-dd")); // 해당 월만의 옵션 데이터를 가져오기 위해 다음 달 첫번째 날짜 담는다.
        String date = Utils.convertCalToStr(startCal, "yyyy-M");
        FIRST_DAY_OF_WEEK = startCal.get(Calendar.DAY_OF_WEEK);    // 1일의 요일
        for (int i = 1; i <= startCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) dayList.add(i); /*      해당 월의 일 리스트     */

        /*  DATA 관련 코드
         *  1. 한 프로젝트에 해당하는 모든 데이터 추출
         *  2. 데이터 중 기간일 경우, 시작일, 마감일 사이에 있는 데이터 생성
         *  3. TOTAL : 프로젝트 옵션 당 총 업무시간
         *  4. DATE TOTAL : 일(DAY) 별 총 업무시간                             */
        ExcelProjectVO vo;
        ArrayList<ExcelOptionVO> optionList;
        HashMap<Integer, Object> dateInfo; // 날짜 별 데이터
        int hour;
        int total;  // 프로젝트 > 옵션의 총 업무시간
        int count;
        for (HashMap<String, Object> project : prjList) {
            vo = new ExcelProjectVO();
            dto.setProject_seq(Integer.parseInt(project.get("seq").toString()));
            vo.setProject_name(project.get("project_name").toString());
            optionList = service.excel(dto);
            // 한 프로젝트의 옵션 리스트
            for (ExcelOptionVO option : optionList) {
                /*      초기화      */
                total = 0;
                dateInfo = new HashMap<>();
                count = option.getDataList().size();
                while (count > 0) {
                    count--;
                    ExcelOptionDataVO data = option.getDataList().get(count);
                    // 기간일 경우 start_date, end_date 사이에 있는 데이터 생성
                    if (data.getDuration() > 0) {
                        for (int day = data.getDay(); day < data.getDay() + data.getDuration(); day++) {
                            hour = data.getHour() / data.getDuration();
                            dateInfo.put(day, hour);
                            /* 일(DAY)별 TOTAL */
                            if (dateTotal.containsKey(day))
                                hour = Integer.parseInt(dateTotal.get(day).toString()) + hour;
                            dateTotal.put(day, hour);
                        }
                    } else {
                        hour = data.getHour();
                        dateInfo.put(data.getDay(), data.getHour());
                        /* 일(DAY)별 TOTAL */
                        if (dateTotal.containsKey(data.getDay()))
                            hour = Integer.parseInt(dateTotal.get(data.getDay()).toString()) + hour;
                        dateTotal.put(data.getDay(), hour);
                    }
                    total += data.getHour();
                }
                option.setTotal(total);
                option.setDateInfo(dateInfo);
            }
            vo.setOptionList(optionList);
            dataList.add(vo);
        }
        Utils.getRequest().setAttribute("fileName", memberVO.getName() + "_" + date);  // 파일 이름
        mav.addObject("title", memberVO.getName() + "(" + date +")");                   // Title
        mav.addObject("FIRST_DAY_OF_WEEK", FIRST_DAY_OF_WEEK);
        mav.addObject("dayList", dayList);
        mav.addObject("dataList", dataList);
        mav.addObject("dateTotal", dateTotal);  // 총 업무시간
        mav.setViewName("excel/report");
        return mav;
    }
}
