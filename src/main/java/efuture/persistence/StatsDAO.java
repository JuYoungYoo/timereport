package efuture.persistence;

import efuture.domain.stats.OpenCheckVO;
import efuture.domain.stats.StatsListVO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 업무시간 통계
 * Created by user on 2017-03-29.
 */
public interface StatsDAO {

    /*  월 별 통계 리스트        */
    ArrayList<HashMap<String,Object>> list(StatsListVO vo);
    /*  각 사원의 TOTAL 업무시간   */
    HashMap<String,Object> total(StatsListVO vo);
    /*  해당 년/월 수정 권한    */
    OpenCheckVO getOpenCheck(@Param("year") int year, @Param("month") int month);
    /*  해당 년/월 수정 권한 변경 */
    int changeOpenCheck(OpenCheckVO vo);
    /*  해당 년/월 수정 권한 등록*/
    int addOpenCheck(OpenCheckVO vo);
}
