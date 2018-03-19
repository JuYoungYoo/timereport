package efuture.service.impl;

import efuture.domain.project.ProjectListVO;
import efuture.domain.project.ProjectVO;
import efuture.domain.project.ProjectSchDTO;
import efuture.persistence.ProjectDAO;
import efuture.service.ProjectService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017-03-29.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private SqlSession session;

    @Override
    public int count(ProjectListVO vo) {
        return session.getMapper(ProjectDAO.class).count(vo);
    }

    @Override
    public ArrayList<ProjectVO> list(ProjectListVO vo) {
        return session.getMapper(ProjectDAO.class).list(vo);
    }

    @Override
    public ProjectVO content(int idx) {
        return session.getMapper(ProjectDAO.class).content(idx);
    }

    @Override
    public int add(ProjectVO vo) {
        return (session.getMapper(ProjectDAO.class).add(vo) > 0)? vo.getSeq() : 0;
    }

    @Override
    public int update(ProjectVO vo) {
        return session.getMapper(ProjectDAO.class).update(vo);
    }

    @Override
    public int delete(int seq) {
        return session.getMapper(ProjectDAO.class).delete(seq);
    }

    @Override
    public List<String> selectDept(int seq) {
        return session.getMapper(ProjectDAO.class).selectDept(seq);
    }

    @Override
    public int deleteDept(int seq) {
        return session.getMapper(ProjectDAO.class).deleteDept(seq);
    }

    @Override
    public int addDept(int seq, ArrayList<String> deptList) {
        int result = 0;
        try{
            for(String dept : deptList){
                result = session.getMapper(ProjectDAO.class).addDept(seq, dept);
            }
        }catch(Exception e){
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
    @Override
    public ArrayList<HashMap<String, Object>> schList(ProjectSchDTO dto) {
        return session.getMapper(ProjectDAO.class).schList(dto);
    }

    @Override
    public int isUseBySeq(int seq) {
        return session.getMapper(ProjectDAO.class).isUseBySeq(seq);
    }
}
