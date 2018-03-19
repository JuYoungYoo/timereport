package efuture.domain.stats;

import lombok.Data;

import java.util.Date;

/**
 * Open Check 테이블
 * 수정권한 관련 테이블
 * Created by user on 2017-03-29.
 */
@Data
public class OpenCheckVO {

    private int seq;
    private int year;      // 년
    private int month;      // 월
    private String isopen;     // 수정권한제어 (true: 수정 가능 / false : 수정 불가)
    private Date regdate;   // 등록일
    private Date editdate; // 수정일

}
