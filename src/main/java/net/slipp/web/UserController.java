package net.slipp.web;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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


    // 가입
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // 수정
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if(HttpSessionUtils.isLoginedUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if(!sessionedUser.matchId(id.toString())) {
            throw new IllegalStateException("아이디가 맞지 않음");
        }

        User user = userRepository.findById(sessionedUser.getId()).orElse(null);
        if(user == null) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("users", user);
        return "/user/updateForm";

    }

    @PostMapping("/update/{id}")
    public String update(User updatedUser, @PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.updateUser(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    // 로그인
    @GetMapping("/loginForm")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            System.out.println("계정이 없음");
            return "redirect:/users/loginForm";
        }
        else if(!user.matchPassword(password)) {
            return "redirect:/users/loginForm";
        }

        System.out.println("로그인 성공");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        session.invalidate();
        return "redirect:/";
    }



}





















