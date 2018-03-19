package efuture.domain.report.excel;

import lombok.Data;

import java.util.Date;

/**
 * Created by user on 2017-07-13.
 * 한 옵션에 해당하는 데이터
 */
@Data
public class ExcelOptionDataVO {
    private int hour =0;        // 총 업무시간
    private int duration =0;    // 기간 (DAY)
    private int day = 0;        // 일
    private Date start_date;    // 시작일
    private Date end_date;
}