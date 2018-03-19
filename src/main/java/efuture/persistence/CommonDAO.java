package efuture.persistence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 프로젝트 관리
 * Created by user on 2017-03-29.
 */
public interface CommonDAO {

    /*  공통코드 출력 */
    ArrayList<HashMap<String,Object>> list(HashMap<String,Object> hmap);
}
