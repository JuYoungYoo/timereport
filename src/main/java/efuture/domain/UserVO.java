package efuture.domain;

import lombok.Data;

/**
 * 로그인 정보
 * Created by user on 2017-03-29.
 */
@Data
public class UserVO {

    private static final long serialVersionUID = 8987889780557008769L;
    private int seq;
    private String userid;  // 로그인 아이디
    private String name;    // 로그인 이름
    private String grade;   // 권한
    private String dept;    // 부서
}
