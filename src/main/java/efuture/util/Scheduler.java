package efuture.util;

import efuture.domain.stats.OpenCheckVO;
import efuture.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017-06-27.
 */
@Component
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private StatsService statsService;
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    /**
     * 매월 1일 수정권한 등록
     * tb_open_check : default true ( 수정가능 )
     */
    @Scheduled(cron="0 0 0 1 * ?") // 매월 1일 0시에 실행
    public void openCheck(){
        Date date = new Date();
        logger.info("::::::::::[ Scheduler, INSERT Open Check" + sdf.format(date.getTime()) + "]::::::::");
        try{
            OpenCheckVO vo = new OpenCheckVO();
            vo.setYear(Utils.getYear(date));
            vo.setMonth(Utils.getMonth(date));
            vo.setIsopen("true");
            statsService.addOpenCheck(vo);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 매월 3일 전월 수정 ClOSE
     * tb_open_check : default false ( 수정불가 )
     */
    @Scheduled(cron="0 0 0 3 * ?")
    public void closeCheck(){
        OpenCheckVO vo = null;
        Date date = Utils.changeMonthByDate(new Date(),-1);
        logger.info("::::::::::[ Scheduler, CLOSE Open Check " + sdf.format(date.getTime()) +"]::::::::");
        try{
            vo = statsService.getOpenCheck(Utils.getYear(date), Utils.getMonth(date));
            vo.setIsopen("false");
            statsService.changeOpenCheck(vo);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
}
