package efuture.persistence;

import efuture.domain.project.ProjectVO;
import efuture.domain.report.TimeReportListVO;
import efuture.domain.report.TimeReportVO;
import efuture.domain.report.TimeReportSaveVO;
import efuture.domain.report.excel.ExcelListVO;
import efuture.domain.report.excel.ExcelOptionVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 프로젝트 관리
 * Created by user on 2017-03-29.
 */
public interface ReportDAO {

    /*  타임리포트 리스트  */
    ArrayList<TimeReportVO> list(TimeReportListVO dto);
    /*  타임리포트 등록   */
    int add(TimeReportSaveVO vo);
    /*  타임리포트 수정   */
    int update(ProjectVO vo);
    /*  타임리포트 삭제   */
    int delete(int seq);
    /*  월 별 통계        */
    ArrayList<HashMap<String,Object>> chartByMM(TimeReportListVO dto);
    /*  엑셀 데이터 :::::::::: 프로젝트 별 데이터  */
    ArrayList<ExcelOptionVO> excel(ExcelListVO dto);

}
