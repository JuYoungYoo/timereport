package efuture.persistence;

import efuture.domain.project.ProjectListVO;
import efuture.domain.project.ProjectVO;
import efuture.domain.project.ProjectSchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 프로젝트 관리
 * Created by user on 2017-03-29.
 */
public interface ProjectDAO {

    /*  프로젝트 카운트  */
    int count(ProjectListVO vo);
    /*  프로젝트 리스트  */
    ArrayList<ProjectVO> list(ProjectListVO vo);
    /*  프로젝트 내용보기   */
    ProjectVO content(int idx);
    /*  프로젝트 등록   */
    int add(ProjectVO vo);
    /*  프로젝트 수정   */
    int update(ProjectVO vo);
    /*  프로젝트 삭제   */
    int delete(int seq);
    /*  프로젝트 부서 리스트   */
    List<String> selectDept(int seq );
    /*  프로젝트 부서 등록   */
    int addDept(@Param("seq") int seq, @Param("dept") String dept);
    /*  프로젝트 부서 삭제   */
    int deleteDept(int seq);

    ArrayList<HashMap<String,Object>> schList(ProjectSchDTO dto);
    /* 프로젝트 사용 유무 */
    int isUseBySeq(int seq);



}
