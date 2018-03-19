package efuture.domain.member;

import lombok.Data;

import java.util.Date;

/**
 * 회원 테이블
 * Created by user on 2017-03-29.
 */
@Data
public class MemberVO {

    private int seq;
    private String userid;  // 아이디
    private String pwd;  // 비밀번호
    private String name;    // 이름
    private String dept;      // 부서 TB_COMMON_MASTESR 참고
    private String grade;      // 회원권한 TB_COMMON_MASTESR 참고
    private Date regdate; // 등록일

}
