package efuture.service.impl;

import efuture.domain.stats.OpenCheckVO;
import efuture.domain.stats.StatsListVO;
import efuture.persistence.StatsDAO;
import efuture.service.StatsService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Service
public class StatsServiceImpl implements StatsService{

    @Autowired
    private SqlSession session;

    @Override
    public ArrayList<HashMap<String, Object>> list(StatsListVO vo) {
        return session.getMapper(StatsDAO.class).list(vo);
    }
    @Override
    public HashMap<String, Object> total(StatsListVO vo) {
        return session.getMapper(StatsDAO.class).total(vo);
    }
    @Override
    public OpenCheckVO getOpenCheck(int year, int month) {
        return session.getMapper(StatsDAO.class).getOpenCheck(year,month);
    }
    @Override
    public int changeOpenCheck(OpenCheckVO vo) {
        return session.getMapper(StatsDAO.class).changeOpenCheck(vo);
    }

    @Override
    public int addOpenCheck(OpenCheckVO vo) {
        return session.getMapper(StatsDAO.class).addOpenCheck(vo);
    }
}
