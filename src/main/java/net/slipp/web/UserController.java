package net.slipp.web;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private List<User> users = new ArrayList<>();

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(model.toString());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/update/{id}")
    public String update(User updatedUser, @PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.updateUser(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }



    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/updateform";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

}





















