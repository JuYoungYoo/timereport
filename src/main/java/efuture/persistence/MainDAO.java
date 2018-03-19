package efuture.persistence;

import efuture.domain.UserVO;

import java.util.HashMap;

/**
 * Created by user on 2017-03-28.
 */
public interface MainDAO {

    /*  로그인 체크  */
    UserVO loginCheck(HashMap<String,Object> hmap);
    /*  로그인 기록 */
    void loginHistory(HashMap<String,Object> hmap);
}
