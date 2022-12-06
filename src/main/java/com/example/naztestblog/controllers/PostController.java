package com.example.naztestblog.controllers;

import com.example.naztestblog.models.Post;
import com.example.naztestblog.models.User;
import com.example.naztestblog.repositories.PostRepository;
import com.example.naztestblog.repositories.UserRepository;
import com.example.naztestblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Controller
public class PostController {
    private final EmailService emailService;
    private final PostRepository postDao;
    private final UserRepository userDao;
    public PostController(EmailService emailService, PostRepository postDao, UserRepository userDao) {
        this.emailService = emailService;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    //Get all the posts
    @GetMapping("/posts")
    public String allPosts(Model model){
        User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("allPosts", postDao.findAll());
        model.addAttribute("user",myUser);
        return "/posts/all-posts";
    }
    //View Someone's individual posts
    @GetMapping("/posts/{id}")
    public String seeUserPost(@PathVariable Long id, Model model){
        Post post = postDao.findById(id).get();
        model.addAttribute("id", id);
        model.addAttribute("forcePost", post);
        return "/posts/individual-post";
    }
    //View the form for creating a post
    @GetMapping("/posts/create")
    public String viewCreateForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }
    //Submit the post you are creating
    @PostMapping("/posts/create")
    public String submitCreatedPost(@ModelAttribute Post post){
       User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(myUser);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        postDao.save(post);
        return "redirect:/posts";
    }
    @GetMapping("/posts/{id}/edit")
    public String goToEdit(@PathVariable Long id,Model model){
        Post post = postDao.findById(id).get();
        User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("editPost",post);
        if(post.getUser().getId() == myUser.getId()){
            return "posts/edit";
        } else {
            return "/error";
        }

    }
    @PostMapping("/posts/{id}/edit")
    public String submitEdit(@ModelAttribute Post post){
        User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(myUser);
            postDao.save(post);
            return "redirect:/posts";

    }

    @GetMapping("/delete/{postID}")
    public String deletePost(@PathVariable Long postID){
        User myUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(myUser.getId() == postDao.findById(postID).get().getUser().getId()){
            postDao.deleteById(postID);
            return "redirect:/posts";
        } else {
            return "redirect:/error";
        }
    }

}//End of class
