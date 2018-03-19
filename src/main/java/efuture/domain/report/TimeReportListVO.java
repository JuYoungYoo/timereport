package efuture.domain.report;

import lombok.Data;

/**
 * Created by user on 2017-07-18.
 * 캘린더,차트 원하는 데이터 추출하는데 필요한 조건 파라미터
 */
@Data
public class TimeReportListVO {

    /*  공통 */
    private int user_seq = 0;
    /*  Calendar 데이터  */
    private String start_date = null;
    private String end_date = null;
    /*  차트  */
    int year = 0;
    int month = 0;
}
