package efuture.service.impl;

import efuture.domain.UserVO;
import efuture.persistence.MainDAO;
import efuture.service.MainService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Service
public class MainServiceImpl implements MainService{

    @Autowired private SqlSession session;

    @Override
    public UserVO loginCheck(HashMap<String, Object> hmap) {
        return session.getMapper(MainDAO.class).loginCheck(hmap);
    }
    @Override
    public void loginHistory(HashMap<String, Object> hmap) {
        session.getMapper(MainDAO.class).loginHistory(hmap);
    }
}
