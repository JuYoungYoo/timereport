package efuture.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by user on 2017-03-29.
 */
@Component
public class Utils {

    public static Calendar setCalendar(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        cal.set(Calendar.MILLISECOND,0);
        return cal;
    }
    public static String convertCalToStr(Calendar cal, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }
    // DateFormat
    public static int getDateDiff(Date start, Date end){
        Calendar stCal = Calendar.getInstance();
        stCal.setTime(start);
        stCal.set(Calendar.HOUR_OF_DAY, 0);
        stCal.set(Calendar.MINUTE, 0);
        stCal.set(Calendar.SECOND, 0);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);

        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(endCal.getTimeInMillis() - stCal.getTimeInMillis());
        return result.get(Calendar.DAY_OF_MONTH);
    }
    // Date Format Calendar Hour
    public static int getHour(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
    }

    public static Date changeMonthByDate(Date date, int m){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, m);
        return cal.getTime();
    }
    // Client IP 주소
    public static String getClientIP(){
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }
    public static void setRequest(String key, Object value){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(key,value);
    }

    public static Object getSession(String key){
        HttpSession httpSession = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
        return httpSession.getAttribute(key);
    }

    public static void setSession(String key, Object value){
        HttpSession httpSession = ((ServletRequestAttributes ) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
        httpSession.setMaxInactiveInterval(60*60*3);
        httpSession.setAttribute(key,value);
    }
    public static void removeSession(String key){
        HttpSession httpSession = ((ServletRequestAttributes ) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
        httpSession.removeAttribute(key);
    }

    public static void setCookie(String name, String value){
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
        Cookie cookie = new Cookie(name, String.valueOf(value));
        cookie.setMaxAge(60*60*3);  // 한시간
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookie(String name){
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String result = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals(name)){
                    result = cookies[i].getValue().trim();
                    break;
                }
            }
        }
        return result;
    }

    public static String getProrValByVertx(String key){
        String propValue = null;
        try {
            //classpath상 파일에 InputStream을 가져온다
            InputStream is = Utils.class.getClassLoader().getResourceAsStream("/properties/vertx.properties");
            Properties props = new Properties();
            //파일 InputStream을 Properties 객체로 읽어온다
            props.load(is);
            //파일에 key 에 값을 가져온다 = "/com/test/key"
            propValue = (String) props.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propValue;
    }



    /*******************    Velocity ToolBox    **************************/
    public boolean containsKey(HashMap hmap, Object key){
        return hmap.containsKey(key.toString());
    }

    /**
     * 문자열 콤마 > 줄바꿈으로 변환 ( <br> )
     * @return String
     */
    public static String replaceTag(String string){
        return string.replaceAll("[|]","<br/>");
    }

    /**
     * 문자열 콤마 > 줄바꿈으로 변환 (\n)
     * @return String
     */
    public static String replaceData(String string){
        return string.replaceAll("[|]","\n");
    }
}
