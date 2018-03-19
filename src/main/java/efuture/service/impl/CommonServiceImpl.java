package efuture.service.impl;

import efuture.persistence.CommonDAO;
import efuture.service.CommonService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
@Service
public class CommonServiceImpl implements CommonService{

    @Autowired private SqlSession session;

    @Override
    public ArrayList<HashMap<String, Object>> list(String groupCode) {
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("groupCode", groupCode);
        return session.getMapper(CommonDAO.class).list(hmap);
    }

    @Override
    public ArrayList<HashMap<String, Object>> list(String groupCode, String orderBy) {
        HashMap<String,Object> param = new HashMap<>();
        param.put("groupCode", groupCode);
        param.put("orderBy", orderBy);
        return session.getMapper(CommonDAO.class).list(param);
    }
}
