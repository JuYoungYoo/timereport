package efuture.domain.report.excel;

import lombok.Data;

/**
 * Created by user on 2017-07-14.
 * 엑셀 다운로드 시 데이터 추출을 위한 파라미터
 */
@Data
public class ExcelListVO {
    private int user_seq = 0;
    private int year = 0;
    private int month = 0;
    private int project_seq = 0;
    private String start_date = null;
    private String next_date = null;
}
