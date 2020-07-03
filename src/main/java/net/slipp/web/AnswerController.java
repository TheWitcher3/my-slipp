package net.slipp.web;

import net.slipp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginedUser(session)) {
            return "redirect:/users/loginForm";
        }
        User loginedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        System.out.println("question : " + question.toString());
        Answer answer = new Answer(loginedUser, question, contents);

        System.out.println("answer : " + answer);

        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }


}
