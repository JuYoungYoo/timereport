package efuture.domain.project;

import efuture.domain.Page;
import lombok.Data;

/**
 * Created by user on 2017-05-26.
 * 프로젝트 관리 페이지 리스트 조건
 */
@Data
public class ProjectListVO {
    private int current = 1;        // 현 페이지
    private int perPageNum = 10;    // 한 페이지에 보여줄 게시글 수
    private String ST_CODE;     // 개발상황 별 검색
    /*      검색      */
    private String schValue;    // 프로젝트 검색
    /*      정렬순서  */
    private String orderBy;
    /*      페이징     */
    private Page page;
}
