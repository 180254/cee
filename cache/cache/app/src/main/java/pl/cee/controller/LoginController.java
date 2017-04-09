package pl.cee.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @GetMapping("/login")
    public String loginGet(HttpServletRequest request,
                           Model model) {
        State appState = (State) request.getSession().getAttribute("appState");
        String selfAddress = selfAddressProvider.getSelfAddress();

        model.addAttribute("appState", appState);
        model.addAttribute("selfAddress", selfAddress);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute Credentials credentials,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        User user = accountsProvider.getUser(credentials);

        if (user != null) {
            State appState = new State(user);
            request.getSession().setAttribute("appState", appState);

        } else {
            redirectAttributes.addFlashAttribute("reqState", "fail");
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutGet(HttpServletRequest request) {
        request.getSession().removeAttribute("appState");
        return "redirect:/login";
    }
}
