package pl.cee.controller;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cee.model.Credentials;
import pl.cee.model.State;
import pl.cee.model.User;
import pl.cee.service.SessionIdGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/")
public class LoginController {

    private static final String COOKIE_NAME = "session.id";

    private final Map<Credentials, User> accounts = new HashMap<>(3);
    private final SessionIdGenerator sessionIdGenerator;
    private final IgniteCache<String, State> cache;

    public LoginController(Ignite ignite, SessionIdGenerator sessionIdGenerator) {
        this.accounts.put(new Credentials("admin", "admin"), new User("administrator"));
        this.accounts.put(new Credentials("student", "student"), new User("student"));
        this.accounts.put(new Credentials("mr", "bean"), new User("Mr. Bean"));

        this.sessionIdGenerator = sessionIdGenerator;
        this.cache = ignite.cache("sessionCache");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,
                        @CookieValue(value = COOKIE_NAME, required = false, defaultValue = "") String sessionId) {
        State appState = cache.get(sessionId);

        model.addAttribute("reqState", "none");
        model.addAttribute("appState", appState);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model,
                        @ModelAttribute Credentials credentials,
                        HttpServletResponse response) {
        User user = accounts.get(credentials);

        if (user != null) {
            String sessionId = sessionIdGenerator.nextSessionId();
            State state = new State(user);

            cache.put(sessionId, state);
            response.addCookie(new Cookie(COOKIE_NAME, sessionId));
            return "redirect:/login";

        } else {
            model.addAttribute("reqState", "fail");
            model.addAttribute("appState", null);
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue(value = COOKIE_NAME, required = false, defaultValue = "") String sessionId) {
        cache.remove(sessionId);
        return "redirect:/login";
    }
}
