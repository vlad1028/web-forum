package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping({"/post/{id}", "/post"})
    public String displayPost(Model model, @PathVariable(required = false) String id) {
        Post post = postService.findById(parseId(id));
        model.addAttribute("post", post);
        return "PostPage";
    }

    @PostMapping({"/post/{id}"})
    public String addComment(@Valid @ModelAttribute Comment comment,
                             BindingResult bindingResult,
                             @PathVariable String id,
                             HttpSession session) {
        if (bindingResult.hasErrors()) {
            putMessage(session, "Forbidden comment format.");
            return "redirect:/post/{id}";
        }

        Post post = postService.findById(parseId(id));
        User user = getUser(session);
        postService.addComment(post, comment, user);
        putMessage(session, "You successfully added a comment");
        return "redirect:/post/{id}";
    }
}
