package efuture.domain.member;

import efuture.domain.Page;
import lombok.Data;

/**
 * Created by user on 2017-05-15.
 */
@Data
public class MemberListVO {

    private int current = 1;        // 현 페이지
    private int perPageNum = 10;     // 한 페이지에 보여줄 게시글 수
    private String DP_CODE;        // 부서 코드
    /*      검색      */
    private String schKind;
    private String schValue;
    /*      정렬순서  */
    private String orderBy;
    /*      페이징     */
    private Page page;
}
