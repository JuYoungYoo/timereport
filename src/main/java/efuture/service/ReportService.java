package efuture.service;

import efuture.domain.report.TimeReportListVO;
import efuture.domain.report.TimeReportVO;
import efuture.domain.report.TimeReportSaveVO;
import efuture.domain.report.excel.ExcelListVO;
import efuture.domain.report.excel.ExcelOptionVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
public interface ReportService {

    /*TimeReport 리스트 */
    ArrayList<TimeReportVO> list(TimeReportListVO dto);
    /* TimeReport 저장*/
    int add(TimeReportSaveVO vo);
    /* TimeReport 삭제*/
    int delete(int seq);
    /*  월의 전체 통계    */
    ArrayList<HashMap<String,Object>> chartByMM(TimeReportListVO dto);
    /*  엑셀 데이터 :::::::::: 프로젝트 별 데이터  */
    ArrayList<ExcelOptionVO> excel(ExcelListVO dto);
}
