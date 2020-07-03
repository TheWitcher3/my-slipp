package net.slipp.web;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginedUser(session)) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {

        User user = HttpSessionUtils.getUserFromSession(session);

        Question question = new Question(user, title, contents);
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

        if(!HttpSessionUtils.isLoginedUser(session)) {
            return "/users/loginForm";
        }

        User loginedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if(!question.isSameWriter(loginedUser)) {
            return "/users/loginForm";
        }

        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/qna/updateForm";
    }

    @PostMapping("/{id}/updateArticle")
    public String updateArticle(@PathVariable Long id, String title, String contents, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);

        question.update(title, contents);
        System.out.println(question.toString());
        questionRepository.save(question);

        return String.format("redirect:/questions/%d", id);
    }

    @PostMapping("/{id}/deleteArticle")
    public String deleteArticle(@PathVariable Long id, HttpSession session) {

        if(!HttpSessionUtils.isLoginedUser(session)) {
            return "/users/loginForm";
        }

        User loginedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElse(null);
        if(!question.isSameWriter(loginedUser)) {
            return "/users/loginForm";
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }


























}
