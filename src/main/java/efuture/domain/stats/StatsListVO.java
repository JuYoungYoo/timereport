package efuture.domain.stats;

import lombok.Data;

import java.util.ArrayList;

/**
 * 업무시간 통계 검색 INFO
 * Created by user on 2017-06-16.
 */
@Data
public class StatsListVO {
    private String date;
    private int year = 0;
    private int month = 0;
    private int project_seq = 0;
    private String option_seq;
    private ArrayList<String> dept;
    private ArrayList<String> memberList;
    private String deptName;

    public void setYear(Object o){
        this.year = Integer.parseInt(o.toString());
    }
    public int getYear(){
        return this.year;
    }
    public void setMonth(Object o){
        this.month = Integer.parseInt(o.toString());
    }
    public int getMonth(){
        return this.month;
    }

    public void setDate(String before){
        String[] str = before.split("-");
        this.year = Integer.parseInt(str[0]);
        this.month = Integer.parseInt(str[1]);
        this.date = before;
    }

    public void setProject_seq(Object project_seq){
        this.project_seq = Integer.parseInt(project_seq.toString());
    }

}
