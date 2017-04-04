package pl.cee.controller;

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
import pl.cee.service.AccountsProvider;
import pl.cee.service.SelfAddressProvider;
import pl.cee.service.SessionIdGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/")
public class LoginController {

    private static final String SESSION_COOKIE_NAME = "cee.session.id";

    private final IgniteCache<String, State> cache;
    private final AccountsProvider accountsProvider;
    private final SessionIdGenerator sessionIdGenerator;
    private final SelfAddressProvider selfAddressProvider;

    public LoginController(IgniteCache<String, State> cache,
                           AccountsProvider accountsProvider,
                           SessionIdGenerator sessionIdGenerator,
                           SelfAddressProvider selfAddressProvider) {
        this.cache = cache;
        this.accountsProvider = accountsProvider;
        this.sessionIdGenerator = sessionIdGenerator;
        this.selfAddressProvider = selfAddressProvider;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGet() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(@CookieValue(value = SESSION_COOKIE_NAME, defaultValue = "") String sessionId,
                           Model model) {
        State appState = cache.get(sessionId);
        String selfAddress = selfAddressProvider.getSelfAddress();

        model.addAttribute("appState", appState);
        model.addAttribute("selfAddress", selfAddress);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute Credentials credentials,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
        User user = accountsProvider.getUser(credentials);

        if (user != null) {
            String sessionId = sessionIdGenerator.nextSessionId();
            State appState = new State(user);
            cache.put(sessionId, appState);
            response.addCookie(new Cookie(SESSION_COOKIE_NAME, sessionId));

        } else {
            redirectAttributes.addFlashAttribute("reqState", "fail");
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGet(@CookieValue(value = SESSION_COOKIE_NAME, defaultValue = "") String sessionId,
                            HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        cache.remove(sessionId);
        return "redirect:/login";
    }
}
