package pl.cee.controller;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cee.model.AppState;
import pl.cee.model.Credentials;
import pl.cee.model.UserInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/")
public class LoginController {

    private static final String COOKIE_NAME = "session.id";

    private final Random random = new SecureRandom();
    private final Map<Credentials, UserInfo> accounts = new HashMap<>(3);
    private final IgniteCache<String, AppState> cache;

    @Autowired
    public LoginController(Ignite ignite) {
        accounts.put(new Credentials("admin", "admin"), new UserInfo("administrator"));
        accounts.put(new Credentials("student", "student"), new UserInfo("student"));
        accounts.put(new Credentials("mr", "bean"), new UserInfo("Mr. Bean"));
        cache = ignite.cache("someCache");
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,
                        @CookieValue(value = COOKIE_NAME, required = false, defaultValue = "") String sessionID
    ) {
        AppState appState = cache.get(sessionID);

        model.addAttribute("reqState", "none");
        model.addAttribute("appState", appState);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model,
                        @ModelAttribute Credentials credentials,
                        HttpServletResponse response
    ) {
        UserInfo userInfo = accounts.get(credentials);

        if (userInfo != null) {
            String sessionID = new BigInteger(130, random).toString(32);
            AppState appState = new AppState(userInfo);

            cache.put(sessionID, appState);
            response.addCookie(new Cookie(COOKIE_NAME, sessionID));
            return "redirect:/login";

        } else {
            model.addAttribute("reqState", "fail");
            model.addAttribute("appState", null);
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue(COOKIE_NAME) String sessionID) {
        AppState appState = cache.get(sessionID);
        if (appState != null) {
            cache.remove(sessionID);
        }

        return "redirect:/login";
    }
}
