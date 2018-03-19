package efuture.domain.member;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-19.
 * 프로젝트 리스트 불러올 시 조건
 */
@Data
public class MemberSchDTO {

    private String dept = null;
    private ArrayList<String> deptList = new ArrayList<>();

    public MemberSchDTO() {
    }

    public MemberSchDTO(ArrayList<String> deptList) {
        this.deptList = deptList;
    }

    public void setDept(String dept){
        deptList.add(dept);
        this.dept = dept;
    }
    public String getDept(){
        return this.dept;
    }
}
