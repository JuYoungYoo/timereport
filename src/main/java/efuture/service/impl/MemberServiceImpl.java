package efuture.service.impl;

import efuture.domain.member.MemberListVO;
import efuture.domain.member.MemberSchDTO;
import efuture.domain.member.MemberVO;
import efuture.persistence.MemberDAO;
import efuture.service.MemberService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-28.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired private SqlSession session;

    @Override
    public int count(MemberListVO vo) {
        return session.getMapper(MemberDAO.class).count(vo);
    }

    @Override
    public ArrayList<HashMap<String, Object>> list(MemberListVO vo) {
        return session.getMapper(MemberDAO.class).list(vo);
    }
    @Override
    public MemberVO content(int seq) {
        return session.getMapper(MemberDAO.class).content(seq);
    }
    @Override
    public int updatePwd(HashMap<String, Object> hmap) {
        return session.getMapper(MemberDAO.class).updatePwd(hmap);
    }

    @Override
    public int delete(int seq) { return session.getMapper(MemberDAO.class).delete(seq);}

    @Override
    public int update(MemberVO vo) {
        return session.getMapper(MemberDAO.class).update(vo);
    }

    @Override
    public int add(MemberVO vo) {
        return session.getMapper(MemberDAO.class).add(vo);
    }

    @Override
    public ArrayList<HashMap<String, Object>> schList(MemberSchDTO dto) {
        return session.getMapper(MemberDAO.class).schList(dto);
    }
}
