package efuture.domain.report;

import lombok.Data;

import java.util.Date;

/**
 * Created by user on 2017-06-05.
 */
@Data
public class TimeReportSaveVO {

    private String seq;
    private int user_seq;       // 등록자
    private int project_seq;    // 프로젝트 seq
    private String option_seq;     // 옵션 seq
    private Date start_date;    //시작일
    private Date end_date;      //마감일
    private int hour;           // 총 시간
    private String allday;      // 1일동안인지 여부
    private Date regdate;   // 등록일

    private String gubun;   // 구분 : DEFAULT, UPDATE
}
