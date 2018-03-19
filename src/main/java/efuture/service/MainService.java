package efuture.service;

import efuture.domain.UserVO;

import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
public interface MainService{
    /*  로그인 체크  */
    UserVO loginCheck(HashMap<String,Object> hmap);
    /*  로그인 히스토리 추가 */
    void loginHistory(HashMap<String,Object> hmap);
}
