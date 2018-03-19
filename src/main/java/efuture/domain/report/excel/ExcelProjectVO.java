package efuture.domain.report.excel;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-07.
 */

/**
 * 엑셀 다운로드
 * 한 프로젝트를 기준으로 데이터 담는다.
 */
@Data
public class ExcelProjectVO {
    private String project_name;
    private ArrayList<ExcelOptionVO> optionList;    // 한 프로젝트의 해당하는 옵션 리스트
}
