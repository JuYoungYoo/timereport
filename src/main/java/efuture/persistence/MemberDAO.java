package efuture.persistence;

import efuture.domain.member.MemberListVO;
import efuture.domain.member.MemberSchDTO;
import efuture.domain.member.MemberVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-28.
 */
public interface MemberDAO {

    int count(MemberListVO vo);
    ArrayList<HashMap<String,Object>> list(MemberListVO vo);
    MemberVO content(int seq);
    int add(MemberVO vo);
    int update(MemberVO vo);
    int updatePwd(HashMap<String,Object> hmap);
    int delete(int seq);
    ArrayList<HashMap<String,Object>> schList(MemberSchDTO dto);
}
