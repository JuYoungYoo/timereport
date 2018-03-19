package efuture.domain.project;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * 프로젝트 테이블
 * Created by user on 2017-03-29.
 */
@Data
public class ProjectVO {

    private int seq;
    private String project_name;        // 프로젝트 명
    private String project_short_name;  // 약어
    private String state;               // 개발상황
    private Date complete_date;         // 개발완료일
    private Date regdate;               // 등록일
    private String bgcolor;             // 프로젝트 색
    private ArrayList<String> dept;     // 부서
}
