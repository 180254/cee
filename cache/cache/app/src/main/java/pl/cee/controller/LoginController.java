package pl.cee.controller;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cee.model.Credentials;
import pl.cee.model.State;
import pl.cee.model.User;
import pl.cee.service.SelfAddressProvider;
import pl.cee.service.SessionIdGenerator;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RequestMapping("/")
public class LoginController {

    private static final String SESSION_COOKIE_NAME = "cee.session.id";
    private static final int SESSION_EXPIRE_MINUTES = 5;

    private final IgniteCache<String, State> cache;
    private final SessionIdGenerator sessionIdGenerator;
    private final SelfAddressProvider selfAddressProvider;
    private final Map<Credentials, User> accounts;

    public LoginController(Ignite ignite, SessionIdGenerator sid, SelfAddressProvider sap) {
        ExpiryPolicy expiryPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.MINUTES, SESSION_EXPIRE_MINUTES));
        this.cache = ignite.<String, State>cache("sessionCache").withExpiryPolicy(expiryPolicy);
        this.sessionIdGenerator = sid;
        this.selfAddressProvider = sap;

        this.accounts = new HashMap<>(3);
        this.accounts.put(new Credentials("admin", "admin"), new User("administrator"));
        this.accounts.put(new Credentials("student", "student"), new User("student"));
        this.accounts.put(new Credentials("mr", "bean"), new User("Mr. Bean"));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGet() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(@CookieValue(value = SESSION_COOKIE_NAME, defaultValue = "") String sessionId,
                           Model model) {
        State appState = cache.get(sessionId);
        model.addAttribute("appState", appState);
        model.addAttribute("selfAddress", selfAddressProvider.get());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute Credentials credentials,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
        User user = accounts.get(credentials);

        if (user != null) {
            String sessionId = sessionIdGenerator.nextSessionId();
            State appState = new State(user);
            cache.put(sessionId, appState);
            response.addCookie(new Cookie(SESSION_COOKIE_NAME, sessionId));
            return "redirect:/login";

        } else {
            redirectAttributes.addFlashAttribute("reqState", "fail");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGet(@CookieValue(value = SESSION_COOKIE_NAME, defaultValue = "") String sessionId) {
        cache.remove(sessionId);
        return "redirect:/login";
    }
}
