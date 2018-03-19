package efuture.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-03-29.
 */
public interface CommonService {
    /**
     * 공통코드 리스트
     * @param  groupCode , orderBy 순서 (default : code_val desc )
     * @return
     */
    ArrayList<HashMap<String,Object>> list(String groupCode);
    ArrayList<HashMap<String,Object>> list(String groupCode, String orderBy);
}
