package efuture.service.impl;

import efuture.domain.report.TimeReportListVO;
import efuture.domain.report.TimeReportVO;
import efuture.domain.report.TimeReportSaveVO;
import efuture.domain.report.excel.ExcelListVO;
import efuture.domain.report.excel.ExcelOptionVO;
import efuture.persistence.ReportDAO;
import efuture.service.ReportService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Service
public class ReportServiceImpl implements ReportService{

    @Autowired private SqlSession session;

    @Override
    public ArrayList<TimeReportVO> list(TimeReportListVO dto) {
        return session.getMapper(ReportDAO.class).list(dto);
    }

    @Override
    public int delete(int seq) {
        return session.getMapper(ReportDAO.class).delete(seq);
    }

    @Override
    public int add(TimeReportSaveVO vo) {
        return session.getMapper(ReportDAO.class).add(vo);
    }

    @Override
    public ArrayList<HashMap<String,Object>> chartByMM(TimeReportListVO dto) {
        return session.getMapper(ReportDAO.class).chartByMM(dto);
    }

    @Override
    public ArrayList<ExcelOptionVO> excel(ExcelListVO dto) {
        return session.getMapper(ReportDAO.class).excel(dto);
    }
}
