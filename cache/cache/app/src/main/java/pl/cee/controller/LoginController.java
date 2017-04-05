package pl.cee.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cee.model.Credentials;
import pl.cee.model.State;
import pl.cee.model.User;
import pl.cee.service.AccountsProvider;
import pl.cee.service.SelfAddressProvider;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/")
public class LoginController {

    private final AccountsProvider accountsProvider;
    private final SelfAddressProvider selfAddressProvider;

    public LoginController(AccountsProvider accountsProvider,
                           SelfAddressProvider selfAddressProvider) {
        this.accountsProvider = accountsProvider;
        this.selfAddressProvider = selfAddressProvider;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGet() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(HttpServletRequest request,
                           Model model) {
        State appState = (State) request.getSession().getAttribute("appState");
        String selfAddress = selfAddressProvider.getSelfAddress();

        model.addAttribute("appState", appState);
        model.addAttribute("selfAddress", selfAddress);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute Credentials credentials,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        User user = accountsProvider.getUser(credentials);

        if (user != null) {
            State state = new State(user);
            request.getSession().setAttribute("state", state);

        } else {
            redirectAttributes.addFlashAttribute("reqState", "fail");
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGet(HttpServletRequest request) {
        request.getSession().removeAttribute("state");
        return "redirect:/login";
    }
}
