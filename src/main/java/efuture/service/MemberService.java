package efuture.service;

import efuture.domain.member.MemberListVO;
import efuture.domain.member.MemberSchDTO;
import efuture.domain.member.MemberVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 회원관리
 * Created by user on 2017-03-28.
 */
public interface MemberService {

    /*  회원 카운트  */
    int count(MemberListVO vo);
    /*  회원 리스트  */
    ArrayList<HashMap<String,Object>> list(MemberListVO vo);
    /*  회원 내용 */
    MemberVO content(int seq);
    /*  회원 등록   */
    int add(MemberVO vo);
    /*  회원 수정*/
    int update(MemberVO vo);
    /*회원 삭제 :::: 1계정씩 삭제 선택 */
    int delete(int seq);
    /*비밀번호 변경*/
    int updatePwd(HashMap<String,Object> hmap);

    /* [검색] 회원 리스트 todo : parameter arrayList로 변경 */
    ArrayList<HashMap<String,Object>> schList(MemberSchDTO dto);
}
