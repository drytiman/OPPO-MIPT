package banks.server.controllers;

import banks.server.repositories.AllRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private AllRoleRepository allRoleRepository;

    @GetMapping("/home")
    public String home(@RequestParam(name = "type", required = false, defaultValue = "300") String name, Model model) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();

        if (allRoleRepository.findByUserlogin(auth1.getName()).type.equals("ROLE_CLIENT")) {
            model.addAttribute("type", "/lk_client");

        } else if (allRoleRepository.findByUserlogin(auth1.getName()).type.equals("ROLE_BANK")) {
            model.addAttribute("type", "/lk_bank");
        } else {
            model.addAttribute("type", "/lk_cb");
        }

        return "main";
    }

    @GetMapping("/lk_client")
    public String clientPage() {
        return "lk_client"; }

    @GetMapping("/lk_bank")
    public String bankPage() {return "lk_bank"; }

    @GetMapping("/lk_cb")
    public String centralBankPage() {return "lk_cb"; }

}
