package net.slipp.web;

import net.slipp.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginedUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);

        if(sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if(!isLoginedUser(session)) {
            return null;
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
