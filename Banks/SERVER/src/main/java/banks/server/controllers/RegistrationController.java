package banks.server.controllers;

import banks.server.model.AllRole;
import banks.server.model.Bank;
import banks.server.model.Client;
import banks.server.repositories.AllRoleRepository;
import banks.server.repositories.BankRepository;
import banks.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AllRoleRepository allRoleRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

    @GetMapping("/registration/client")
    public String registrationClient() {
        return "registrationForClient";
    }

    @PostMapping("/registration/client")
    public String registrationClient(@RequestParam String name,
                                        @RequestParam String surname,
                                        @RequestParam String login,
                                        @RequestParam String password) {

        AllRole allroles = allRoleRepository.findByUserlogin(login);

        if(allroles!=null)
        {
            return "registrationForClient";
        }

        else {
            Client new_client = new Client();
            AllRole new_allrole=new AllRole();

            new_allrole.setLogin(login);
            new_allrole.setPassword(password);
            new_allrole.setType("ROLE_CLIENT");

            new_client.setName(name);
            new_client.setSurname(surname);
            new_client.setLogin(login);
            new_client.setPassword(passwordEncoder.encode(password));
            new_client.setType("ROLE_CLIENT");
            new_client.setPassport(null);
            new_client.setPhoneNumber(null);

            ResponseEntity.ok(allRoleRepository.save(new_allrole));
            ResponseEntity.ok(clientRepository.save(new_client));
            return "redirect:/login";}
    }

    @GetMapping("/registration/bank")
    public String registrationBank() {
        return "registrationForBank";
    }

    @PostMapping("/registration/bank")
    public String registrationBank(@RequestParam String login,
                                   @RequestParam String password) {

        AllRole allrole=allRoleRepository.findByUserlogin(login);

        if(allrole!=null)  {return "registrationForBank";}

        else{
            AllRole new_allrole=new AllRole();
            Bank new_bank = new Bank();


            new_allrole.setLogin(login);
            new_allrole.setPassword(password);
            new_allrole.setType("ROLE_BANK");

            new_bank.setLogin(login);
            new_bank.setPassword(passwordEncoder.encode(password));
            new_bank.setType("ROLE_BANK");

            ResponseEntity.ok(allRoleRepository.save(new_allrole));
            ResponseEntity.ok(bankRepository.save(new_bank));
            return "redirect:/lk_cb";
        }
    }




    /*
    @GetMapping("/pay/begginer")
    public String pay_begginer() {
        return "pay_begginer";
    }

    @PostMapping("/pay/begginer")
    public String pay_beginner_2(@RequestParam String tariff,@RequestParam String number) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(auth1.getName()).tariff=tariff;
        clientRepository.save(clientRepository.findBylogin(auth1.getName()));
        return "redirect:/home";
    }

    @GetMapping("/pay/elementary")
    public String pay_elementary() {
        return "pay_elementary";
    }

    @PostMapping("/pay/elementary")
    public String pay_elementary_2(@RequestParam String tariff,@RequestParam String number) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(auth1.getName()).tariff=tariff;
        clientRepository.save(clientRepository.findBylogin(auth1.getName()));
        return "redirect:/home";
    }

    @GetMapping("/pay/intermediate")
    public String pay_intermediate() {
        return "pay_intermediate";
    }

    @PostMapping("/pay/intermediate")
    public String pay_intermediate_2(@RequestParam String tariff,@RequestParam String number) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(auth1.getName()).tariff=tariff;
        clientRepository.save(clientRepository.findBylogin(auth1.getName()));
        return "redirect:/home";
    }

    @GetMapping("/pay/upper_intermediate")
    public String pay_upper_intermediate() {
        return "pay_upper_intermediate";
    }

    @PostMapping("/pay/upper_intermediate")
    public String pay_upper_intermediate_2(@RequestParam String tariff,@RequestParam String number) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(auth1.getName()).tariff=tariff;
        clientRepository.save(clientRepository.findBylogin(auth1.getName()));
        return "redirect:/home";
    }

    @GetMapping("/pay/advanced")
    public String pay_advanced() {
        return "pay_advanced";
    }

    @PostMapping("/pay/advanced")
    public String pay_advanced_2(@RequestParam String tariff,@RequestParam String number) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(auth1.getName()).tariff=tariff;
        clientRepository.save(clientRepository.findBylogin(auth1.getName()));
        return "redirect:/home";
    }
    @GetMapping("/student_page")
    public String studentPage(@RequestParam(name = "name",required = false,defaultValue = "300") String name,Model model) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", clientRepository.findBylogin(auth1.getName()).name);
        model.addAttribute("surname", clientRepository.findBylogin(auth1.getName()).surname);
        model.addAttribute("login", clientRepository.findBylogin(auth1.getName()).login);
        model.addAttribute("link", clientRepository.findBylogin(auth1.getName()).link);
        model.addAttribute("tariff", clientRepository.findBylogin(auth1.getName()).tariff);
        model.addAttribute("teacher", clientRepository.findBylogin(auth1.getName()).yourTeacher);
        return "student_page";
    }
    @GetMapping("/teacher_page")
    public String teacherPage(@RequestParam(name = "name",required = false,defaultValue = "300") String name,Model model) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", bankRepository.findBylogin(auth1.getName()).name);
        model.addAttribute("surname", bankRepository.findBylogin(auth1.getName()).surname);
        model.addAttribute("login", bankRepository.findBylogin(auth1.getName()).login);
        model.addAttribute("link", bankRepository.findBylogin(auth1.getName()).link);
        model.addAttribute("education", bankRepository.findBylogin(auth1.getName()).education);
        return "teacher_page";
    }

    @GetMapping("/admin_page")
    public String adminPage(@RequestParam(name = "name",required = false,defaultValue = "300") String name,Model model) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", centralBankRepository.findBylogin(auth1.getName()).name);
        model.addAttribute("surname", centralBankRepository.findBylogin(auth1.getName()).surname);
        model.addAttribute("login", centralBankRepository.findBylogin(auth1.getName()).login);
        model.addAttribute("link", centralBankRepository.findBylogin(auth1.getName()).link);
        model.addAttribute("position", centralBankRepository.findBylogin(auth1.getName()).position);
        return "admin_page";
    }

   @GetMapping("/my_student")
   public String getStudent(@RequestParam(name = "name",required = false,defaultValue = "300") String name, Model model) {
       Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
       model.addAttribute("student", clientRepository.findByStudentTeacher(auth1.getName()));
       return "my_student";
   }

    @GetMapping("/free_student")
    public String freeStudent(@RequestParam(name = "name",required = false,defaultValue = "300") String name, Model model) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("student", clientRepository.findFreeStudent());
        return "free_student";
    }


    @GetMapping("/target")
    public String target() {
        return "target";
    }

    @PostMapping("/target")
    public String target(@RequestParam String yourTeacher,@RequestParam String student) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        clientRepository.findBylogin(student).yourTeacher=yourTeacher;
        clientRepository.save(clientRepository.findBylogin(student));
        return "redirect:/home";
    }


*/

}
