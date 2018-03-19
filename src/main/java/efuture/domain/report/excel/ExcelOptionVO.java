package efuture.domain.report.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-07-06.
 * 엑셀 다운로드
 * 한 프로젝트의 옵션정보이다.
 */
@Data
public class ExcelOptionVO {

    private int project_seq;
    private String option_seq;
    private String option_name;     // 옵션 명
    private int total = 0;          // 옵션에 해당하는 총 근무시간
    // DB에서 사용
    private ArrayList<ExcelOptionDataVO> dataList; // 한 옵션의 데이터 리스트 )
    // VIEW에서 사용
    private HashMap<Integer,Object> dateInfo; // day, hour
}
