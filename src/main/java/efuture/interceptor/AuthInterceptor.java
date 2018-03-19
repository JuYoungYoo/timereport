package efuture.interceptor;

import efuture.domain.UserVO;
import efuture.util.Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by user on 2017-03-30.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Session 없을 시 로그인 페이지로 이동
            HttpSession session = request.getSession();
            UserVO loginVO = (UserVO) Utils.getSession("login");
            if (session == null || loginVO == null) {
                response.sendRedirect("/");
                return false;
            }
        return true;
    }
}
