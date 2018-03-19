package efuture.domain.project;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-19.
 * 프로젝트 리스트 불러올 시 조건
 */
@Data
public class ProjectSchDTO {

    private String state = null;    // 프로젝트 상황
    private String dept = null;     // 프로젝트 관련 부서 ( 1개일 경우 )
    private ArrayList<String> deptList = new ArrayList<>(); // 프로젝트 관련 부서 여러개일 경우

    public ProjectSchDTO(ArrayList<String> deptList) {
        this.deptList = deptList;
    }
    public ProjectSchDTO(String state, String dept) {
        deptList.add(dept);
        this.state = state;
        this.dept = dept;
    }
}
