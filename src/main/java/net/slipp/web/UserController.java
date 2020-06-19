package net.slipp.web;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            return "redirect:/users/loginForm";
        }
        if(!password.equals(user.getPassword())) {
            System.out.println("패스워드 불일치");
            return "redirect:/users/loginForm";
        }

        session.setAttribute("user", user);
        System.out.println("login..... success");

        return "redirect:/";
    }

    @PostMapping("")
    public String create(User user) {
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(model.toString());
        return "/user/list";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("userInfo", userRepository.findById(id).orElse(null));
        return "/user/updateForm";
    }

    @PostMapping("/{id}")
//    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        user.setUserId(updatedUser.getUserId());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);

        return "redirect:/users";
    }

}
